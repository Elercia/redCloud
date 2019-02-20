package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.exceptions.UnauthorizedActionException;

public class SecurityUtils {
    private SecurityUtils() {

    }

    public static void checkUserRightOn(User connectedUser, File file) {
        checkUserRightOn(connectedUser, file.getDirectory());
    }

    public static void checkUserRightOn(User connectedUser, Directory directory) {
        checkUserRightOn(connectedUser, directory.getUser());
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
