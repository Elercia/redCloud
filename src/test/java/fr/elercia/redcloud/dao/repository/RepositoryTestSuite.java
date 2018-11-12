package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.repository.tests.DirectoryRepositoryTest;
import fr.elercia.redcloud.dao.repository.tests.FileRepositoryTest;
import fr.elercia.redcloud.dao.repository.tests.UserRepositoryTest;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({UserRepositoryTest.class, DirectoryRepositoryTest.class, FileRepositoryTest.class})
public class RepositoryTestSuite {
}