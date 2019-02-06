package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.exceptions.UnauthorizedActionException;
import fr.elercia.redcloud.utils.DirectoryTestUtils;
import fr.elercia.redcloud.utils.FileTestUtils;
import fr.elercia.redcloud.utils.UserTestUtils;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class ResourcesSecurityAccessTest {

    @Test
    void checkUserRightOnFile_rightUser_accept() {

        User owner = UserTestUtils.mockUser();
        Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

        File file = FileTestUtils.mockFile(owner);
        SecurityUtils.checkUserRightOn(owner, file);
    }

    @Test
    void checkUserRightOnFile_wrongUser_throw() {

        assertThrows(UnauthorizedActionException.class, () -> {

            User owner = UserTestUtils.mockUser();
            User other = UserTestUtils.mockUser();
            Mockito.when(other.getUserType()).thenReturn(UserType.USER);

            File file = FileTestUtils.mockFile(owner);
            SecurityUtils.checkUserRightOn(other, file);
        });
    }

    @Test
    void checkUserRightOnDirectory_rightUser_accept() {

        User owner = UserTestUtils.mockUser();
        Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

        Directory directory = DirectoryTestUtils.mockDirectoryWithUser(owner);
        SecurityUtils.checkUserRightOn(owner, directory);
    }

    @Test
    void checkUserRightOnDirectory_wrongUser_throw() {

        assertThrows(UnauthorizedActionException.class, () -> {

            User owner = UserTestUtils.mockUser();
            User other = UserTestUtils.mockUser();

            Mockito.when(other.getUserType()).thenReturn(UserType.USER);

            Directory directory = DirectoryTestUtils.mockDirectoryWithUser(owner);
            SecurityUtils.checkUserRightOn(other, directory);
        });
    }

    @Test
    void checkUserRightOnUser_rightUser_accept() {

        User owner = UserTestUtils.mockUser();

        Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

        SecurityUtils.checkUserRightOn(owner, owner);
    }

    @Test
    void checkUserRightOnUser_wrongUser_throw() {

        assertThrows(UnauthorizedActionException.class, () -> {

            User owner = UserTestUtils.mockUser();
            User other = UserTestUtils.mockUser();

            Mockito.when(owner.getUserType()).thenReturn(UserType.USER);

            SecurityUtils.checkUserRightOn(owner, other);
        });
    }

    @Test
    void checkAdminRights_everythingAccepted() {

        User owner = UserTestUtils.mockUser();
        User other = UserTestUtils.mockUser();

        Mockito.when(owner.getUserType()).thenReturn(UserType.ADMIN);

        File file = FileTestUtils.mockFile(owner);
        Directory directory = DirectoryTestUtils.mockDirectoryWithUser(owner);

        SecurityUtils.checkUserRightOn(owner, other);
        SecurityUtils.checkUserRightOn(owner, file);
        SecurityUtils.checkUserRightOn(owner, directory);
    }
}
