package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.config.SaveConfig;
import org.springframework.http.MediaType;

public class FileSystemUtils {

    public static String getPathToFile(File file) {
        return SaveConfig.PATH_TO_FILES + "/" + getUserPath(file) + "/" + getFilePath(file);
    }

    private static String getFilePath(File file) {
        return file.getResourceId().toString();
    }

    public static String getUserDirectoryPath(User user) {
        return SaveConfig.PATH_TO_FILES + "/" + getUserPath(user) + "/";
    }

    private static String getUserPath(User user) {
        return user.getResourceId().toString();
    }

    private static String getUserPath(File file) {
        return getUserPath(file.getDirectory().getUser());
    }

    @Deprecated
    private static String getDirectoryPath(File file) {

        StringBuilder path = new StringBuilder();
        Directory dir = file.getDirectory();
        do {
            path.append(dir.getResourceId().toString()).append("/").append(path);
            dir = dir.getParentDirectory();

        } while (dir != null);

        path.deleteCharAt(path.length() - 1);

        return path.toString();
    }

    public static MediaType getMediaTypeFromExtension(String fileName) {

        String ext = null;
        int dotIndex = fileName.lastIndexOf(".");
        if (dotIndex != -1) {
            ext = fileName.substring(dotIndex + 1);
        }

        if (ext == null) {
            return MediaType.APPLICATION_OCTET_STREAM;
        }

        switch (ext) {
            case "pdf":
                return MediaType.APPLICATION_PDF;
            case "png":
                return MediaType.IMAGE_PNG;
            case "jpg":
            case "jpeg":
                return MediaType.IMAGE_JPEG;
            case "gif":
                return MediaType.IMAGE_GIF;
            default:
                return MediaType.APPLICATION_OCTET_STREAM;
        }
    }
}