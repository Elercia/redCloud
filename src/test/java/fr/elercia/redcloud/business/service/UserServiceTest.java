package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.UserTestUtils;
import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.api.dto.entity.UpdateUserDto;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static fr.elercia.redcloud.TestUtils.userRootDirectoryCreated;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Test
    void userEntity_lifeCycle() throws InvalidUserCreationException {

        CreateUserDto wantedUser = new CreateUserDto("name", "password");
        User createdUser = userService.createUser(wantedUser);

        assertNotNull(createdUser);
        assertTrue(userRootDirectoryCreated(createdUser));
        assertEquals(createdUser.getName(), wantedUser.getName());
        assertEquals(createdUser.getHashedPassword(), PasswordEncoder.encode(wantedUser.getUnHashedPassword()));

        userService.deleteUser(createdUser);
        assertFalse(userRootDirectoryCreated(createdUser));
    }

    @Test
    void userCreate_sameName_expectException() {

        assertThrows(InvalidUserCreationException.class, () -> {
            User user = new User("dedede", "password", UserType.USER);
            user = userRepository.save(user);

            CreateUserDto wantedUser = new CreateUserDto(user.getName(), "password");
            userService.createUser(wantedUser);
        });
    }

    /**
     * This test require one createdUser with the name testUser1
     */
    @Test
    void fetchUser_updateUser_userUpdated() throws UserNotFoundException {

        User user = userService.findByName("testUser1");

        assertNotNull(user);

        UpdateUserDto updateUserDto = new UpdateUserDto("somename1", "jefhehfefh");
        User updatedUser = userService.updateUser(user, updateUserDto);

        User mockedUser = UserTestUtils.mockUser(updateUserDto.getName(), user.getResourceId(), PasswordEncoder.encode(updateUserDto.getPassword()), user.getUserType(), user.getCreationDate(), user.getDirectories());
        UserTestUtils.checkEquals(mockedUser, updatedUser);
    }

    /**
     * This test require one createdUser with the name testUser1
     */
    @Test
    void fetchUser_updateWithNull_notUpdated() throws UserNotFoundException {

        User user = userService.findByName("testUser1");

        assertNotNull(user);

        UpdateUserDto updateUserDto = new UpdateUserDto(null, null);
        User updatedUser = userService.updateUser(user, updateUserDto);

        User mockedUser = UserTestUtils.mockUser(user.getName(), user.getResourceId(), user.getHashedPassword(), user.getUserType(), user.getCreationDate(), user.getDirectories());
        UserTestUtils.checkEquals(mockedUser, updatedUser);
    }
}