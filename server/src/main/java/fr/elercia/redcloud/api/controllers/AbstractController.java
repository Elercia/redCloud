package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.security.AuthorizationUtils;
import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.service.AuthenticationService;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.exceptions.InvalidTokenException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.UUID;

public class AbstractController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private AuthenticationService authenticationService;

    public HttpServletRequest getRequest() {
        return request;
    }

    public AppUser getConnectedUser() {
        String accessToken = AuthorizationUtils.getAccessToken(request);

        try {
            return authenticationService.getUserConnected(accessToken);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

        return null;
    }

    public UUID getConnectedUserUid() {

        String accessToken = AuthorizationUtils.getAccessToken(request);

        try {
            return authenticationService.getConnectedUserUid(accessToken);
        } catch (InvalidTokenException e) {
            e.printStackTrace();
        }

        return null;
    }

    protected String getAuthToken() {
        return request.getHeader(SecurityConstants.REQUEST_HEADER_NAME);
    }
}
