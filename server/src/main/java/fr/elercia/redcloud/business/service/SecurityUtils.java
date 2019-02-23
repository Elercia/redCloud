package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.drive.DriveFile;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.exceptions.UnauthorizedActionException;

public class SecurityUtils {
    private SecurityUtils() {

    }

    public static void checkUserRightOn(User connectedUser, DriveFile driveFile) {
        checkUserRightOn(connectedUser, driveFile.getParent());
    }

    public static void checkUserRightOn(User connectedUser, DriveFolder driveFolder) {
        checkUserRightOn(connectedUser, driveFolder.getUser());
    }

    public static void checkUserRightOn(User connectedUser, User user) {

        if (connectedUser.getUserType().isAdmin()) {
            return;
        }

        if (!connectedUser.equals(user)) {
            throw new UnauthorizedActionException();
        }
    }
}
