package fr.elercia.redcloud.business.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class FileServiceTest {
 // TODO implements tests
}