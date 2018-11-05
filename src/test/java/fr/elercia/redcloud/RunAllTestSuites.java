package fr.elercia.redcloud;

import fr.elercia.redcloud.dao.repository.RepositoryTestSuite;
import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(Suite.class)
@SuiteClasses({RepositoryTestSuite.class})
public class RunAllTestSuites {
}