package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import org.apache.commons.collections4.map.PassiveExpiringMap;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class TokenStore {

    private Map<String, User> tokensMap;

    @Autowired
    public TokenStore() {
        tokensMap = new PassiveExpiringMap<>(SecurityConstants.EXPIRATION_TIME);
    }

    public User retrieveFromToken(String token) {

        User user = tokensMap.get(token);

        if(user == null) {
            throw new TokenNotFoundException();
        }

        return user;
    }

    public String newToken(User user) {

        String token = UUID.randomUUID().toString();

        tokensMap.put(token, user);

        return token;
    }

    public boolean revokeToken(String token) {
        return tokensMap.remove(token) != null;
    }
}
