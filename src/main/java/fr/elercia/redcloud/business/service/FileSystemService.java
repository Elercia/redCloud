package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.DynamicConfig;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import fr.elercia.redcloud.exceptions.UnexpectedFileSystemException;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class FileSystemService {

    private static final Logger LOG = LoggerFactory.getLogger(FileSystemService.class);
    private DynamicConfigService dynamicConfigService;

    @Autowired
    public FileSystemService(DynamicConfigService dynamicConfigService) {
        this.dynamicConfigService = dynamicConfigService;
    }

    public void uploadFile(MultipartFile multipartFile, File file) throws FileStorageException {

        String filePath = getPathToFile(file);

        LOG.info("Upload file [path {}]", filePath);

        java.io.File ioFile = new java.io.File(filePath);

        if (ioFile.exists()) {
            throw new FileStorageException();
        }

        try {
            if (!ioFile.createNewFile()) {
                throw new FileStorageException("Cannot create the file");
            }

            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), ioFile);

        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

    public Resource download(File file) throws FileNotFoundException {
        try {
            String filePath = getPathToFile(file);

            LOG.info("Download file [path {}]", filePath);

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

        LOG.info("Create user directory [user {}]", user.getResourceId());

        java.io.File userDirectory = new java.io.File(getUserDirectoryPath(user));

        if (userDirectory.exists()) {
            throw new UnexpectedFileSystemException("New user directory already exists user:" + user.getResourceId());
        }

        if (!userDirectory.mkdirs()) {
            throw new UnexpectedFileSystemException("Unable to create user directory user:" + user.getResourceId());
        }
    }

    public void deleteUserFileSystem(User user) {

        LOG.info("Delete user directory [user {}]", user.getResourceId());

        java.io.File userDirectory = new java.io.File(getUserDirectoryPath(user));

        if (!userDirectory.exists() || !userDirectory.isDirectory()) {
            throw new UnexpectedFileSystemException("Impossible to delete user directory (wrong directory) user:" + user.getResourceId());
        }
        try {
            FileUtils.deleteDirectory(userDirectory);
        } catch (IOException e) {
            throw new UnexpectedFileSystemException("Unable to delete user directory (delete error) user:" + user.getResourceId(), e);
        }
    }

    private String getPathToFile(File file) {
        return getConfiguredPathToFiles() + "/" + getUserPath(file) + "/" + getFilePath(file);
    }

    private String getFilePath(File file) {
        return file.getResourceId().toString();
    }

    private String getUserDirectoryPath(User user) {
        return getConfiguredPathToFiles() + "/" + getUserPath(user) + "/";
    }

    private String getUserPath(User user) {
        return user.getResourceId().toString();
    }

    private String getUserPath(File file) {
        return getUserPath(file.getDirectory().getUser());
    }

    private String getConfiguredPathToFiles() {
        return dynamicConfigService.getString(DynamicConfig.DynamicConfigName.STORAGE_PATH);
    }
}
