package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class TokenStore {

    private PassiveExpiringMap<String, Token> accessTokensMap;
    private PassiveExpiringMap<String, Token> refreshTokensMap;

    @Autowired
    public TokenStore() {
        accessTokensMap = new PassiveExpiringMap<>(SecurityConstants.EXPIRATION_TIME);
        refreshTokensMap = new PassiveExpiringMap<>(SecurityConstants.EXPIRATION_TIME);
    }

    public Token fabricToken(User user) {

        String accessToken = generateString();
        String refreshToken = generateString();

        Token token = new Token(accessToken, SecurityConstants.TOKEN_TYPE, SecurityConstants.EXPIRATION_TIME, refreshToken, user);

        accessTokensMap.put(accessToken, token);
        refreshTokensMap.put(refreshToken, token);

        return token; // TODO manager old token from user when he re-log
    }

    public Token retrieveFromAccessToken(String accessToken) throws TokenNotFoundException {

        Token token = accessTokensMap.get(accessToken);

        if (token == null) {
            throw new TokenNotFoundException();
        }

        refreshTokensMap.get(token.getRefreshToken()); // just refresh

        return token;
    }

    public Token refreshToken(String refreshToken) throws TokenNotFoundException {

        Token token = refreshTokensMap.get(refreshToken);

        if (token == null) {
            throw new TokenNotFoundException("Invalid refresh token");
        }

        accessTokensMap.get(token.getAccessToken()); // just refresh
        token.setCreationDate(new Date());

        return token;
    }

    public void revokeAccessToken(String accessToken) throws TokenNotFoundException {

        Token token = accessTokensMap.get(accessToken);

        if (token == null) {
            throw new TokenNotFoundException("Invalid refresh token");
        }

        accessTokensMap.remove(accessToken);
        refreshTokensMap.remove(token.getRefreshToken());
    }

    private String generateString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
