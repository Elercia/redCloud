package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@Transactional
class FileSystemServiceTest {

    @Autowired
    private FileSystemService fileSystemService;

    @Test
    void uploadDownload_assertSameContent() throws IOException, FileStorageException, FileNotFoundException {

        String fileName = "testUploadFile.txt";
        String originalFileName = "testUploadFile.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(Paths.get(new java.io.File(this.getClass().getClassLoader().getResource("testUploadFile.txt").getFile()).getAbsolutePath()));

        MultipartFile multipartFile = new MockMultipartFile(fileName, originalFileName, contentType, content);

        File fileEntity = mockFile();

        fileSystemService.createUserFileSystemSpace(fileEntity.getDirectory().getUser());

        fileSystemService.uploadFile(multipartFile, fileEntity);

        Resource resource = fileSystemService.download(fileEntity);
        byte[] downloadedContent = resource.getInputStream().readAllBytes();
        resource.getInputStream().close();

        assertArrayEquals(content, downloadedContent);

        //TODO this thing don't work
//        fileSystemService.deleteUserFileSystem(fileEntity.getDirectory().getUser());
    }

    private File mockFile() {

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