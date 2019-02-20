package fr.elercia.redcloud;

import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.business.service.PasswordEncoder;
import fr.elercia.redcloud.business.service.UserService;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.UUID;

@Disabled
@SpringBootTest
public class PopulateDB {

    @Autowired
    private UserService userService;

    @Test
    void test() throws InvalidUserCreationException {

        CreateUserDto createUserDto = new CreateUserDto("testUser1", "password");

        userService.createUser(createUserDto);
    }
}
