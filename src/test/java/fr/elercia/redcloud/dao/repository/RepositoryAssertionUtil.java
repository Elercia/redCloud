package fr.elercia.redcloud.dao.repository;

import fr.elercia.redcloud.dao.entity.UserBase;
import junit.framework.AssertionFailedError;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RepositoryAssertionUtil {

    public static void assertUserBaseEquals(UserBase one, UserBase two) {
        assertUserBaseEquals(one, two, null);
    }

    public static void assertUserBaseEquals(UserBase one, UserBase two, String message) {
        assertEquals(one.getId(), two.getId(), message);
        assertEquals(one.getCreationDate(), two.getCreationDate(), message);
        assertEquals(one.getName(), two.getName(), message);
        assertEquals(one.getPassword(), two.getPassword(), message);
        assertEquals(one.getResourceId(), two.getResourceId(), message);
        assertEquals(one.getUserType(), two.getUserType(), message);
    }
}
