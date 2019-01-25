package fr.elercia.redcloud.business.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
@TransactionConfiguration(defaultRollback = true)
class FileServiceTest {

}