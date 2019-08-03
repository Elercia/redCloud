package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.entity.drive.DriveFile;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.exceptions.UnauthorizedActionException;
import fr.elercia.redcloud.utils.FileTestUtils;
import fr.elercia.redcloud.utils.FolderTestUtils;
import fr.elercia.redcloud.utils.UserTestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourcesSecurityAccessTest {

    @Test
    void checkUserRightOnFile_rightUser_accept() {

        AppUser owner = UserTestUtils.mockUser();
        Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

        DriveFile driveFile = FileTestUtils.mockFile(owner);
        SecurityUtils.checkUserRightOn(owner, driveFile);
    }

    @Test
    void checkUserRightOnFile_wrongUser_throw() {

        assertThrows(UnauthorizedActionException.class, () -> {

            AppUser owner = UserTestUtils.mockUser();
            AppUser other = UserTestUtils.mockUser();
            Mockito.when(other.getUserType()).thenReturn(UserType.USER);

            DriveFile driveFile = FileTestUtils.mockFile(owner);
            SecurityUtils.checkUserRightOn(other, driveFile);
        });
    }

    @Test
    void checkUserRightOnFolder_rightUser_accept() {

        AppUser owner = UserTestUtils.mockUser();
        Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

        DriveFolder driveFolder = FolderTestUtils.mockFolderWithUser(owner);
        SecurityUtils.checkUserRightOn(owner, driveFolder);
    }

    @Test
    void checkUserRightOnFolder_wrongUser_throw() {

        assertThrows(UnauthorizedActionException.class, () -> {

            AppUser owner = UserTestUtils.mockUser();
            AppUser other = UserTestUtils.mockUser();

            Mockito.when(other.getUserType()).thenReturn(UserType.USER);

            DriveFolder driveFolder = FolderTestUtils.mockFolderWithUser(owner);
            SecurityUtils.checkUserRightOn(other, driveFolder);
        });
    }

    @Test
    void checkUserRightOnUser_rightUser_accept() {

        AppUser owner = UserTestUtils.mockUser();

        Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

        SecurityUtils.checkUserRightOn(owner, owner);
    }

    @Test
    void checkUserRightOnUser_wrongUser_throw() {

        assertThrows(UnauthorizedActionException.class, () -> {

            AppUser owner = UserTestUtils.mockUser();
            AppUser other = UserTestUtils.mockUser();

            Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

            SecurityUtils.checkUserRightOn(owner, other);
        });
    }

    @Test
    void checkAdminRights_everythingAccepted() {

        AppUser owner = UserTestUtils.mockUser();
        AppUser other = UserTestUtils.mockUser();

        Mockito.when(owner.getUserType()).thenReturn(UserType.ADMIN);

        DriveFile driveFile = FileTestUtils.mockFile(owner);
        DriveFolder driveFolder = FolderTestUtils.mockFolderWithUser(owner);

        SecurityUtils.checkUserRightOn(owner, other);
        SecurityUtils.checkUserRightOn(owner, driveFile);
        SecurityUtils.checkUserRightOn(owner, driveFolder);
    }
}
