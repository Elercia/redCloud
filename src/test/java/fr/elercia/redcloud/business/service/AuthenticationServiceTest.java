package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.TokenTestUtils;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.exceptions.InvalidLoginException;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.fail;

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
}