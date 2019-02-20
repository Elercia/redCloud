package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UpdateDirectoryDto;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.repository.DriveFolderRepository;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedDirectoryActionException;
import fr.elercia.redcloud.utils.DirectoryTestUtils;
import fr.elercia.redcloud.utils.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class DriveFolderServiceTest {

    @Mock
    private DriveFolderRepository driveFolderRepository;

    @Autowired
    @InjectMocks
    private DriveFolderService driveFolderService;

    private UUID dir1ResourceId;
    private DriveFolder driveFolder1;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);

        driveFolder1 = DirectoryTestUtils.mockDirectory("blabla", dir1ResourceId, null);

        dir1ResourceId = UUID.randomUUID();
        Mockito.when(driveFolderRepository.findByResourceId(dir1ResourceId)).thenReturn(driveFolder1);
        Mockito.when(driveFolderRepository.save(ArgumentMatchers.any(DriveFolder.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void createRootDirectory_assertCreated() {

        driveFolderService.createRootDirectory(UserTestUtils.mockUser());

        ArgumentCaptor<DriveFolder> argument = ArgumentCaptor.forClass(DriveFolder.class);
        Mockito.verify(driveFolderRepository).save(argument.capture());

        assertEquals("root", argument.getValue().getName());
        assertNotNull(argument.getValue().getResourceId());
        assertNotNull(argument.getValue().getCreationDate());
    }

    @Test
    void findDirectory_validResourceId_returnDirectory() throws DirectoryNotFoundException {

        DriveFolder driveFolder = driveFolderService.find(dir1ResourceId);

        assertEquals(driveFolder1.getName(), driveFolder.getName());
        assertEquals(driveFolder1.getResourceId(), driveFolder.getResourceId());
    }

    @Test
    void findDirectory_invalidResourceId_throwException() {

        assertThrows(DirectoryNotFoundException.class, () -> driveFolderService.find(UUID.randomUUID()));
    }

    @Test
    void createSubDirectory_assertCreated() throws UnauthorizedDirectoryActionException {

        DriveFolder parent = DirectoryTestUtils.mockDirectoryWithUser("root", null);
        CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto("sub");

        DriveFolder sub = driveFolderService.createSubDirectory(parent, createDirectoryDto);

        assertNotNull(sub);
        assertSame(sub.getParentDriveFolder(), parent);
        assertSame(sub.getUser(), parent.getUser());
    }

    @Test
    void createSubDirectory_withSameNameAsSubFolder_throwsException() {

        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            DriveFolder parent = DirectoryTestUtils.mockDirectoryWithSubFolders("root", null, Arrays.asList(DirectoryTestUtils.mockDirectory("sub", null)));
            CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto("sub");

            DriveFolder sub = driveFolderService.createSubDirectory(parent, createDirectoryDto);

            assertNotNull(sub);
            assertSame(sub.getParentDriveFolder(), parent);
            assertSame(sub.getUser(), parent.getUser());
        });
    }

    @Test
    void createSubDirectory_withSameNameUpperCaseAsSubFolder_throwsException() {

        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            DriveFolder parent = DirectoryTestUtils.mockDirectoryWithSubFolders("root", null, Arrays.asList(DirectoryTestUtils.mockDirectory("SUB", null)));
            CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto("sub");

            DriveFolder sub = driveFolderService.createSubDirectory(parent, createDirectoryDto);

            assertNotNull(sub);
            assertSame(sub.getParentDriveFolder(), parent);
            assertSame(sub.getUser(), parent.getUser());
        });
    }

    @Test
    void deleteDirectory_deleteParent_assertThrow() {
        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            DriveFolder parent = DirectoryTestUtils.mockDirectoryWithSubFolders("root", null, Arrays.asList(DirectoryTestUtils.mockDirectory("SUB", null)));

            driveFolderService.deleteDirectory(parent);
        });
    }

    @Test
    void updateDirectory_validName_assertUpdated() throws UnauthorizedDirectoryActionException {
        DriveFolder driveFolder = new DriveFolder("mechim", Mockito.mock(User.class), Mockito.mock(DriveFolder.class));
        UpdateDirectoryDto updateDirectoryDto = new UpdateDirectoryDto("testt");

        driveFolderService.update(driveFolder, updateDirectoryDto);

        assertEquals(updateDirectoryDto.getName(), driveFolder.getName());
        Mockito.verify(driveFolderRepository, Mockito.times(1)).save(ArgumentMatchers.any(DriveFolder.class));
    }

    @Test
    void updateDirectory_rootDir_assertThrow() {
        assertThrows(UnauthorizedDirectoryActionException.class, () -> {

            DriveFolder driveFolder = DirectoryTestUtils.mockDirectoryWithUser("root", null);
            UpdateDirectoryDto updateDirectoryDto = new UpdateDirectoryDto("testt");

            driveFolderService.update(driveFolder, updateDirectoryDto);
        });
    }

    @Test
    void moveDirectory_dirToAnother_done() throws UnauthorizedDirectoryActionException {

        DriveFolder driveFolder = DirectoryTestUtils.mockDirectory("dir1", Mockito.mock(DriveFolder.class));
        DriveFolder moveTo = DirectoryTestUtils.mockDirectory("dir2", Mockito.mock(DriveFolder.class));

        driveFolderService.move(driveFolder, moveTo);
    }

    @Test
    void moveDirectory_dirToSame_throw() {

        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            DriveFolder driveFolder = DirectoryTestUtils.mockDirectory("dir1", UUID.randomUUID(), Mockito.mock(DriveFolder.class));

            driveFolderService.move(driveFolder, driveFolder);
        });
    }

    @Test
    void moveDirectory_rootDir_throw() {
        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            DriveFolder driveFolder = DirectoryTestUtils.mockDirectory("dir1", UUID.randomUUID(), null);
            DriveFolder moveTo = DirectoryTestUtils.mockDirectory("dir2", Mockito.mock(DriveFolder.class));

            driveFolderService.move(driveFolder, moveTo);
        });
    }

    @Test
    void moveDirectory_dirToWithSubFoldersName_throw() {
        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            DriveFolder driveFolder = DirectoryTestUtils.mockDirectory("dir1", UUID.randomUUID(), Mockito.mock(DriveFolder.class));
            DriveFolder moveTo = DirectoryTestUtils.mockDirectory("dir2", Mockito.mock(DriveFolder.class));

            List<DriveFolder> subFolder = Arrays.asList(DirectoryTestUtils.mockDirectory("dir1", null));
            Mockito.when(moveTo.getSubFolders()).thenReturn(subFolder);

            driveFolderService.move(driveFolder, moveTo);
        });
    }
}