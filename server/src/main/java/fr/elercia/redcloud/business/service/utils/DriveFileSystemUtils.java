package fr.elercia.redcloud.business.service.utils;

import org.springframework.http.MediaType;

public class DriveFileSystemUtils {
    public static MediaType getMediaTypeFromExtension(String fileName) {

        String ext = null;
        int dotIndex = fileName.lastIndexOf('.');
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
