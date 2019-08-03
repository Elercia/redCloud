package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.drive.DriveFile;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.exceptions.UnauthorizedActionException;

public class SecurityUtils {
    private SecurityUtils() {

    }

    public static void checkUserRightOn(AppUser connectedUser, DriveFile driveFile) {
        checkUserRightOn(connectedUser, driveFile.getParent());
    }

    public static void checkUserRightOn(AppUser connectedUser, DriveFolder driveFolder) {
        checkUserRightOn(connectedUser, driveFolder.getUser());
    }

    public static void checkUserRightOn(AppUser connectedUser, AppUser user) {

        if (connectedUser.getUserType().isAdmin()) {
            return;
        }

        if (!connectedUser.equals(user)) {
            throw new UnauthorizedActionException();
        }
    }
}
