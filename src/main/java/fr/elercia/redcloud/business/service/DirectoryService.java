package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UpdateDirectoryDto;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
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

    public Directory findRootDirectory(User user) throws DirectoryNotFoundException {

        Directory directory = directoryRepository.findByParentDirectoryIsNullAndUser(user);

        if (directory == null) {
            throw new DirectoryNotFoundException();
        }

        LOG.info("findRootDirectory directory {}", directory.getResourceId());

        return directory;
    }

    public Directory find(UUID directoryId) throws DirectoryNotFoundException {

        Directory directory = directoryRepository.findByResourceId(directoryId);

        if (directory == null) {
            throw new DirectoryNotFoundException();
        }

        LOG.info("find directory {}", directory.getResourceId());

        return directory;
    }

    public Directory createSubDirectory(Directory parentDir, CreateDirectoryDto wantedDirectory) {

        Directory subDirectory = new Directory(wantedDirectory.getName(), parentDir.getUser(), parentDir);

        subDirectory = directoryRepository.save(subDirectory);

        LOG.info("createSubDirectory parent {}, children {}", parentDir.getResourceId(), subDirectory.getResourceId());

        return subDirectory;
    }

    public void deleteDirectory(Directory directory) {

        LOG.info("Delete directory {}", directory.getResourceId());

        directoryRepository.delete(directory);
    }

    public void update(Directory directory, UpdateDirectoryDto updateDirectoryDto) {

        LOG.info("Update directory {}", directory.getResourceId());

        directory.updateName(updateDirectoryDto.getName());

        directoryRepository.save(directory);
    }

    public void move(Directory directory, Directory moveToDirectory) {

        //TODO Can't move root directory

        LOG.info("Move directory from {} to {}", directory.getResourceId(), moveToDirectory.getResourceId());

        directory.setParentDirectory(moveToDirectory);

        directoryRepository.save(directory);
    }
}
