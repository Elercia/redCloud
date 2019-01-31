package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import org.mockito.Mockito;

import java.util.List;
import java.util.UUID;

public class DirectoryTestUtils {

    public static void checkEquals(Directory expected, Directory actual) {

    }

    public static Directory mockDirectory() {
        return Mockito.mock(Directory.class);
    }

    public static Directory mockDirectoryWithFiles(String name, Directory parent, List<File> files) {
        Directory directory = mockDirectoryWithUser(name, parent);

        Mockito.when(directory.getFiles()).thenReturn(files);

        return directory;
    }

    public static Directory mockDirectoryWithSubFolders(String name, Directory parent, List<Directory> subFolders) {
        Directory directory = mockDirectoryWithUser(name, parent);

        Mockito.when(directory.getSubFolders()).thenReturn(subFolders);
        subFolders.forEach(sb -> {
            Mockito.when(sb.getParentDirectory()).thenReturn(directory);
        });

        return directory;
    }

    public static Directory mockDirectoryWithUser(String name, Directory parent) {
        Directory directory = mockDirectory(name, parent);

        Mockito.when(directory.getUser()).thenReturn(Mockito.mock(User.class));

        return directory;
    }

    public static Directory mockDirectoryWithUser(User user) {
        Directory directory = mockDirectory("dir1", UUID.randomUUID(), null);

        Mockito.when(directory.getUser()).thenReturn(user);

        return directory;
    }

    public static Directory mockDirectory(String name, Directory parent) {
        return mockDirectory(name, UUID.randomUUID(), parent);
    }

    public static Directory mockDirectory(String name, UUID resourceId, Directory parent) {
        Directory directory = Mockito.mock(Directory.class);

        Mockito.when(directory.getName()).thenReturn(name);
        Mockito.when(directory.getResourceId()).thenReturn(resourceId);
        Mockito.when(directory.getParentDirectory()).thenReturn(parent);

        return directory;
    }
}
