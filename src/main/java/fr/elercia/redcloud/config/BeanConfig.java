package fr.elercia.redcloud.config;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.business.entity.Mapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class BeanConfig {

    @Bean
    public Mapper getBusinessMapper() {
        return new Mapper();
    }

    @Bean
    public DtoMapper getDtoMapper() {
        return new DtoMapper();
    }
}
