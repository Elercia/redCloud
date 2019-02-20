package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.entity.DynamicConfig;
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
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.net.MalformedURLException;

@Service
public class DriveFileSystemService {

    private static final Logger LOG = LoggerFactory.getLogger(DriveFileSystemService.class);
    private DynamicConfigService dynamicConfigService;

    @Autowired
    public DriveFileSystemService(DynamicConfigService dynamicConfigService) {
        this.dynamicConfigService = dynamicConfigService;
    }

    public void uploadFile(MultipartFile multipartFile, DriveFile driveFile) throws FileStorageException {

        String filePath = getPathToFile(driveFile);

        LOG.info("Upload driveFile [path {}]", filePath);

        java.io.File ioFile = new java.io.File(filePath);

        if (ioFile.exists()) {
            throw new FileStorageException();
        }

        try {
            if (!ioFile.createNewFile()) {
                throw new FileStorageException("Cannot create the driveFile");
            }

            FileUtils.copyInputStreamToFile(multipartFile.getInputStream(), ioFile);

        } catch (IOException e) {
            throw new FileStorageException(e);
        }
    }

    public Resource download(DriveFile driveFile) throws FileNotFoundException {
        try {
            String filePath = getPathToFile(driveFile);

            LOG.info("Download driveFile [path {}]", filePath);

            Resource resource = new UrlResource(new java.io.File(filePath).toURI());

            if (resource.exists()) {
                return resource;
            } else {
                throw new FileNotFoundException("DriveFile not found " + driveFile.getFileName());
            }

        } catch (MalformedURLException ex) {
            throw new FileNotFoundException("DriveFile not found " + driveFile.getFileName(), ex);
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

    private String getPathToFile(DriveFile driveFile) {
        return getConfiguredPathToFiles() + "/" + getUserPath(driveFile) + "/" + getFilePath(driveFile);
    }

    private String getFilePath(DriveFile driveFile) {
        return driveFile.getResourceId().toString();
    }

    private String getUserDirectoryPath(User user) {
        return getConfiguredPathToFiles() + "/" + getUserPath(user) + "/";
    }

    private String getUserPath(User user) {
        return user.getResourceId().toString();
    }

    private String getUserPath(DriveFile driveFile) {
        return getUserPath(driveFile.getParent().getUser());
    }

    private String getConfiguredPathToFiles() {
        return dynamicConfigService.getString(DynamicConfig.DynamicConfigName.STORAGE_PATH);
    }
}
