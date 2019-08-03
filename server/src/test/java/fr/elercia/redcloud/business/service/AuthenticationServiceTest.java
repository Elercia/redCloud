package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.DynamicConfig;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.exceptions.InvalidTokenException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import fr.elercia.redcloud.utils.TokenUtils;
import fr.elercia.redcloud.utils.UserTestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.Date;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
//@Transactional(rollbackFor = Throwable.class)
class AuthenticationServiceTest {

    private static final String SECRET = "test";
    private static final String ISSUER = "test";
    private static final UUID USER_UID = UUID.fromString("7c115377-c819-4ee7-90e7-3712cf56e57e");

    @Mock
    private UserService userService;

    @Mock
    private DynamicConfigService dynamicConfigService;

    private AuthenticationService authenticationService;

    @BeforeEach
    void setUp() throws UserNotFoundException {
        AppUser mockUser = UserTestUtils.mockUser("name", UUID.randomUUID(), UserType.USER, new Date(), new ArrayList<>());
        Mockito.when(userService.findByResourceId(ArgumentMatchers.eq(USER_UID))).thenReturn(mockUser);

        Mockito.when(dynamicConfigService.getString(ArgumentMatchers.eq(DynamicConfig.DynamicConfigName.OAUTH_ISSUER))).thenReturn("test");
        Mockito.when(dynamicConfigService.getString(ArgumentMatchers.eq(DynamicConfig.DynamicConfigName.OAUTH_SECRET))).thenReturn("test");

        System.out.println(dynamicConfigService.getString(DynamicConfig.DynamicConfigName.OAUTH_SECRET));

        authenticationService = new AuthenticationService(userService, dynamicConfigService);
    }

    @Test
    void getConnectedUser_validUser_returnUser() throws InvalidTokenException {

        String token = TokenUtils.buildToken(ISSUER, SECRET, USER_UID.toString());
        assertNotNull(authenticationService.getUserConnected(token));
    }

    @Test
    void getConnectedUser_invalidUser_throwException() {

        assertThrows(InvalidTokenException.class, () -> {
            String token = TokenUtils.buildToken(ISSUER, SECRET, UUID.randomUUID().toString());
            authenticationService.getUserConnected(token);
        });
    }

    @Test
    void getConnectedUser_invaliTokenSecret_throwException() {

        assertThrows(InvalidTokenException.class, () -> {
            String token = TokenUtils.buildToken(ISSUER, "fnkfeknfkenf", USER_UID.toString());
            authenticationService.getUserConnected(token);
        });
    }

    @Test
    void getConnectedUser_invaliTokenIssuer_throwException() {

        assertThrows(InvalidTokenException.class, () -> {
            String token = TokenUtils.buildToken("knkwdnwkdnwkdnwd", SECRET, USER_UID.toString());
            authenticationService.getUserConnected(token);
        });
    }
}