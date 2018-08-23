package fr.elercia.redcloud;

import com.google.common.collect.Lists;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.entity.UserBase;

import java.util.Date;
import java.util.UUID;

public class TestUtils {

    public static UserBase createUser() {
        return new UserBase(0, "Testuser", new Date(), UUID.randomUUID(), "password", UserType.USER);
    }

}
