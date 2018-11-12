package fr.elercia.redcloud.config;

import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.api.security.SecurityRestCallInterceptor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class SecurityConfig implements WebMvcConfigurer {

    @Bean
    public HandlerInterceptor securityRestCallInterceptor() {
        return new SecurityRestCallInterceptor();
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(securityRestCallInterceptor()).addPathPatterns(Route.BASE+"/**");
    }
} 