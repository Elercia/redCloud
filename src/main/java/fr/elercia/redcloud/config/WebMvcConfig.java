package fr.elercia.redcloud.config;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.interceptor.RequirePrivilegeInterceptor;
import fr.elercia.redcloud.api.route.Route;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    private RequirePrivilegeInterceptor requirePrivilegeInterceptor;

    @Autowired
    public WebMvcConfig(RequirePrivilegeInterceptor requirePrivilegeInterceptor) {
        this.requirePrivilegeInterceptor = requirePrivilegeInterceptor;
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(requirePrivilegeInterceptor).addPathPatterns(Route.BASE + "**");
    }

    @Bean
    public DtoMapper getDtoMapper() {
        return new DtoMapper();
    }
}