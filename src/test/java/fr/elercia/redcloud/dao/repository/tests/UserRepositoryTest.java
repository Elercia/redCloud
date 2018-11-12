package fr.elercia.redcloud.dao.repository.tests;

import fr.elercia.redcloud.TestUtils;
import fr.elercia.redcloud.config.SQLConfig;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.repository.RepositoryAssertionUtil;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.dao.repository.impl.JOOQUserRepository;
import org.jooq.DSLContext;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.context.support.AnnotationConfigContextLoader;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.Assert.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SQLConfig.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource("/application.properties")
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
        UserBase userBase = TestUtils.createUserBase();
        userBase = userRepository.add(userBase);

        // find user by name
        UserBase foundsByName = userRepository.findByName(userBase.getName());

        RepositoryAssertionUtil.assertUserBaseEquals(userBase, foundsByName, "User not equals");

        // update user data

        String newName = "OtherName";
        userBase.setName(newName);
        userRepository.update(userBase);

        foundsByName = userRepository.findByName(newName);

        RepositoryAssertionUtil.assertUserBaseEquals(userBase, foundsByName, "User not equals");

        // find it by resource id
        UserBase foundByResourceId = userRepository.findByResourceId(userBase.getResourceId());
        RepositoryAssertionUtil.assertUserBaseEquals(userBase, foundByResourceId, "User not equals");

        // delete user
        userRepository.delete(userBase.getId());
        UserBase postDeleteUser = userRepository.findByName(userBase.getName());

        assertNull("User not deleted", postDeleteUser);
    }
}