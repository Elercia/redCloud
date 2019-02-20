package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.service.utils.StringUtils;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTestUtils {

    public static void checkEquals(File expected, File actual) {
        assertEquals(expected.getFileName(), actual.getFileName());
        assertEquals(expected.getResourceId(), actual.getResourceId());
        assertEquals(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getSize(), actual.getSize());
    }

    public static File mockFile() {
        return mockFile(UUID.randomUUID(), StringUtils.randomString(), UserTestUtils.mockUser());
    }

    public static File mockFile(UUID uuid) {
        return mockFile(uuid, "f2", UserTestUtils.mockUser());
    }

    public static File mockFile(String name) {
        return mockFile(UUID.randomUUID(), name, UserTestUtils.mockUser());
    }

    public static File mockFile(User mockUser) {
        return mockFile(UUID.randomUUID(), "f2", mockUser);
    }

    public static File mockFile(UUID fileUUID, String name, User mockUser) {

        Directory directory = Mockito.mock(Directory.class);
        Mockito.when(directory.getUser()).thenReturn(mockUser);

        File fileEntity = Mockito.mock(File.class);
        Mockito.when(fileEntity.getResourceId()).thenReturn(fileUUID);
        Mockito.when(fileEntity.getDirectory()).thenReturn(directory);
        Mockito.when(fileEntity.getFileName()).thenReturn(name);

        return fileEntity;
    }
}
