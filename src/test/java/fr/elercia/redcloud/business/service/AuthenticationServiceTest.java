package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.repository.UserRepository;
import fr.elercia.redcloud.exceptions.InvalidLoginException;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import fr.elercia.redcloud.utils.TokenTestUtils;
import fr.elercia.redcloud.utils.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@Transactional(rollbackFor = Throwable.class)
class AuthenticationServiceTest {

    @Autowired
    @InjectMocks
    private AuthenticationService authenticationService;

    @Mock
    private UserRepository userRepository;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.initMocks(this);
        User mockUser = UserTestUtils.mockUser("name", UUID.randomUUID(), PasswordEncoder.encode("password"), UserType.USER, new Date(), new ArrayList<>());
        Mockito.when(userRepository.findByName(ArgumentMatchers.eq("testUser1"))).thenReturn(mockUser);
    }

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