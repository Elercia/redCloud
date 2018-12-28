package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
import fr.elercia.redcloud.dao.repository.FileRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class DirectoryService {

    private DirectoryRepository directoryRepository;
    private FileRepository fileRepository;

    @Autowired
    public DirectoryService(DirectoryRepository directoryRepository, FileRepository fileRepository) {

        this.directoryRepository = directoryRepository;
        this.fileRepository = fileRepository;
    }

    public void createRootDirectory(User user) {

        Directory directory = new Directory("root", user, null);
        directoryRepository.save(directory);
        user.setRootDirectory(directory);

    }

    public Directory findRootDirectory(User user) {

        return null;
    }

    public Directory find(UUID parentDirectoryId) {
        return null;
    }

    public Directory createSubSirectory(Directory parentDir, CreateDirectoryDto wantedDirectory) {
        return null;
    }
}
