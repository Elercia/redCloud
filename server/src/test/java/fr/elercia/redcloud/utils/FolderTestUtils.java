package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.entity.User;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

public class FolderTestUtils {

    public static void checkEquals(DriveFolder expected, DriveFolder actual) {

    }

    public static DriveFolder mockFolder() {
        return Mockito.mock(DriveFolder.class);
    }

    public static DriveFolder mockFolderWithFiles(String name, DriveFolder parent, List<DriveFile> driveFiles) {
        DriveFolder driveFolder = mockFolderWithUser(name, parent);

        Mockito.when(driveFolder.getDriveFiles()).thenReturn(driveFiles);

        return driveFolder;
    }

    public static DriveFolder mockFolderWithSubFolders(String name, DriveFolder parent, List<DriveFolder> subFolders) {
        DriveFolder driveFolder = mockFolderWithUser(name, parent);

        Mockito.when(driveFolder.getSubFolders()).thenReturn(subFolders);
        subFolders.forEach(sb -> {
            Mockito.when(sb.getParentDriveFolder()).thenReturn(driveFolder);
        });

        return driveFolder;
    }

    public static DriveFolder mockFolderWithUser(String name, DriveFolder parent) {
        DriveFolder driveFolder = mockFolder(name, parent);

        Mockito.when(driveFolder.getUser()).thenReturn(Mockito.mock(User.class));

        return driveFolder;
    }

    public static DriveFolder mockFolderWithUser(User user) {
        DriveFolder driveFolder = mockFolder("dir1", UUID.randomUUID(), null);

        Mockito.when(driveFolder.getUser()).thenReturn(user);

        return driveFolder;
    }

    public static DriveFolder mockFolder(String name, DriveFolder parent) {
        return mockFolder(name, UUID.randomUUID(), parent);
    }

    public static DriveFolder mockFolder(String name, UUID resourceId, DriveFolder parent) {
        DriveFolder driveFolder = Mockito.mock(DriveFolder.class);

        Mockito.when(driveFolder.getName()).thenReturn(name);
        Mockito.when(driveFolder.getResourceId()).thenReturn(resourceId);
        Mockito.when(driveFolder.getParentDriveFolder()).thenReturn(parent);

        return driveFolder;
    }
}
