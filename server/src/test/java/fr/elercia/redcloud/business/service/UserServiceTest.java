package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.api.dto.entity.UpdateUserDto;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.events.UserCreationEvent;
import fr.elercia.redcloud.business.events.UserDeleteEvent;
import fr.elercia.redcloud.dao.repository.DriveFolderRepository;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.utils.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private ApplicationEventPublisher applicationEventPublisher;

    @Autowired
    @InjectMocks
    private UserService userService;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);
        Mockito.when(userRepository.save(ArgumentMatchers.any())).thenAnswer((invocation) -> invocation.getArguments()[0]);
    }

    @Test
    void userEntity_lifeCycle() throws InvalidUserCreationException {

        CreateUserDto wantedUser = new CreateUserDto("name", "password");
        User createdUser = userService.createUser(wantedUser);

        assertNotNull(createdUser);

        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.any());
        Mockito.verify(applicationEventPublisher, Mockito.times(1)).publishEvent(ArgumentMatchers.any(UserCreationEvent.class));
        assertEquals(createdUser.getName(), wantedUser.getName());
        assertEquals(createdUser.getHashedPassword(), PasswordEncoder.encode(wantedUser.getUnHashedPassword()));

        userService.deleteUser(createdUser);
        Mockito.verify(userRepository, Mockito.times(1)).delete(ArgumentMatchers.any());
        Mockito.verify(applicationEventPublisher, Mockito.times(1)).publishEvent(ArgumentMatchers.any(UserDeleteEvent.class));
    }

    @Test
    void userCreate_sameName_expectException() {

        User user = new User("dedede", "password", UserType.USER);
        Mockito.when(userRepository.findByName(user.getName())).thenReturn(user);

        try {
            CreateUserDto wantedUser = new CreateUserDto(user.getName(), "password");
            userService.createUser(wantedUser);
            fail("Mathod create didn't throw exception");
        } catch (InvalidUserCreationException ignored) {
        } catch (Throwable t) {
            fail("Unexpected throw");
            t.printStackTrace();
        }
    }

    @Test
    void userCreate_noName_expectException() {

        assertThrows(InvalidUserCreationException.class, () -> {

            CreateUserDto wantedUser = new CreateUserDto("", "password");
            userService.createUser(wantedUser);
        });
    }

    @Test
    void updateUser_validData_userUpdated() {

        User user = new User("name1", "password", UserType.USER);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        UpdateUserDto updateUserDto = new UpdateUserDto("somename1", "jefhehfefh");
        userService.updateUser(user, updateUserDto);

        Mockito.verify(userRepository).save(argument.capture());
        User updatedUser = argument.getValue();

        User mockedUser = UserTestUtils.mockUser(updateUserDto.getName(), user.getResourceId(), PasswordEncoder.encode(updateUserDto.getPassword()), user.getUserType(), user.getCreationDate(), user.getDriveFolders());
        UserTestUtils.checkEquals(mockedUser, updatedUser);
    }

    @Test
    void updateUser_validData_notUpdated() {

        User user = new User("name1", "password", UserType.USER);
        ArgumentCaptor<User> argument = ArgumentCaptor.forClass(User.class);

        assertNotNull(user);

        UpdateUserDto updateUserDto = new UpdateUserDto(null, null);
        userService.updateUser(user, updateUserDto);

        Mockito.verify(userRepository).save(argument.capture());
        User updatedUser = argument.getValue();

        User mockedUser = UserTestUtils.mockUser(user.getName(), user.getResourceId(), user.getHashedPassword(), user.getUserType(), user.getCreationDate(), user.getDriveFolders());
        UserTestUtils.checkEquals(mockedUser, updatedUser);
    }
}