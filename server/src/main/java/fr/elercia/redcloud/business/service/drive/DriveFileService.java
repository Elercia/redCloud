package fr.elercia.redcloud.business.service.drive;

import fr.elercia.redcloud.business.entity.drive.DriveFile;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.business.service.utils.StringUtils;
import fr.elercia.redcloud.dao.repository.FileRepository;
import fr.elercia.redcloud.exceptions.FileNameFormatException;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileOperationException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Optional;
import java.util.UUID;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class DriveFileService {

    private static final Logger LOG = LoggerFactory.getLogger(DriveFileService.class);

    //a-z A-Z 0-9 spaces(like [\r\n\t\f\v ]) _ . - ( ) :
    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("^([a-zA-Z0-9\\s_.\\-\\(\\):])+$");

    private FileRepository fileRepository;
    private DriveFileSystemService driveFileSystemService;

    @Autowired
    public DriveFileService(FileRepository fileRepository, DriveFileSystemService driveFileSystemService) {

        this.fileRepository = fileRepository;
        this.driveFileSystemService = driveFileSystemService;
    }

    public DriveFile find(UUID fileId) throws FileNotFoundException {

        DriveFile driveFile = fileRepository.findByResourceId(fileId);

        if (driveFile == null) {
            throw new FileNotFoundException();
        }

        LOG.info("Find driveFile {}", driveFile.getResourceId());

        return driveFile;
    }

    public void delete(DriveFile driveFile) {

        LOG.info("Delete driveFile {}", driveFile.getResourceId());

        fileRepository.delete(driveFile);
    }

    public void move(DriveFile driveFile, DriveFolder driveFolder) throws FileOperationException {

        //Check if a driveFile with this name already exist in this driveFolder
        Optional<DriveFile> any = driveFolder.getDriveFiles().stream().filter(f -> f.getFileName().equals(driveFile.getFileName())).findAny();
        if (any.isPresent()) {
            throw new FileOperationException();
        }

        LOG.info("Move driveFile from {} to {}", driveFile.getParent().getResourceId(), driveFolder.getResourceId());

        driveFile.setParent(driveFolder);
        fileRepository.save(driveFile);
    }

    public DriveFile storeFile(DriveFolder driveFolder, MultipartFile multipartFile) throws FileNameFormatException, FileStorageException {

        String fileName = multipartFile.getOriginalFilename();

        if (StringUtils.isNullOrEmpty(fileName)) {
            fileName = multipartFile.getName();
        }

        if (!isValidFileName(fileName)) {
            throw new FileNameFormatException();
        }

        DriveFile driveFile = new DriveFile(fileName, driveFolder, multipartFile.getSize());

        driveFileSystemService.uploadFile(multipartFile, driveFile);

        fileRepository.save(driveFile);

        LOG.info("StoreFile driveFile {}", driveFile.getResourceId());

        return driveFile;
    }

    public Resource downloadFile(DriveFile driveFile) throws FileNotFoundException {

        LOG.info("DownloadFile driveFile {}", driveFile.getResourceId());

        return driveFileSystemService.download(driveFile);
    }

    private boolean isValidFileName(String fileName) {

        Matcher matcher = FILE_NAME_PATTERN.matcher(fileName);

        return matcher.matches();
    }
}
