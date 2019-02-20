package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.api.dto.entity.UpdateUserDto;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.service.utils.StringUtils;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.apache.commons.collections4.IteratorUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private static final Logger LOG = LoggerFactory.getLogger(UserService.class);

    private UserRepository userRepository;
    private DriveFolderService driveFolderService;
    private DriveFileSystemService driveFileSystemService;

    @Autowired
    public UserService(UserRepository userRepository, DriveFolderService driveFolderService, DriveFileSystemService driveFileSystemService) {
        this.userRepository = userRepository;
        this.driveFolderService = driveFolderService;
        this.driveFileSystemService = driveFileSystemService;
    }

    public User findByResourceId(UUID userResourceId) throws UserNotFoundException {

        User user = userRepository.findByResourceId(userResourceId);

        if (user == null) {
            throw new UserNotFoundException("User not found with this resource Id");
        }

        LOG.info("findByResourceId user {}", user.getResourceId());

        return user;
    }

    public User findByName(String name) throws UserNotFoundException {

        User user = userRepository.findByName(name);

        if (user == null) {
            throw new UserNotFoundException();
        }

        LOG.info("findByName user {}", user.getResourceId());

        return user;
    }

    public List<User> findAllUsers() {

        List<User> users = IteratorUtils.toList(userRepository.findAll().iterator());

        LOG.info("findAllUsers users size {}", users.size());

        return users;
    }

    public User createUser(CreateUserDto wantedUser) throws InvalidUserCreationException {

        checkUserCreationValidity(wantedUser);

        String hashedPassword = PasswordEncoder.encode(wantedUser.getUnHashedPassword());

        User newUser = new User(wantedUser.getName(), hashedPassword, UserType.USER);

        LOG.info("createUser user {}", newUser.getResourceId());

        userRepository.save(newUser);
        driveFolderService.createRootDirectory(newUser);
        driveFileSystemService.createUserFileSystemSpace(newUser);

        return newUser;
    }

    private void checkUserCreationValidity(CreateUserDto wantedUser) throws InvalidUserCreationException {

        if (StringUtils.isNullOrEmpty(wantedUser.getName()) || StringUtils.isNullOrEmpty(wantedUser.getUnHashedPassword())) {
            throw new InvalidUserCreationException("User request is invalid");
        }

        User oldUser = userRepository.findByName(wantedUser.getName());

        if (oldUser != null) {
            throw new InvalidUserCreationException("User with this name already exists");
        }
    }

    public void deleteUser(User user) {

        LOG.info("deleteUser user {}", user.getResourceId());

        userRepository.delete(user);
        driveFileSystemService.deleteUserFileSystem(user);
    }

    public User updateUser(User user, UpdateUserDto updateUserDto) {

        LOG.info("updateUser user {}", user.getResourceId());

        user.updateName(updateUserDto.getName());
        user.updateUnhashedPassword(updateUserDto.getPassword());

        return userRepository.save(user);
    }
}
