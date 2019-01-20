package fr.elercia.redcloud;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.service.PasswordEncoder;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class UserTestUtils {

    public static void checkEquals(User expected, User actual) {

        assertEquals(expected.getName(), actual.getName());
        assertEquals(expected.getResourceId(), actual.getResourceId());
        assertEquals(expected.getHashedPassword(), actual.getHashedPassword());
        assertEquals(expected.getUserType(), actual.getUserType());
        assertEquals(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getDirectories().size(), actual.getDirectories().size());
    }

    public static User mockUser(String name, UUID resourceId, String password, UserType userType, Date creationDate, List<Directory> directories) {
        User user = Mockito.mock(User.class);

        Mockito.when(user.getName()).thenReturn(name);
        Mockito.when(user.getResourceId()).thenReturn(resourceId);
        Mockito.when(user.getHashedPassword()).thenReturn(password);
        Mockito.when(user.getUserType()).thenReturn(userType);
        Mockito.when(user.getCreationDate()).thenReturn(creationDate);
        Mockito.when(user.getDirectories()).thenReturn(directories);

        return user;
    }
}
