package redcloud.business.service;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redcloud.api.dto.entity.SimpleUserDto;
import redcloud.business.entity.User;
import redcloud.exceptions.UserNotFoundException;

import java.util.List;
import java.util.UUID;

@Service
public class UserService {

//    private UserRepository userRepository;

    @Autowired
    public UserService() {

    }

    public User findByResourceId(UUID userId) throws UserNotFoundException {

//        User user = userRepository.findByResourceId(userId);

//        if(user == null) {
//            throw new UserNotFoundException();
//        }

        return null;
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
