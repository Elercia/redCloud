package fr.elercia.redcloud.dao.repository.tests;

import fr.elercia.redcloud.TestUtils;
import fr.elercia.redcloud.config.SQLConfig;
import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.entity.UserBase;
import fr.elercia.redcloud.dao.repository.DirectoryRepository;
import fr.elercia.redcloud.dao.repository.RepositoryAssertionUtil;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.dao.repository.impl.JOOQDirectoryRepository;
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

import static org.junit.jupiter.api.Assertions.assertNull;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = SQLConfig.class, loader = AnnotationConfigContextLoader.class)
@TestPropertySource("/application.properties")
@Transactional
public class DirectoryRepositoryTest {

    @Autowired
    private DSLContext dslContext;

    private DirectoryRepository directoryRepository;
    private UserRepository userRepository;

    @Before
    public void setUp() {
        this.directoryRepository = new JOOQDirectoryRepository(dslContext);
        this.userRepository = new JOOQUserRepository(dslContext);
    }

    @Test
    public void create_update_find_delete() {

        // create directory
        UserBase userBase = TestUtils.createUserBase();
        userBase = userRepository.add(userBase);

        DirectoryBase directoryBase = TestUtils.createDirectoryBase(userBase.getId());
        directoryBase = directoryRepository.add(directoryBase);

        // update directory
        String newName = "test2";
        directoryBase.setName(newName);

        directoryRepository.update(directoryBase);

        DirectoryBase newDir = directoryRepository.findByResourceId(directoryBase.getResourceId());

        RepositoryAssertionUtil.assertDirectoryBaseEquals(directoryBase, newDir, "Directory not updated");

        // delete directory
        directoryRepository.delete(directoryBase.getId());

        newDir = directoryRepository.findByResourceId(directoryBase.getResourceId());
        assertNull(newDir);
    }
}