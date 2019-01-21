package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UpdateDirectoryDto;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
import fr.elercia.redcloud.dao.repository.FileRepository;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DirectoryService {

    private DirectoryRepository directoryRepository;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository) {

        this.directoryRepository = directoryRepository;
    }

    public void createRootDirectory(User user) {

        Directory directory = new Directory("root", user, null);
        directoryRepository.save(directory);
        user.setRootDirectory(directory);
    }

    public Directory findRootDirectory(User user) throws DirectoryNotFoundException {

        Directory directory = directoryRepository.findByParentDirectoryIsNullAndUser(user);

        if(directory == null) {
            throw new DirectoryNotFoundException();
        }

        return directory;
    }

    public Directory find(UUID directoryId) throws DirectoryNotFoundException {

        Directory directory = directoryRepository.findByResourceId(directoryId);

        if(directory == null) {
            throw new DirectoryNotFoundException();
        }

        return directory;
    }

    public Directory createSubDirectory(Directory parentDir, CreateDirectoryDto wantedDirectory) {

        Directory subDirectory = new Directory(wantedDirectory.getName(), parentDir.getUser(), parentDir);

        subDirectory = directoryRepository.save(subDirectory);

        return subDirectory;
    }

    public void deleteDirectory(Directory directory) {

        directoryRepository.delete(directory);
    }

    public void update(Directory directory, UpdateDirectoryDto updateDirectoryDto) {

        directory.updateName(updateDirectoryDto.getName());

        directoryRepository.save(directory);
    }

    public void move(Directory directory, Directory moveToDirectory) {

        directory.setParentDirectory(moveToDirectory);

        directoryRepository.save(directory);
    }
}
