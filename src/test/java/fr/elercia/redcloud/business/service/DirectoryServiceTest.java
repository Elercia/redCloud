package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UpdateDirectoryDto;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DirectoryServiceTest {

    @Mock
    private DirectoryRepository directoryRepository;

    @Autowired
    @InjectMocks
    private DirectoryService directoryService;

    private UUID dir1ResourceId;
    private Directory directory1;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);

        directory1 = DirectoryTestUtils.mockDirectory("blabla", dir1ResourceId, null);

        dir1ResourceId = UUID.randomUUID();
        Mockito.when(directoryRepository.findByResourceId(dir1ResourceId)).thenReturn(directory1);
        Mockito.when(directoryRepository.save(ArgumentMatchers.any(Directory.class))).thenAnswer(i -> i.getArguments()[0]);
    }

    @Test
    void createRootDirectory_assertCreated() {

        directoryService.createRootDirectory(UserTestUtils.mockUser());

        ArgumentCaptor<Directory> argument = ArgumentCaptor.forClass(Directory.class);
        Mockito.verify(directoryRepository).save(argument.capture());

        assertEquals("root", argument.getValue().getName());
        assertNotNull(argument.getValue().getResourceId());
        assertNotNull(argument.getValue().getCreationDate());
    }

    @Test
    void findDirectory_validResourceId_returnDirectory() throws DirectoryNotFoundException {

        Directory directory = directoryService.find(dir1ResourceId);

        assertEquals(directory1.getName(), directory.getName());
        assertEquals(directory1.getResourceId(), directory.getResourceId());
    }

    @Test
    void findDirectory_invalidResourceId_throwException() {

        assertThrows(DirectoryNotFoundException.class, () -> directoryService.find(UUID.randomUUID()));
    }

    @Test
    void createSubDirectory_assertCreated() throws UnauthorizedDirectoryActionException {

        Directory parent = DirectoryTestUtils.mockDirectoryWithUser("root", null);
        CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto("sub");

        Directory sub = directoryService.createSubDirectory(parent, createDirectoryDto);

        assertNotNull(sub);
        assertSame(sub.getParentDirectory(), parent);
        assertSame(sub.getUser(), parent.getUser());
    }

    @Test
    void createSubDirectory_withSameNameAsSubFolder_throwsException() {

        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            Directory parent = DirectoryTestUtils.mockDirectoryWithSubFolders("root", null, Arrays.asList(DirectoryTestUtils.mockDirectory("sub", null)));
            CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto("sub");

            Directory sub = directoryService.createSubDirectory(parent, createDirectoryDto);

            assertNotNull(sub);
            assertSame(sub.getParentDirectory(), parent);
            assertSame(sub.getUser(), parent.getUser());
        });
    }

    @Test
    void createSubDirectory_withSameNameUpperCaseAsSubFolder_throwsException() {

        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            Directory parent = DirectoryTestUtils.mockDirectoryWithSubFolders("root", null, Arrays.asList(DirectoryTestUtils.mockDirectory("SUB", null)));
            CreateDirectoryDto createDirectoryDto = new CreateDirectoryDto("sub");

            Directory sub = directoryService.createSubDirectory(parent, createDirectoryDto);

            assertNotNull(sub);
            assertSame(sub.getParentDirectory(), parent);
            assertSame(sub.getUser(), parent.getUser());
        });
    }

    @Test
    void deleteDirectory_deleteParent_assertThrow() {
        assertThrows(UnauthorizedDirectoryActionException.class, () -> {
            Directory parent = DirectoryTestUtils.mockDirectoryWithSubFolders("root", null, Arrays.asList(DirectoryTestUtils.mockDirectory("SUB", null)));

            directoryService.deleteDirectory(parent);
        });
    }

    @Test
    void updateDirectory_validName_assertUpdated() throws UnauthorizedDirectoryActionException {
        Directory directory = new Directory("mechim", Mockito.mock(User.class), Mockito.mock(Directory.class));
        UpdateDirectoryDto updateDirectoryDto = new UpdateDirectoryDto("testt");

        directoryService.update(directory, updateDirectoryDto);

        assertEquals(updateDirectoryDto.getName(), directory.getName());
        Mockito.verify(directoryRepository, Mockito.times(1)).save(ArgumentMatchers.any(Directory.class));
    }

    @Test
    void updateDirectory_rootDir_assertThrow() {
        assertThrows(UnauthorizedDirectoryActionException.class, () -> {

            Directory directory = DirectoryTestUtils.mockDirectoryWithUser("root", null);
            UpdateDirectoryDto updateDirectoryDto = new UpdateDirectoryDto("testt");

            directoryService.update(directory, updateDirectoryDto);
        });
    }

    @Test
    void moveDirectory_dirToAnother_done() {

    }

    @Test
    void moveDirectory_dirToSame_throw() {

    }

    @Test
    void moveDirectory_rootDir_throw() {

    }

    @Test
    void moveDirectory_dirToWithSubFoldersName_throw() {

    }
}