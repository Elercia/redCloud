package fr.elercia.redcloud.utils;

import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.UserType;
import org.mockito.Mockito;

import javax.persistence.Column;
import javax.persistence.OneToOne;
import java.util.Date;
import java.util.List;
import java.util.UUID;

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
