package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.Application;
import fr.elercia.redcloud.config.SaveConfig;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class UserServiceTest {

    @Autowired
    private UserService userService;

    @Test
    void findByResourceId() {
        System.out.println(SaveConfig.PATH_TO_FILES);
    }

    @Test
    void findByName() {
    }

    @Test
    void getAllUsers() {
    }

    @Test
    void createUser() {
    }

    @Test
    void deleteUser() {
    }

    @Test
    void updateUser() {
    }
}