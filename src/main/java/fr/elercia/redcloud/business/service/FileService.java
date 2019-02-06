package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
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
public class FileService {

    private static final Logger LOG = LoggerFactory.getLogger(FileService.class);

    //a-z A-Z 0-9 spaces(like [\r\n\t\f\v ]) _ . - ( ) :
    private static final Pattern FILE_NAME_PATTERN = Pattern.compile("^([a-zA-Z0-9\\s_.\\-\\(\\):])+$");

    private FileRepository fileRepository;
    private FileSystemService fileSystemService;

    @Autowired
    public FileService(FileRepository fileRepository, FileSystemService fileSystemService) {

        this.fileRepository = fileRepository;
        this.fileSystemService = fileSystemService;
    }

    public File find(UUID fileId) throws FileNotFoundException {

        File file = fileRepository.findByResourceId(fileId);

        if (file == null) {
            throw new FileNotFoundException();
        }

        LOG.info("Find file {}", file.getResourceId());

        return file;
    }

    public void delete(File file) {

        LOG.info("Delete file {}", file.getResourceId());

        fileRepository.delete(file);
    }

    public void move(File file, Directory directory) throws FileOperationException {

        //Check if a file with this name already exist in this directory
        Optional<File> any = directory.getFiles().stream().filter(f -> f.getFileName().equals(file.getFileName())).findAny();
        if (any.isPresent()) {
            throw new FileOperationException();
        }

        LOG.info("Move file from {} to {}", file.getDirectory().getResourceId(), directory.getResourceId());

        file.setDirectory(directory);
        fileRepository.save(file);
    }

    public File storeFile(Directory directory, MultipartFile multipartFile) throws FileNameFormatException, FileStorageException {

        String fileName = multipartFile.getOriginalFilename();

        if (StringUtils.isNullOrEmpty(fileName)) {
            fileName = multipartFile.getName();
        }

        if (!isValidFileName(fileName)) {
            throw new FileNameFormatException();
        }

        File file = new File(fileName, directory, multipartFile.getSize());

        fileSystemService.uploadFile(multipartFile, file);

        fileRepository.save(file);

        LOG.info("StoreFile file {}", file.getResourceId());

        return file;
    }

    public Resource downloadFile(File file) throws FileNotFoundException {

        LOG.info("DownloadFile file {}", file.getResourceId());

        return fileSystemService.download(file);
    }

    private boolean isValidFileName(String fileName) {

        Matcher matcher = FILE_NAME_PATTERN.matcher(fileName);

        return matcher.matches();
    }
}
