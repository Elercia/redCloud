package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTestUtils {

    public static void checkEquals(AppUser expected, AppUser actual) {

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getResourceId(), actual.getResourceId());
        assertEquals(expected.getUserType(), actual.getUserType());
        assertEquals(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getDriveFolders().size(), actual.getDriveFolders().size());
    }

    public static AppUser mockUser() {
        return mockUser("name2", UUID.randomUUID(), UserType.USER, new Date(), new ArrayList<>());
    }

    public static AppUser mockUser(String name, UUID resourceId, UserType userType, Date creationDate, List<DriveFolder> folders) {
        AppUser user = Mockito.mock(AppUser.class);

        Mockito.when(user.getName()).thenReturn(name);
        Mockito.when(user.getResourceId()).thenReturn(resourceId);
        Mockito.when(user.getUserType()).thenReturn(userType);
        Mockito.when(user.getCreationDate()).thenReturn(creationDate);
        Mockito.when(user.getDriveFolders()).thenReturn(folders);

        return user;
    }
}
