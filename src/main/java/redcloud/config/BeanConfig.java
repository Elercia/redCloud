package redcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import redcloud.api.dto.DtoMapper;

@Configuration
public class BeanConfig {

    @Bean
    public DtoMapper getDtoMapper() {
        return new DtoMapper();
    }
}
