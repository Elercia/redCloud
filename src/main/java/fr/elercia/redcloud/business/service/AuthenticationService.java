package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.config.SecurityConstants;
import fr.elercia.redcloud.dao.repository.TokenRepository;
import fr.elercia.redcloud.exceptions.InvalidLoginException;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.UUID;

@Service
public class AuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private TokenRepository tokenRepository;
    private UserService userService;

    @Autowired
    public AuthenticationService(TokenRepository tokenRepository, UserService userService) {

        this.tokenRepository = tokenRepository;
        this.userService = userService;
    }

    public Token login(String username, String password) throws InvalidLoginException {

        User user;

        try {
            user = userService.findByName(username);

            LOG.info("Login user : {}", user.getResourceId());

        } catch (Exception e) {
            throw new InvalidLoginException("No User found for this username", e);
        }

        if (!PasswordEncoder.matches(password, user.getHashedPassword())) {
            throw new InvalidLoginException("Password don't matches");
        }

        return createToken(user);
    }

    public void logout(String token) {

        LOG.info("Logout token : {}", token);

        tokenRepository.deleteByAccessToken(token);
    }

    public Token findByToken(String tokenString) throws TokenNotFoundException {

        Token token = tokenRepository.findByAccessToken(tokenString);

        if (token == null) {
            throw new TokenNotFoundException();
        }

        LOG.info("FindByToken user : {}", token.getStoredUser().getResourceId());

        if (!isValidToken(token)) {

            LOG.info("Invalid token user : {}", token.getStoredUser().getResourceId());

            tokenRepository.delete(token);
            throw new TokenNotFoundException();
        }

        refreshToken(token);

        return token;
    }

    private void refreshToken(Token token) {

        LOG.debug("Refresh token user : {}", token.getStoredUser().getResourceId());

        token.setExpiringDate(new Date(new Date().getTime() + SecurityConstants.EXPIRATION_TIME));
        tokenRepository.save(token);
    }

    private boolean isValidToken(Token token) {
        return !token.getExpiringDate().before(new Date());
    }

    private Token createToken(User user) {
        Token token = new Token(createTokenString(), createTokenString(), user);

        LOG.info("Create token user : {}", token.getStoredUser().getResourceId());

        tokenRepository.save(token);

        return token;
    }

    private String createTokenString() {
        return UUID.randomUUID().toString().replace("-", "");
    }
}
