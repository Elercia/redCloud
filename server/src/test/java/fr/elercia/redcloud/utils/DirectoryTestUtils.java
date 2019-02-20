package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.entity.User;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

public class DirectoryTestUtils {

    public static void checkEquals(DriveFolder expected, DriveFolder actual) {

    }

    public static DriveFolder mockDirectory() {
        return Mockito.mock(DriveFolder.class);
    }

    public static DriveFolder mockDirectoryWithFiles(String name, DriveFolder parent, List<DriveFile> driveFiles) {
        DriveFolder driveFolder = mockDirectoryWithUser(name, parent);

        Mockito.when(driveFolder.getDriveFiles()).thenReturn(driveFiles);

        return driveFolder;
    }

    public static DriveFolder mockDirectoryWithSubFolders(String name, DriveFolder parent, List<DriveFolder> subFolders) {
        DriveFolder driveFolder = mockDirectoryWithUser(name, parent);

        Mockito.when(driveFolder.getSubFolders()).thenReturn(subFolders);
        subFolders.forEach(sb -> {
            Mockito.when(sb.getParentDriveFolder()).thenReturn(driveFolder);
        });

        return driveFolder;
    }

    public static DriveFolder mockDirectoryWithUser(String name, DriveFolder parent) {
        DriveFolder driveFolder = mockDirectory(name, parent);

        Mockito.when(driveFolder.getUser()).thenReturn(Mockito.mock(User.class));

        return driveFolder;
    }

    public static DriveFolder mockDirectoryWithUser(User user) {
        DriveFolder driveFolder = mockDirectory("dir1", UUID.randomUUID(), null);

        Mockito.when(driveFolder.getUser()).thenReturn(user);

        return driveFolder;
    }

    public static DriveFolder mockDirectory(String name, DriveFolder parent) {
        return mockDirectory(name, UUID.randomUUID(), parent);
    }

    public static DriveFolder mockDirectory(String name, UUID resourceId, DriveFolder parent) {
        DriveFolder driveFolder = Mockito.mock(DriveFolder.class);

        Mockito.when(driveFolder.getName()).thenReturn(name);
        Mockito.when(driveFolder.getResourceId()).thenReturn(resourceId);
        Mockito.when(driveFolder.getParentDriveFolder()).thenReturn(parent);

        return driveFolder;
    }
}
