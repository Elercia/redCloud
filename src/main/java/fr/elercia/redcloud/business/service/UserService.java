package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.api.dto.entity.UpdateUserDto;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.apache.commons.collections4.IteratorUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private DirectoryService directoryService;
    private FileSystemService fileSystemService;

    @Autowired
    public UserService(UserRepository userRepository, DirectoryService directoryService, FileSystemService fileSystemService) {
        this.userRepository = userRepository;
        this.directoryService = directoryService;
        this.fileSystemService = fileSystemService;
    }

    public User findByResourceId(UUID userResourceId) throws UserNotFoundException {

        User user = userRepository.findByResourceId(userResourceId);

        if (user == null) {
            throw new UserNotFoundException("User not found with this resource Id");
        }

        return user;
    }

    public User findByName(String name) throws UserNotFoundException {

        User user = userRepository.findByName(name);

        if (user == null) {
            throw new UserNotFoundException();
        }

        return user;
    }

    public List<User> getAllUsers() {

        List<User> users = IteratorUtils.toList(userRepository.findAll().iterator());

        return users;
    }

    public User createUser(CreateUserDto wantedUser) throws InvalidUserCreationException {

        User oldUser = userRepository.findByName(wantedUser.getName());

        if (oldUser != null) {
            throw new InvalidUserCreationException("User with this name already exists");
        }

        String hashedPassword = PasswordEncoder.encode(wantedUser.getUnHashedPassword());

        User newUser = new User(wantedUser.getName(), hashedPassword, UserType.USER);

        userRepository.save(newUser);
        directoryService.createRootDirectory(newUser);
        fileSystemService.createUserFileSystemSpace(newUser);

        return newUser;
    }

    public void deleteUser(User user) {

        userRepository.delete(user);
        fileSystemService.deleteUserFileSystem(user);
    }

    public User updateUser(User user, UpdateUserDto updateUserDto) {

        user.updateName(updateUserDto.getName());
        user.updateUnhashedPassword(updateUserDto.getPassword());

        return userRepository.save(user);
    }
}
