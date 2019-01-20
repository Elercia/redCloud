package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.service.FileSystemUtils;

import java.io.File;

public class TestUtils {

    public static boolean userRootDirectoryCreated(User user) {

        File file = new File(FileSystemUtils.getUserDirectoryPath(user));

        return file.exists() && file.isDirectory();
    }
}
