package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.DynamicConfig;
import fr.elercia.redcloud.business.entity.Environment;
import fr.elercia.redcloud.dao.repository.DynamicConfigRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class DynamicConfigServiceTest {

    @Mock
    private DynamicConfigRepository dynamicConfigRepository;

    @Autowired
    @InjectMocks
    private DynamicConfigService dynamicConfigService;

    @BeforeEach
    public void setUp() throws Exception {
        // Initialize mocks created above
        MockitoAnnotations.initMocks(this);
    }

    @Test
    void getAsEnum_getRightValue() {

        DynamicConfig expectedConfig = Mockito.mock(DynamicConfig.class);
        Mockito.when(expectedConfig.getValue()).thenReturn(Environment.DEV.toString());

        Mockito.when(dynamicConfigRepository.findByName(DynamicConfig.DynamicConfigName.ENVIRONMENT)).thenReturn(expectedConfig);

        Environment actualEnv = dynamicConfigService.getAsEnum(Environment.class, DynamicConfig.DynamicConfigName.ENVIRONMENT);

        assertEquals(Environment.DEV, actualEnv);
    }

    @Test
    void getEnvironment_getRightValue() {

        DynamicConfig expectedConfig = Mockito.mock(DynamicConfig.class);
        Mockito.when(expectedConfig.getValue()).thenReturn(Environment.DEV.toString());

        Mockito.when(dynamicConfigRepository.findByName(DynamicConfig.DynamicConfigName.ENVIRONMENT)).thenReturn(expectedConfig);

        Environment actualEnv = dynamicConfigService.getEnvironment();

        assertEquals(Environment.DEV, actualEnv);
    }

    @Test
    void getString_getRightValue() {

        String path = "somePath";
        DynamicConfig expectedConfig = Mockito.mock(DynamicConfig.class);
        Mockito.when(expectedConfig.getValue()).thenReturn(path);

        Mockito.when(dynamicConfigRepository.findByName(DynamicConfig.DynamicConfigName.STORAGE_PATH)).thenReturn(expectedConfig);

        String actualPath = dynamicConfigService.getString(DynamicConfig.DynamicConfigName.STORAGE_PATH);

        assertEquals(path, actualPath);
    }
}
