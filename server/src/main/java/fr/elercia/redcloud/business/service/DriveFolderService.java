package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UpdateDirectoryDto;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.events.UserCreationEvent;
import fr.elercia.redcloud.dao.repository.DriveFolderRepository;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedDirectoryActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DriveFolderService implements ApplicationListener<UserCreationEvent> {

    private static final Logger LOG = LoggerFactory.getLogger(DriveFolderService.class);

    private DriveFolderRepository driveFolderRepository;

    @Autowired
    public DriveFolderService(DriveFolderRepository driveFolderRepository) {

        this.driveFolderRepository = driveFolderRepository;
    }

    public void createRootDirectory(User user) {

        LOG.info("Creating root driveFolder for user {}", user.getResourceId());

        DriveFolder driveFolder = new DriveFolder("root", user, null);
        driveFolderRepository.save(driveFolder);
        user.setRootDriveFolder(driveFolder);
    }

    public DriveFolder find(UUID directoryId) throws DirectoryNotFoundException {

        DriveFolder driveFolder = driveFolderRepository.findByResourceId(directoryId);

        if (driveFolder == null) {
            throw new DirectoryNotFoundException();
        }

        LOG.info("find driveFolder {}", driveFolder.getResourceId());

        return driveFolder;
    }

    public DriveFolder createSubDirectory(DriveFolder parentDir, CreateDirectoryDto wantedDirectory) throws UnauthorizedDirectoryActionException {

        if (!isNameValid(parentDir, wantedDirectory.getName())) {
            throw new UnauthorizedDirectoryActionException();
        }

        DriveFolder subDriveFolder = new DriveFolder(wantedDirectory.getName(), parentDir.getUser(), parentDir);

        subDriveFolder = driveFolderRepository.save(subDriveFolder);

        LOG.info("createSubDirectory parent {}, children {}", parentDir.getResourceId(), subDriveFolder.getResourceId());

        return subDriveFolder;
    }

    public void deleteDirectory(DriveFolder driveFolder) throws UnauthorizedDirectoryActionException {

        LOG.info("Delete driveFolder {}", driveFolder.getResourceId());

        // Deleting a root driveFolder is forbidden
        if(driveFolder.getParentDriveFolder() == null) {
            throw new UnauthorizedDirectoryActionException();
        }

        driveFolderRepository.delete(driveFolder);
    }

    public void update(DriveFolder driveFolder, UpdateDirectoryDto updateDirectoryDto) throws UnauthorizedDirectoryActionException {

        LOG.info("Update driveFolder {}", driveFolder.getResourceId());

        if (driveFolder.getParentDriveFolder() == null || !isNameValid(driveFolder, updateDirectoryDto.getName())) {
            throw new UnauthorizedDirectoryActionException();
        }

        driveFolder.updateName(updateDirectoryDto.getName());

        driveFolderRepository.save(driveFolder);
    }

    public void move(DriveFolder driveFolder, DriveFolder moveToDriveFolder) throws UnauthorizedDirectoryActionException {

        if (moveToDriveFolder == null || driveFolder.getParentDriveFolder() == null) {
            throw new UnauthorizedDirectoryActionException("Can't move root driveFolder");
        }

        if (!isNameValid(moveToDriveFolder, driveFolder.getName()) || driveFolder.equals(moveToDriveFolder)) {
            throw new UnauthorizedDirectoryActionException();
        }

        LOG.info("Move driveFolder from {} to {}", driveFolder.getResourceId(), moveToDriveFolder.getResourceId());

        driveFolder.setParentDriveFolder(moveToDriveFolder);

        driveFolderRepository.save(driveFolder);
    }

    private boolean isNameValid(DriveFolder container, String wantedDirectoryName) {

        for (DriveFolder driveFolder : container.getSubFolders()) {
            if (driveFolder.getName().equalsIgnoreCase(wantedDirectoryName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onApplicationEvent(UserCreationEvent event) {
        createRootDirectory(event.getUser());
    }
}
