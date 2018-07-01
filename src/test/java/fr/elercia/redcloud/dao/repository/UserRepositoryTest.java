package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.TestUtils;
import fr.elercia.redcloud.config.SQLConfig;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.repository.impl.JOOQUserRepository;
import org.jooq.DSLContext;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jooq.JooqTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.PropertySource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SQLConfig.class, loader= AnnotationConfigContextLoader.class)
@TestPropertySource("classpath:application.properties")
@Transactional
public class UserRepositoryTest {

    @Autowired
    private DSLContext dslContext;

    private UserRepository userRepository;

    @Before
    public void setUp() {
        this.userRepository = new JOOQUserRepository(dslContext);
    }

    @Test
    public void create_update_find_delete() {

        // create user
        UserBase userBase = TestUtils.createUser();
        userBase = userRepository.add(userBase);

        // find user by name
        List<UserBase> foundsByName = userRepository.findByName(userBase.getName());

        assertEquals(1, foundsByName.size(), "Unexpected record found");
        RepositoryAssertionUtil.assertUserBaseEquals(userBase, foundsByName.get(0));

        // update user data

        // find it by resource id

        // delete user

        // find again
    }
}