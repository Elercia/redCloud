package fr.elercia.redcloud.business.service;

import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.transaction.TransactionConfiguration;
import org.springframework.transaction.annotation.Transactional;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
@TransactionConfiguration(defaultRollback = true)
public class ResourcesSecurityAcessTest {
}
