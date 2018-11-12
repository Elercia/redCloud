package fr.elercia.redcloud.business.service;

import com.google.common.collect.Lists;
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

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class UserService {

    private UserRepository userRepository;
    private DirectoryService directoryService;

    @Autowired
    public UserService(UserRepository userRepository, DirectoryService directoryService) {
        this.userRepository = userRepository;
        this.directoryService = directoryService;
    }

    public User findByResourceId(UUID userId) throws UserNotFoundException {

        UserBase userBase = userRepository.findByResourceId(userId);

        if (userBase == null) {
            throw new UserNotFoundException();
        }

        return BusinessMapper.mapToUser(userBase, directoryService.findRootDirectory(userBase.getId()));
    }

    public User findByName(String name) throws UserNotFoundException {

        UserBase userBase = userRepository.findByName(name);

        if (userBase == null) {
            throw new UserNotFoundException();
        }

        return BusinessMapper.mapToUser(userBase, null);
    }

    public List<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll())
                .stream()
                .map(e -> BusinessMapper.mapToUser(e, directoryService.findRootDirectory(e.getId())))
                .collect(Collectors.toList());
    }

    public User createUser(CreateUserDto wantedUser) throws InvalidUserCreationException {

        UserBase userBase = userRepository.findByName(wantedUser.getName());

        if (userBase != null) {
            throw new InvalidUserCreationException("User with this name already exists");
        }

        String hashedPassword = PasswordEncoder.encode(wantedUser.getUnHashedPassword());

        UserBase newUserBase = new UserBase(wantedUser.getName(), new Date(), hashedPassword, UserType.USER);
        newUserBase = userRepository.add(newUserBase);

        User newUser = BusinessMapper.mapToUser(newUserBase, null);

        Directory directory = new Directory("root", null, newUser.getId());
        directoryService.create(directory);

        newUser.setRootDirectory(directory);

        return newUser;
    }

    public void deleteUser(User user) {

        userRepository.delete(user.getId());
    }
}
