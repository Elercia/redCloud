package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.DynamicConfig;
import fr.elercia.redcloud.business.entity.Environment;
import fr.elercia.redcloud.dao.repository.DynamicConfigRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class DynamicConfigService {

    private DynamicConfigRepository dynamicConfigRepository;

    @Autowired
    public DynamicConfigService(DynamicConfigRepository dynamicConfigRepository) {
        this.dynamicConfigRepository = dynamicConfigRepository;
    }

    public Environment getEnvironment() {

        DynamicConfig config = getConfig(DynamicConfig.DynamicConfigName.ENVIRONMENT);

        return Environment.valueOf(config.getValue());
    }

    <T extends Enum<T>> T getAsEnum(Class<T> clazz, DynamicConfig.DynamicConfigName name) {

        DynamicConfig config = getConfig(name);

        return T.valueOf(clazz, config.getValue());
    }

    public String getString(DynamicConfig.DynamicConfigName name) {

        DynamicConfig config = getConfig(name);

        return config.getValue();
    }

    private DynamicConfig getConfig(DynamicConfig.DynamicConfigName name) {

        DynamicConfig config = dynamicConfigRepository.findByName(name);

        if(config == null) {
            throw new IllegalStateException("Should never be here");
        }

        return config;
    }
}
