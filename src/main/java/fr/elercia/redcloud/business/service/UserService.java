package fr.elercia.redcloud.business.service;

import com.google.common.collect.Lists;
import fr.elercia.redcloud.api.dto.entity.SimpleUserDto;
import fr.elercia.redcloud.business.entity.BusinessMapper;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

        return BusinessMapper.mapToUser(userBase);
    }

    public User findByName(String name) throws UserNotFoundException {

        List<UserBase> userBases = userRepository.findByName(name);

        if (userBases == null) {
            throw new UserNotFoundException();
        }

        UserBase userBase = userBases.get(0);

        return BusinessMapper.mapToUser(userBase);
    }

    public List<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll())
                .stream()
                .map(BusinessMapper::mapToUser)
                .collect(Collectors.toList());
    }

    public User createUser(SimpleUserDto wantedUser) {
        throw new RuntimeException();
    }
}
