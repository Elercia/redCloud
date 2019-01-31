package fr.elercia.redcloud.config;

import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.security.SecurityRestCallConnectionInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor securityRestCallInterceptor() {
        return new SecurityRestCallConnectionInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityRestCallInterceptor()).addPathPatterns(Route.BASE + "/**");
    }
} 