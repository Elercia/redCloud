package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.security.AuthorizationUtils;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.dao.repository.TokenRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;

public class AbstractController {

    @Autowired
    private HttpServletRequest request;

    @Autowired
    private TokenRepository tokenRepository;

    public HttpServletRequest getRequest() {
        return request;
    }

    public User getConnectedUser() {
        String accessToken = AuthorizationUtils.getAccessToken(request);

        Token token = tokenRepository.findByAccessToken(accessToken);

        if (token == null) {
            return null;
        }

        return token.getStoredUser();
    }

    protected String getAuthToken() {
        return request.getHeader(SecurityConstants.REQUEST_HEADER_NAME);
    }
}
