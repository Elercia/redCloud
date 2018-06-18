package redcloud.business.services;

import com.google.common.collect.Lists;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import redcloud.business.entity.User;
import redcloud.dao.repositories.UserRepository;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class UserService {

    private UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {

        this.userRepository = userRepository;
    }

    public User findByName(String name) {
        return null;
    }

    public List<User> getAllUsers() {
        return Lists.newArrayList(userRepository.findAll());
    }
}
