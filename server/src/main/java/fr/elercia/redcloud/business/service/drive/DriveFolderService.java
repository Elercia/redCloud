package fr.elercia.redcloud.business.service.drive;

import fr.elercia.redcloud.api.dto.entity.drive.CreateFolderDto;
import fr.elercia.redcloud.api.dto.entity.drive.UpdateFolderDto;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.business.events.UserCreationEvent;
import fr.elercia.redcloud.dao.repository.DriveFolderRepository;
import fr.elercia.redcloud.exceptions.FolderNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedFolderActionException;
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

    public void createRootFolder(User user) {

        LOG.info("Creating root driveFolder for user {}", user.getResourceId());

        DriveFolder driveFolder = new DriveFolder("root", user, null);
        driveFolderRepository.save(driveFolder);
        user.setRootDriveFolder(driveFolder);
    }

    public DriveFolder find(UUID directoryId) throws FolderNotFoundException {

        DriveFolder driveFolder = driveFolderRepository.findByResourceId(directoryId);

        if (driveFolder == null) {
            throw new FolderNotFoundException();
        }

        LOG.info("find driveFolder {}", driveFolder.getResourceId());

        return driveFolder;
    }

    public DriveFolder createSubFolder(DriveFolder parentDir, CreateFolderDto wantedFolder) throws UnauthorizedFolderActionException {

        if (!isNameValid(parentDir, wantedFolder.getName())) {
            throw new UnauthorizedFolderActionException();
        }

        DriveFolder subDriveFolder = new DriveFolder(wantedFolder.getName(), parentDir.getUser(), parentDir);

        subDriveFolder = driveFolderRepository.save(subDriveFolder);

        LOG.info("createSubFolder parent {}, children {}", parentDir.getResourceId(), subDriveFolder.getResourceId());

        return subDriveFolder;
    }

    public void deleteFolder(DriveFolder driveFolder) throws UnauthorizedFolderActionException {

        LOG.info("Delete driveFolder {}", driveFolder.getResourceId());

        // Deleting a root driveFolder is forbidden
        if(driveFolder.getParentDriveFolder() == null) {
            throw new UnauthorizedFolderActionException();
        }

        driveFolderRepository.delete(driveFolder);
    }

    public void update(DriveFolder driveFolder, UpdateFolderDto updateFolderDto) throws UnauthorizedFolderActionException {

        LOG.info("Update driveFolder {}", driveFolder.getResourceId());

        if (driveFolder.getParentDriveFolder() == null || !isNameValid(driveFolder, updateFolderDto.getName())) {
            throw new UnauthorizedFolderActionException();
        }

        driveFolder.updateName(updateFolderDto.getName());

        driveFolderRepository.save(driveFolder);
    }

    public void move(DriveFolder driveFolder, DriveFolder moveToDriveFolder) throws UnauthorizedFolderActionException {

        if (moveToDriveFolder == null || driveFolder.getParentDriveFolder() == null) {
            throw new UnauthorizedFolderActionException("Can't move root driveFolder");
        }

        if (!isNameValid(moveToDriveFolder, driveFolder.getName()) || driveFolder.equals(moveToDriveFolder)) {
            throw new UnauthorizedFolderActionException();
        }

        LOG.info("Move driveFolder from {} to {}", driveFolder.getResourceId(), moveToDriveFolder.getResourceId());

        driveFolder.setParentDriveFolder(moveToDriveFolder);

        driveFolderRepository.save(driveFolder);
    }

    private boolean isNameValid(DriveFolder container, String wantedFolderName) {

        for (DriveFolder driveFolder : container.getSubFolders()) {
            if (driveFolder.getName().equalsIgnoreCase(wantedFolderName)) {
                return false;
            }
        }
        return true;
    }

    @Override
    public void onApplicationEvent(UserCreationEvent event) {
        createRootFolder(event.getUser());
    }
}
