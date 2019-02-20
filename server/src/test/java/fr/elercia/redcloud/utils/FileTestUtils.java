package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.service.utils.StringUtils;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class FileTestUtils {

    public static void checkEquals(DriveFile expected, DriveFile actual) {
        assertEquals(expected.getFileName(), actual.getFileName());
        assertEquals(expected.getResourceId(), actual.getResourceId());
        assertEquals(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getSize(), actual.getSize());
    }

    public static DriveFile mockFile() {
        return mockFile(UUID.randomUUID(), StringUtils.randomString(), UserTestUtils.mockUser());
    }

    public static DriveFile mockFile(UUID uuid) {
        return mockFile(uuid, "f2", UserTestUtils.mockUser());
    }

    public static DriveFile mockFile(String name) {
        return mockFile(UUID.randomUUID(), name, UserTestUtils.mockUser());
    }

    public static DriveFile mockFile(User mockUser) {
        return mockFile(UUID.randomUUID(), "f2", mockUser);
    }

    public static DriveFile mockFile(UUID fileUUID, String name, User mockUser) {

        DriveFolder driveFolder = Mockito.mock(DriveFolder.class);
        Mockito.when(driveFolder.getUser()).thenReturn(mockUser);

        DriveFile driveFileEntity = Mockito.mock(DriveFile.class);
        Mockito.when(driveFileEntity.getResourceId()).thenReturn(fileUUID);
        Mockito.when(driveFileEntity.getParent()).thenReturn(driveFolder);
        Mockito.when(driveFileEntity.getFileName()).thenReturn(name);

        return driveFileEntity;
    }
}
