package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.utils.TokenTestUtils;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.exceptions.InvalidLoginException;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional
class AuthenticationServiceTest {

    @Autowired
    private AuthenticationService authenticationService;

    /**
     * This test require a user
     * name : testUser1
     * password : password
     */
    @Test
    void token_lifeCycle() throws InvalidLoginException, TokenNotFoundException {

        Token token = authenticationService.login("testUser1", "password");

        assertNotNull(token);

        Token foundToken = authenticationService.findByToken(token.getAccessToken());

        TokenTestUtils.checkEquals(token, foundToken);

        authenticationService.logout(token.getAccessToken());

        try {
            authenticationService.findByToken(token.getAccessToken());
            fail("Found a revoked Token");
        } catch (TokenNotFoundException ignored) {

        }
    }

    @Test
    void login_wrongLogin_exceptionThrown() {

        assertThrows(InvalidLoginException.class, () -> {
            authenticationService.login("dededefejbrkbgrkjbrjkgb", "password");
        });
    }

    @Test
    void login_wrongPassword_exceptionThrown() {

        assertThrows(InvalidLoginException.class, () -> {
            authenticationService.login("testUser1", "jfejfbejfejf");
        });
    }

    @Test
    void login_loginNull_exceptionThrown() {

        assertThrows(InvalidLoginException.class, () -> {
            authenticationService.login(null, null);
        });
    }
}