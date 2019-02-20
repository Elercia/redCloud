package fr.elercia.redcloud.api.security;

import fr.elercia.redcloud.config.SecurityConstants;

import javax.servlet.http.HttpServletRequest;

public class AuthorizationUtils {

    public static String getAccessToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(SecurityConstants.REQUEST_HEADER_NAME);

        if (bearerToken == null || !bearerToken.substring(0,SecurityConstants.TOKEN_TYPE.length()).equalsIgnoreCase(SecurityConstants.TOKEN_TYPE)) {
            return null;
        }

        return bearerToken.substring(SecurityConstants.TOKEN_TYPE.length() + 1);
    }
}
