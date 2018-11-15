package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.business.entity.BusinessMapper;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private DirectoryService directoryService;

    @Autowired
    public UserService(UserRepository userRepository, DirectoryService directoryService) {
        this.userRepository = userRepository;
        this.directoryService = directoryService;
    }

    public User findByResourceId(UUID userResourceId) throws UserNotFoundException {

        UserBase userBase = userRepository.findByResourceId(userResourceId);

        if (userBase == null) {
            throw new UserNotFoundException("User not found with this resource Id");
        }

        User user = BusinessMapper.mapToUser(userBase);
        Directory directory = directoryService.findRootDirectory(user);
        user.setRootDirectory(directory);

        return user;
    }

    public User findByName(String name) throws UserNotFoundException {

        UserBase userBase = userRepository.findByName(name);

        if (userBase == null) {
            throw new UserNotFoundException();
        }

        User user = BusinessMapper.mapToUser(userBase);
        user.setRootDirectory(directoryService.findRootDirectory(user));

        return user;
    }

    public List<User> getAllUsers() {

        List<User> users = new ArrayList<>();
        List<UserBase> bases = userRepository.findAll();

        for (UserBase base : bases) {
            User user = BusinessMapper.mapToUser(base);
            user.setRootDirectory(directoryService.findRootDirectory(user));
            users.add(user);
        }

        return users;
    }

    public User createUser(CreateUserDto wantedUser) throws InvalidUserCreationException {

        UserBase userBase = userRepository.findByName(wantedUser.getName());

        if (userBase != null) {
            throw new InvalidUserCreationException("User with this name already exists");
        }

        String hashedPassword = PasswordEncoder.encode(wantedUser.getUnHashedPassword());

        UserBase newUserBase = new UserBase(wantedUser.getName(), new Date(), hashedPassword, UserType.USER);
        newUserBase = userRepository.add(newUserBase);

        User newUser = BusinessMapper.mapToUser(newUserBase);

        Directory directory = directoryService.createRootDirectory(newUser);
        newUser.setRootDirectory(directory);

        return newUser;
    }

    public void deleteUser(User user) {

        userRepository.delete(user.getId());
    }
}
