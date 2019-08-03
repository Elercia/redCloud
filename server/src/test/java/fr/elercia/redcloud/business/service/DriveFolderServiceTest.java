package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.drive.CreateFolderDto;
import fr.elercia.redcloud.api.dto.entity.drive.UpdateFolderDto;
import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.business.service.drive.DriveFolderService;
import fr.elercia.redcloud.dao.repository.DriveFolderRepository;
import fr.elercia.redcloud.exceptions.FolderNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedFolderActionException;
import fr.elercia.redcloud.utils.FolderTestUtils;
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

        driveFolder1 = FolderTestUtils.mockFolder("blabla", dir1ResourceId, null);

        dir1ResourceId = UUID.randomUUID();
        Mockito.when(driveFolderRepository.findByResourceId(dir1ResourceId)).thenReturn(driveFolder1);
        Mockito.when(driveFolderRepository.save(ArgumentMatchers.any(DriveFolder.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void createRootFolder_assertCreated() {

        driveFolderService.createRootFolder(UserTestUtils.mockUser());

        ArgumentCaptor<DriveFolder> argument = ArgumentCaptor.forClass(DriveFolder.class);
        Mockito.verify(driveFolderRepository).save(argument.capture());

        assertEquals("root", argument.getValue().getName());
        assertNotNull(argument.getValue().getResourceId());
        assertNotNull(argument.getValue().getCreationDate());
    }

    @Test
    void findFolder_validResourceId_returnFolder() throws FolderNotFoundException {

        DriveFolder driveFolder = driveFolderService.find(dir1ResourceId);

        assertEquals(driveFolder1.getName(), driveFolder.getName());
        assertEquals(driveFolder1.getResourceId(), driveFolder.getResourceId());
    }

    @Test
    void findFolder_invalidResourceId_throwException() {

        assertThrows(FolderNotFoundException.class, () -> driveFolderService.find(UUID.randomUUID()));
    }

    @Test
    void createSubFolder_assertCreated() throws UnauthorizedFolderActionException {

        DriveFolder parent = FolderTestUtils.mockFolderWithUser("root", null);
        CreateFolderDto createFolderDto = new CreateFolderDto("sub");

        DriveFolder sub = driveFolderService.createSubFolder(parent, createFolderDto);

        assertNotNull(sub);
        assertSame(sub.getParentDriveFolder(), parent);
        assertSame(sub.getUser(), parent.getUser());
    }

    @Test
    void createSubFolder_withSameNameAsSubFolder_throwsException() {

        assertThrows(UnauthorizedFolderActionException.class, () -> {
            DriveFolder parent = FolderTestUtils.mockFolderWithSubFolders("root", null, Arrays.asList(FolderTestUtils.mockFolder("sub", null)));
            CreateFolderDto createFolderDto = new CreateFolderDto("sub");

            DriveFolder sub = driveFolderService.createSubFolder(parent, createFolderDto);

            assertNotNull(sub);
            assertSame(sub.getParentDriveFolder(), parent);
            assertSame(sub.getUser(), parent.getUser());
        });
    }

    @Test
    void createSubFolder_withSameNameUpperCaseAsSubFolder_throwsException() {

        assertThrows(UnauthorizedFolderActionException.class, () -> {
            DriveFolder parent = FolderTestUtils.mockFolderWithSubFolders("root", null, Arrays.asList(FolderTestUtils.mockFolder("SUB", null)));
            CreateFolderDto createFolderDto = new CreateFolderDto("sub");

            DriveFolder sub = driveFolderService.createSubFolder(parent, createFolderDto);

            assertNotNull(sub);
            assertSame(sub.getParentDriveFolder(), parent);
            assertSame(sub.getUser(), parent.getUser());
        });
    }

    @Test
    void deleteFolder_deleteParent_assertThrow() {
        assertThrows(UnauthorizedFolderActionException.class, () -> {
            DriveFolder parent = FolderTestUtils.mockFolderWithSubFolders("root", null, Arrays.asList(FolderTestUtils.mockFolder("SUB", null)));

            driveFolderService.deleteFolder(parent);
        });
    }

    @Test
    void updateFolder_validName_assertUpdated() throws UnauthorizedFolderActionException {
        DriveFolder driveFolder = new DriveFolder("mechim", Mockito.mock(AppUser.class), Mockito.mock(DriveFolder.class));
        UpdateFolderDto updateFolderDto = new UpdateFolderDto("testt");

        driveFolderService.update(driveFolder, updateFolderDto);

        assertEquals(updateFolderDto.getName(), driveFolder.getName());
        Mockito.verify(driveFolderRepository, Mockito.times(1)).save(ArgumentMatchers.any(DriveFolder.class));
    }

    @Test
    void updateFolder_rootDir_assertThrow() {
        assertThrows(UnauthorizedFolderActionException.class, () -> {

            DriveFolder driveFolder = FolderTestUtils.mockFolderWithUser("root", null);
            UpdateFolderDto updateFolderDto = new UpdateFolderDto("testt");

            driveFolderService.update(driveFolder, updateFolderDto);
        });
    }

    @Test
    void moveFolder_dirToAnother_done() throws UnauthorizedFolderActionException {

        DriveFolder driveFolder = FolderTestUtils.mockFolder("dir1", Mockito.mock(DriveFolder.class));
        DriveFolder moveTo = FolderTestUtils.mockFolder("dir2", Mockito.mock(DriveFolder.class));

        driveFolderService.move(driveFolder, moveTo);
    }

    @Test
    void moveFolder_dirToSame_throw() {

        assertThrows(UnauthorizedFolderActionException.class, () -> {
            DriveFolder driveFolder = FolderTestUtils.mockFolder("dir1", UUID.randomUUID(), Mockito.mock(DriveFolder.class));

            driveFolderService.move(driveFolder, driveFolder);
        });
    }

    @Test
    void moveFolder_rootDir_throw() {
        assertThrows(UnauthorizedFolderActionException.class, () -> {
            DriveFolder driveFolder = FolderTestUtils.mockFolder("dir1", UUID.randomUUID(), null);
            DriveFolder moveTo = FolderTestUtils.mockFolder("dir2", Mockito.mock(DriveFolder.class));

            driveFolderService.move(driveFolder, moveTo);
        });
    }

    @Test
    void moveFolder_dirToWithSubFoldersName_throw() {
        assertThrows(UnauthorizedFolderActionException.class, () -> {
            DriveFolder driveFolder = FolderTestUtils.mockFolder("dir1", UUID.randomUUID(), Mockito.mock(DriveFolder.class));
            DriveFolder moveTo = FolderTestUtils.mockFolder("dir2", Mockito.mock(DriveFolder.class));

            List<DriveFolder> subFolder = Arrays.asList(FolderTestUtils.mockFolder("dir1", null));
            Mockito.when(moveTo.getSubFolders()).thenReturn(subFolder);

            driveFolderService.move(driveFolder, moveTo);
        });
    }
}