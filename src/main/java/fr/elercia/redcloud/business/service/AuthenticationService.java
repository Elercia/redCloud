package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.exceptions.InvalidLoginException;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private TokenStore tokenStore;
    private UserService userService;

    @Autowired
    public AuthenticationService(TokenStore tokenStore, UserService userService) {

        this.tokenStore = tokenStore;
        this.userService = userService;
    }

    public Token login(String username, String password) throws InvalidLoginException {

        User user;

        try {
            user = userService.findByName(username);
        } catch (Exception e) {
            throw new InvalidLoginException("No User found for this username", e);
        }

        if(!PasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new InvalidLoginException("Password don't matches");
        }

        return tokenStore.fabricToken(user);
    }

    public void logout(String token) throws TokenNotFoundException {

        tokenStore.revokeAccessToken(token);
    }

    public Token findByToken(String token) throws TokenNotFoundException {

        return tokenStore.retrieveFromAccessToken(token);
    }
}
