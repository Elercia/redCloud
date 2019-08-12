package fr.elercia.redcloud.api.security;

import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.service.AuthenticationService;
import fr.elercia.redcloud.business.service.UserService;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.exceptions.InvalidTokenException;
import fr.elercia.redcloud.exceptions.UnauthorizedRestCall;
import fr.elercia.redcloud.exceptions.UserNotCreatedException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.lang.annotation.Annotation;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.UUID;

@Component
public class SecurityRestCallConnectionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private AuthenticationService authenticationService;
    @Autowired
    private UserService userService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if (!(handler instanceof HandlerMethod)) {
            return true;
        }

        HandlerMethod handlerMethod = (HandlerMethod) handler;

        if (isAnnotationPresent(PermitAll.class, handlerMethod.getMethod())) {
            return true;
        }

        String accessToken = AuthorizationUtils.getAccessToken(request);

        if (accessToken == null) {
            setErrorResponseHeader(response);
            throw new UnauthorizedRestCall("Missing Auth token");
        }

        try {

            UUID connectUserUid = authenticationService.getConnectedUserUid(accessToken);

            if (isAnnotationPresent(RequireUserType.class, handlerMethod.getMethod())) {
                AppUser connectedUser = userService.findByResourceId(connectUserUid);

                RequireUserType annotation = handlerMethod.getMethod().getAnnotation(RequireUserType.class);
                UserType[] requiredUserTypes = annotation.value();

                if (!Arrays.asList(requiredUserTypes).contains(connectedUser.getUserType())) {
                    setErrorResponseHeader(response);
                    throw new UnauthorizedRestCall("Unknown token");
                }
            }

        } catch (InvalidTokenException e) {
            setErrorResponseHeader(response);
            throw new UnauthorizedRestCall("Unknown token");
        } catch (UserNotFoundException e) {
            throw new UserNotCreatedException();
        }

        return true;
    }

    private void setErrorResponseHeader(HttpServletResponse response) {
        response.setHeader(SecurityConstants.RESPONSE_HEADER_NAME, SecurityConstants.RESPONSE_HEADER_VALUE);
    }

    private boolean isAnnotationPresent(Class<? extends Annotation> annotation, Method method) {
        return method.getAnnotation(annotation) != null;
    }
}