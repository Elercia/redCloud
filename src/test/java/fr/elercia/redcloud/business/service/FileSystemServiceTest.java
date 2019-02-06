package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import fr.elercia.redcloud.utils.FileTestUtils;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.Resource;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.management.MalformedObjectNameException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertArrayEquals;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class FileSystemServiceTest {

    @Autowired
    private FileSystemService fileSystemService;

    @Test
    void uploadDownload_assertSameContent() throws IOException, FileStorageException, FileNotFoundException, MalformedObjectNameException {

        String fileName = "testUploadFile.txt";
        String originalFileName = "testUploadFile.txt";
        String contentType = "text/plain";
        byte[] content = Files.readAllBytes(Paths.get(new java.io.File(this.getClass().getClassLoader().getResource("testUploadFile.txt").getFile()).getAbsolutePath()));

        MultipartFile multipartFile = new MockMultipartFile(fileName, originalFileName, contentType, content);

        File fileEntity = FileTestUtils.mockFile();

        fileSystemService.createUserFileSystemSpace(fileEntity.getDirectory().getUser());

        fileSystemService.uploadFile(multipartFile, fileEntity);

        Resource resource = fileSystemService.download(fileEntity);
        byte[] downloadedContent = resource.getInputStream().readAllBytes();

        assertArrayEquals(content, downloadedContent);

        // This is a trick to force Java to close the file an allow the delete operation
        resource = null;
        System.gc();

        fileSystemService.deleteUserFileSystem(fileEntity.getDirectory().getUser());
    }
}