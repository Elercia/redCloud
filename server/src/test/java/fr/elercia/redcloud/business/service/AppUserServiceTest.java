package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.SimpleUserDto;
import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.events.UserCreationEvent;
import fr.elercia.redcloud.business.events.UserDeleteEvent;
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
class AppUserServiceTest {

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

        SimpleUserDto wantedUser = new SimpleUserDto("name");
        AppUser createdUser = userService.createUser(wantedUser);

        assertNotNull(createdUser);

        Mockito.verify(userRepository, Mockito.times(1)).save(ArgumentMatchers.any());
        Mockito.verify(applicationEventPublisher, Mockito.times(1)).publishEvent(ArgumentMatchers.any(UserCreationEvent.class));
        assertEquals(createdUser.getName(), wantedUser.getName());

        userService.deleteUser(createdUser);
        Mockito.verify(userRepository, Mockito.times(1)).delete(ArgumentMatchers.any());
        Mockito.verify(applicationEventPublisher, Mockito.times(1)).publishEvent(ArgumentMatchers.any(UserDeleteEvent.class));
    }

    @Test
    void userCreate_sameName_expectException() {

        AppUser user = new AppUser("dedede", UserType.USER);
        Mockito.when(userRepository.findByName(user.getName())).thenReturn(user);

        try {
            SimpleUserDto wantedUser = new SimpleUserDto(user.getName());
            userService.createUser(wantedUser);
            fail("Mathod create didn't throw exception");
        } catch (InvalidUserCreationException ignored) {
        } catch (Throwable t) {
            t.printStackTrace();
            fail("Unexpected throw");
        }
    }

    @Test
    void userCreate_noName_expectException() {

        assertThrows(InvalidUserCreationException.class, () -> {

            SimpleUserDto wantedUser = new SimpleUserDto("");
            userService.createUser(wantedUser);
        });
    }

    @Test
    void updateUser_validData_userUpdated() {

        AppUser user = new AppUser("name1", UserType.USER);
        ArgumentCaptor<AppUser> argument = ArgumentCaptor.forClass(AppUser.class);

        SimpleUserDto updateUserDto = new SimpleUserDto("somename1");
        userService.updateUser(user, updateUserDto);

        Mockito.verify(userRepository).save(argument.capture());
        AppUser updatedUser = argument.getValue();

        AppUser mockedUser = UserTestUtils.mockUser(updateUserDto.getName(), user.getResourceId(), user.getUserType(), user.getCreationDate(), user.getDriveFolders());
        UserTestUtils.checkEquals(mockedUser, updatedUser);
    }

    @Test
    void updateUser_validData_notUpdated() {

        AppUser user = new AppUser("name1", UserType.USER);
        ArgumentCaptor<AppUser> argument = ArgumentCaptor.forClass(AppUser.class);

        assertNotNull(user);

        SimpleUserDto updateUserDto = new SimpleUserDto(null);
        userService.updateUser(user, updateUserDto);

        Mockito.verify(userRepository).save(argument.capture());
        AppUser updatedUser = argument.getValue();

        AppUser mockedUser = UserTestUtils.mockUser(user.getName(), user.getResourceId(), user.getUserType(), user.getCreationDate(), user.getDriveFolders());
        UserTestUtils.checkEquals(mockedUser, updatedUser);
    }
}