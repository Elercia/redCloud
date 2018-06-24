package fr.elercia.redcloud.config;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.business.entity.BusinessMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public BusinessMapper getBusinessMapper() {
        return new BusinessMapper();
    }

    @Bean
    public DtoMapper getDtoMapper() {
        return new DtoMapper();
    }
}
