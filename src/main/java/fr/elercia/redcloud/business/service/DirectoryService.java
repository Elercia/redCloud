package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UpdateDirectoryDto;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedDirectoryActionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DirectoryService {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryService.class);

    private DirectoryRepository directoryRepository;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository) {

        this.directoryRepository = directoryRepository;
    }

    public void createRootDirectory(User user) {

        LOG.info("Creating root directory for user {}", user.getResourceId());

        Directory directory = new Directory("root", user, null);
        directoryRepository.save(directory);
        user.setRootDirectory(directory);
    }

    public Directory find(UUID directoryId) throws DirectoryNotFoundException {

        Directory directory = directoryRepository.findByResourceId(directoryId);

        if (directory == null) {
            throw new DirectoryNotFoundException();
        }

        LOG.info("find directory {}", directory.getResourceId());

        return directory;
    }

    public Directory createSubDirectory(Directory parentDir, CreateDirectoryDto wantedDirectory) throws UnauthorizedDirectoryActionException {

        if (!isNameValid(parentDir, wantedDirectory.getName())) {
            throw new UnauthorizedDirectoryActionException();
        }

        Directory subDirectory = new Directory(wantedDirectory.getName(), parentDir.getUser(), parentDir);

        subDirectory = directoryRepository.save(subDirectory);

        LOG.info("createSubDirectory parent {}, children {}", parentDir.getResourceId(), subDirectory.getResourceId());

        return subDirectory;
    }

    public void deleteDirectory(Directory directory) throws UnauthorizedDirectoryActionException {

        LOG.info("Delete directory {}", directory.getResourceId());

        // Deleting a root directory is forbidden
        if(directory.getParentDirectory() == null) {
            throw new UnauthorizedDirectoryActionException();
        }

        directoryRepository.delete(directory);
    }

    public void update(Directory directory, UpdateDirectoryDto updateDirectoryDto) throws UnauthorizedDirectoryActionException {

        LOG.info("Update directory {}", directory.getResourceId());

        if (directory.getParentDirectory() == null || !isNameValid(directory, updateDirectoryDto.getName())) {
            throw new UnauthorizedDirectoryActionException();
        }

        directory.updateName(updateDirectoryDto.getName());

        directoryRepository.save(directory);
    }

    public void move(Directory directory, Directory moveToDirectory) throws UnauthorizedDirectoryActionException {

        if (moveToDirectory == null || directory.getParentDirectory() == null) {
            throw new UnauthorizedDirectoryActionException("Can't move root directory");
        }

        if (!isNameValid(moveToDirectory, directory.getName())) {
            throw new UnauthorizedDirectoryActionException();
        }

        LOG.info("Move directory from {} to {}", directory.getResourceId(), moveToDirectory.getResourceId());

        directory.setParentDirectory(moveToDirectory);

        directoryRepository.save(directory);
    }

    private boolean isNameValid(Directory container, String wantedDirectoryName) {

        for (Directory directory : container.getSubFolders()) {
            if (directory.getName().equalsIgnoreCase(wantedDirectoryName)) {
                return false;
            }
        }
        return true;
    }
}
