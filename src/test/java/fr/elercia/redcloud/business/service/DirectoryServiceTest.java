package fr.elercia.redcloud.business.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class DirectoryServiceTest {

    @Autowired
    private DirectoryService directoryService;

}