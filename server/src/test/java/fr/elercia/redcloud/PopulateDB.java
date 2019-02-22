package fr.elercia.redcloud;

import fr.elercia.redcloud.api.dto.entity.CreateUserDto;
import fr.elercia.redcloud.business.entity.DynamicConfig;
import fr.elercia.redcloud.business.entity.Environment;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.service.UserService;
import fr.elercia.redcloud.dao.repository.DynamicConfigRepository;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidUserCreationException;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Arrays;

@Disabled
@SpringBootTest
public class PopulateDB {

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    DynamicConfigRepository dynamicConfigRepository;

    @Test
    void test() throws InvalidUserCreationException {

        DynamicConfig pathConfig = new DynamicConfig(DynamicConfig.DynamicConfigName.STORAGE_PATH, "C:/Users/rabie/Documents/redCloud/server/content");
        DynamicConfig envConfig = new DynamicConfig(DynamicConfig.DynamicConfigName.ENVIRONMENT, Environment.DEV.toString());
        dynamicConfigRepository.saveAll(Arrays.asList(pathConfig, envConfig));

        CreateUserDto createUserDto = new CreateUserDto("testUser1", "password");
        userService.createUser(createUserDto);

        CreateUserDto createAdminUserDto = new CreateUserDto("admin", "password");
        User adminUser = userService.createUser(createAdminUserDto);

        adminUser.setUserType(UserType.ADMIN);
        userRepository.save(adminUser);
    }
}
