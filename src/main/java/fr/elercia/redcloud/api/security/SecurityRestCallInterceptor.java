package fr.elercia.redcloud.api.security;

import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.service.AuthenticationService;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedRestCall;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;

@Component
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
            setErrorResponseHeader(response);
            throw new UnauthorizedRestCall("Missing Auth token");
        }

        String accessToken = bearerToken.substring(SecurityConstants.TOKEN_TYPE.length() + 1);

        try {
            Token token = authenticationService.findByToken(accessToken);
        } catch (TokenNotFoundException e) {
            setErrorResponseHeader(response);
            throw new UnauthorizedRestCall("Unknown token");
        }

        return false;
    }

    private void setErrorResponseHeader(HttpServletResponse response) {
        response.setHeader(SecurityConstants.RESPONSE_HEADER_NAME, SecurityConstants.RESPONSE_HEADER_VALUE);
    }

    private boolean isAnnotationPresent(Class<? extends Annotation> annotation, Method method) {
        return method.getAnnotation(annotation) != null;
    }
}