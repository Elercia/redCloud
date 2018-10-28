package fr.elercia.redcloud.business.service.security;

import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.service.AuthenticationService;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedRestCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

public class SecurityRestCallInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthenticationService authenticationService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (isAnnotationPresent(PermitAll.class, handlerMethod.getMethod())) {
            return true;
        }

        String bearerToken = request.getHeader(SecurityConstants.REQUEST_HEADER_NAME);

        if (bearerToken == null) {
            throw new UnauthorizedRestCall("Missing Auth token");
        }

        String token = bearerToken.substring(SecurityConstants.TOKEN_PREFIX.length() + 1);

        try {
            User user = authenticationService.findByToken(token);
        } catch (TokenNotFoundException e) {
            throw new UnauthorizedRestCall("Unknown token");
        }

        return true;
    }

    private boolean isAnnotationPresent(Class<? extends Annotation> annotation, Method method) {
        return method.getAnnotation(annotation) != null;
    }
}