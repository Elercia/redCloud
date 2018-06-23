package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.SimpleUserDto;
import fr.elercia.redcloud.business.entity.Mapper;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

    private UserRepository userRepository;
    private Mapper mapper;

    @Autowired
    public UserService(UserRepository userRepository, Mapper mapper) {
        this.userRepository = userRepository;
        this.mapper = mapper;
    }

    public User findByResourceId(UUID userId) throws UserNotFoundException {

        UserRecord userBase = userRepository.findByResourceId(userId);

        if (userBase == null) {
            throw new UserNotFoundException();
        }

        return mapper.mapToUser(userBase, null, null);
    }

    public User findByName(String name) throws UserNotFoundException {

//        List<User> users = userRepository.findByName(name);
//
//        if (users == null) {
//            throw new UserNotFoundException();
//        }
//
//        return users.get(0);
        return null;
    }

    public List<User> getAllUsers() {
//        return Lists.newArrayList(userRepository.findAll());
        return null;
    }

    public User createUser(SimpleUserDto wantedUser) {
        return null;
    }
}
