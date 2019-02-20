package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.Token;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TokenTestUtils {

    public static void checkEquals(Token expected, Token actual) {

        assertEquals(expected.getCreationDate(), actual.getCreationDate());
        assertEquals(expected.getAccessToken(), actual.getAccessToken());
        assertEquals(expected.getRefreshToken(), actual.getRefreshToken());
        assertEquals(expected.getExpiringDate(), actual.getExpiringDate());
        assertEquals(expected.getStoredUser(), actual.getStoredUser());
    }
}
