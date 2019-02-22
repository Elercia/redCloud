package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.dao.repository.FileRepository;
import fr.elercia.redcloud.exceptions.FileNameFormatException;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileOperationException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import fr.elercia.redcloud.utils.FileTestUtils;
import fr.elercia.redcloud.utils.FolderTestUtils;
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
class DriveDriveFileServiceTest {

    @Mock
    private DriveFileSystemService driveFileSystemService;

    @Mock
    private FileRepository fileRepository;

    @Autowired
    @InjectMocks
    private DriveFileService driveFileService;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void find_knownId_returnExpected() throws FileNotFoundException {

        UUID fileId = UUID.randomUUID();
        DriveFile expectedDriveFile = FileTestUtils.mockFile(fileId);
        Mockito.when(fileRepository.findByResourceId(fileId)).thenReturn(expectedDriveFile);

        DriveFile returnedDriveFile = driveFileService.find(fileId);

        FileTestUtils.checkEquals(expectedDriveFile, returnedDriveFile);
    }

    @Test
    void find_unknownId_throw() {

        assertThrows(FileNotFoundException.class, () -> {
            UUID fileId = UUID.randomUUID();

            driveFileService.find(fileId);
        });
    }

    @Test
    void delete_expectDeleteCall() {
        DriveFile driveFile = FileTestUtils.mockFile();

        driveFileService.delete(driveFile);

        Mockito.verify(fileRepository, Mockito.times(1)).delete(driveFile);
    }

    @Test
    void move_validFolder_moveDone() throws FileOperationException {

        DriveFile driveFile = FileTestUtils.mockFile();
        DriveFolder driveFolder = FolderTestUtils.mockFolderWithSubFolders("name", null, new ArrayList<>());

        driveFileService.move(driveFile, driveFolder);

        ArgumentCaptor<DriveFolder> directoryArgumentCaptor = ArgumentCaptor.forClass(DriveFolder.class);
        ArgumentCaptor<DriveFile> fileArgumentCaptor = ArgumentCaptor.forClass(DriveFile.class);

        Mockito.verify(driveFile, Mockito.times(1)).setParent(directoryArgumentCaptor.capture());
        Mockito.verify(fileRepository, Mockito.times(1)).save(fileArgumentCaptor.capture());

        DriveFolder actualDriveFolder = directoryArgumentCaptor.getValue();
        DriveFile actualDriveFile = fileArgumentCaptor.getValue();

        assertEquals(driveFolder.getResourceId(), actualDriveFolder.getResourceId());
        assertSame(driveFile, actualDriveFile);
    }

    @Test
    void move_directoryFilesWithSameName_throw() {

        assertThrows(FileOperationException.class, () -> {

            DriveFile driveFile = FileTestUtils.mockFile("filename1");
            DriveFolder driveFolder = FolderTestUtils.mockFolderWithFiles("name", null, Arrays.asList(FileTestUtils.mockFile(driveFile.getFileName())));

            driveFileService.move(driveFile, driveFolder);
        });
    }

    @ParameterizedTest
    @MethodSource("createValidParametersForStoreFile")
    void storeFile_withNameParameter_valid(String fileName) throws FileNameFormatException, FileStorageException {

        DriveFolder parent = FolderTestUtils.mockFolder();
        MultipartFile multipartFile = new MockMultipartFile(fileName, new byte[0]);

        driveFileService.storeFile(parent, multipartFile);

        Mockito.verify(driveFileSystemService, Mockito.times(1)).uploadFile(ArgumentMatchers.any(), ArgumentMatchers.any());
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
            DriveFolder parent = FolderTestUtils.mockFolder();
            MultipartFile multipartFile = new MockMultipartFile(fileName, new byte[0]);

            driveFileService.storeFile(parent, multipartFile);
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