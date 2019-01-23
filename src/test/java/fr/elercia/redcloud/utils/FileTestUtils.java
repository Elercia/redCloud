package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import org.mockito.Mockito;

import java.util.Date;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTestUtils {

    public static void checkEquals(File expected, File actual) {

    }

    public static File mockFile() {
        UUID fileUUID = UUID.randomUUID();
        UUID userUUID = UUID.randomUUID();

        User user = Mockito.mock(User.class);
        Mockito.when(user.getResourceId()).thenReturn(userUUID);

        Directory directory = Mockito.mock(Directory.class);
        Mockito.when(directory.getUser()).thenReturn(user);

        File fileEntity = Mockito.mock(File.class);
        Mockito.when(fileEntity.getResourceId()).thenReturn(fileUUID);
        Mockito.when(fileEntity.getDirectory()).thenReturn(directory);

        return fileEntity;
    }
}
