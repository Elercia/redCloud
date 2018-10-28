package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.exceptions.WrongLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService {

    private TokenStore tokenStore;
    private UserService userService;
    private PasswordEncoderImpl passwordEncoder;

    @Autowired
    public AuthenticationService(TokenStore tokenStore, UserService userService, PasswordEncoderImpl passwordEncoder) {

        this.tokenStore = tokenStore;
        this.userService = userService;
        this.passwordEncoder = passwordEncoder;
    }

    public String login(String username, String password) throws WrongLoginException {

        User user;
        try {
            user = userService.findByName(username);
        } catch (Exception e) {
            throw new WrongLoginException("No User found for this username", e);
        }

        if(!passwordEncoder.matches(password, user.getHashedPassword())) {
            throw new WrongLoginException("Password don't matches");
        }

        return tokenStore.newToken(user);
    }

    public boolean logout(String token) {

        return tokenStore.revokeToken(token);
    }

    public User findByToken(String token) {

        return tokenStore.retrieveFromToken(token);
    }
}
