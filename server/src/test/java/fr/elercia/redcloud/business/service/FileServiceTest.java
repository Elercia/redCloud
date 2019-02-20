package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.dao.repository.FileRepository;
import fr.elercia.redcloud.exceptions.FileNameFormatException;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileOperationException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import fr.elercia.redcloud.utils.DirectoryTestUtils;
import fr.elercia.redcloud.utils.FileTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.UUID;
import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class FileServiceTest {

    @Mock
    private FileSystemService fileSystemService;

    @Mock
    private FileRepository fileRepository;

    @Autowired
    @InjectMocks
    private FileService fileService;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void find_knownId_returnExpected() throws FileNotFoundException {

        UUID fileId = UUID.randomUUID();
        File expectedFile = FileTestUtils.mockFile(fileId);
        Mockito.when(fileRepository.findByResourceId(fileId)).thenReturn(expectedFile);

        File returnedFile = fileService.find(fileId);

        FileTestUtils.checkEquals(expectedFile, returnedFile);
    }

    @Test
    void find_unknownId_throw() {

        assertThrows(FileNotFoundException.class, () -> {
            UUID fileId = UUID.randomUUID();

            fileService.find(fileId);
        });
    }

    @Test
    void delete_expectDeleteCall() {
        File file = FileTestUtils.mockFile();

        fileService.delete(file);

        Mockito.verify(fileRepository, Mockito.times(1)).delete(file);
    }

    @Test
    void move_validDirectory_moveDone() throws FileOperationException {

        File file = FileTestUtils.mockFile();
        Directory directory = DirectoryTestUtils.mockDirectoryWithSubFolders("name", null, new ArrayList<>());

        fileService.move(file, directory);

        ArgumentCaptor<Directory> directoryArgumentCaptor = ArgumentCaptor.forClass(Directory.class);
        ArgumentCaptor<File> fileArgumentCaptor = ArgumentCaptor.forClass(File.class);

        Mockito.verify(file, Mockito.times(1)).setDirectory(directoryArgumentCaptor.capture());
        Mockito.verify(fileRepository, Mockito.times(1)).save(fileArgumentCaptor.capture());

        Directory actualDirectory = directoryArgumentCaptor.getValue();
        File actualFile = fileArgumentCaptor.getValue();

        assertEquals(directory.getResourceId(), actualDirectory.getResourceId());
        assertSame(file, actualFile);
    }

    @Test
    void move_directoryFilesWithSameName_throw() {

        assertThrows(FileOperationException.class, () -> {

            File file = FileTestUtils.mockFile("filename1");
            Directory directory = DirectoryTestUtils.mockDirectoryWithFiles("name", null, Arrays.asList(FileTestUtils.mockFile(file.getFileName())));

            fileService.move(file, directory);
        });
    }

    @ParameterizedTest
    @MethodSource("createValidParametersForStoreFile")
    void storeFile_withNameParameter_valid(String fileName) throws FileNameFormatException, FileStorageException {

        Directory parent = DirectoryTestUtils.mockDirectory();
        MultipartFile multipartFile = new MockMultipartFile(fileName, new byte[0]);

        fileService.storeFile(parent, multipartFile);

        Mockito.verify(fileSystemService, Mockito.times(1)).uploadFile(ArgumentMatchers.any(), ArgumentMatchers.any());
        Mockito.verify(fileRepository, Mockito.times(1)).save(ArgumentMatchers.any());
    }

    private static Stream<String> createValidParametersForStoreFile() {
        return Stream.of(
                "file",
                "file-",
                "file-file.jpg",
                "s38s.jpg.jpg",
                "DhDhDi677envF.jfjir.BURFGRU",
                "xdtcfjknnhgfdfsrfxdg",
                "xdtcfjknnhgfdfsrfxdg:2",
                "dd-dd-dddw-wwd",
                "fnrjf f232rknfrnf (123)",
                "bla989b blafrjnf_n657efiefn .hpg"
        );
    }

    @ParameterizedTest
    @MethodSource("createInvalidParametersForStoreFile")
    void storeFile_withNameParameter_invalid(String fileName) {

        assertThrows(FileNameFormatException.class, () -> {
            Directory parent = DirectoryTestUtils.mockDirectory();
            MultipartFile multipartFile = new MockMultipartFile(fileName, new byte[0]);

            fileService.storeFile(parent, multipartFile);
        });
    }

    private static Stream<String> createInvalidParametersForStoreFile() {
        return Stream.of(
                "file{",
                "file{",
                "file[",
                "file]",
                "file}"
        );
    }
}