package redcloud.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import redcloud.api.interceptor.RequirePrivilegeInterceptor;
import redcloud.api.route.Route;

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
}