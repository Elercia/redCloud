package fr.elercia.redcloud.business.service;

import com.google.common.collect.Lists;
import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.api.dto.entity.SimpleUserDto;
import fr.elercia.redcloud.business.entity.BusinessMapper;
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

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public User findByResourceId(UUID userId) throws UserNotFoundException {

        UserBase userBase = userRepository.findByResourceId(userId);

        if (userBase == null) {
            throw new UserNotFoundException();
        }

        return BusinessMapper.mapToUser(userBase, null);
    }

    public List<User> findByName(String name) throws UserNotFoundException {

        List<UserBase> userBases = userRepository.findByName(name);

        if (userBases == null || userBases.isEmpty()) {
            throw new UserNotFoundException();
        }

        return userBases.stream().map(ub -> BusinessMapper.mapToUser(ub, null)).collect(Collectors.toList());
    }

    public List<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll())
                .stream()
                .map(e -> BusinessMapper.mapToUser(e, null))
                .collect(Collectors.toList());
    }

    public User createUser(CreateUserDto wantedUser) throws InvalidUserCreationException {

        List<UserBase> userBases = userRepository.findByName(wantedUser.getName());

        if (!userBases.isEmpty()) {
            throw new InvalidUserCreationException("User with this name already exists");
        }

        String hashedPassword = HashPasswordUtils.hashString(wantedUser.getUnHashedPassword());

        UserBase newUser = new UserBase(wantedUser.getName(), new Date(), hashedPassword, UserType.USER);

        newUser = userRepository.add(newUser);

        return BusinessMapper.mapToUser(newUser, null);
    }
}
