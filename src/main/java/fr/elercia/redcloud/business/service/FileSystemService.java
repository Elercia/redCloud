package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class FileSystemService {

    @Autowired
    public FileSystemService() {

    }

    public void uploadFile(MultipartFile multipartFile, File file) throws FileStorageException {
        java.io.File ioFile = new java.io.File(FileSystemUtils.getPathToFile(file));

        if (ioFile.exists()) {
            throw new FileStorageException();
        }

        try {
            ioFile.createNewFile();
            multipartFile.transferTo(ioFile);
        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

    public Resource download(File file) throws FileNotFoundException {
        try {
            String filePath = FileSystemUtils.getPathToFile(file);
            Resource resource = new UrlResource(new java.io.File(filePath).toURI());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("File not found " + file.getFileName());
            }

        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("File not found " + file.getFileName(), ex);
        }
    }

    void createUserFileSystemSpace(User user) {
        java.io.File userDirectory = new java.io.File(FileSystemUtils.getUserDirectoryPath(user));

        if (userDirectory.exists()) {
            throw new RuntimeException("New user directory already exists user:" + user.getResourceId());
        }

        if (!userDirectory.mkdirs()) {
            throw new RuntimeException("Unable to create user directory user:" + user.getResourceId());
        }
    }

    public void deleteUserFileSystem(User user) {
    }
}
