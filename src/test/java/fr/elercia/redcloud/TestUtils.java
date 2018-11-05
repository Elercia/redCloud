package fr.elercia.redcloud;

import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.entity.UserBase;

import java.util.Date;
import java.util.UUID;

public class TestUtils {

    public static UserBase createUserBase() {
        return new UserBase(0, "Testuser", new Date(), UUID.randomUUID(), "password", UserType.USER);
    }

    public static DirectoryBase createDirectoryBase(int userId) {
        return createDirectoryBase(null, userId);
    }

    public static DirectoryBase createDirectoryBase(Integer parentId, int userId) {
        return new DirectoryBase(0, "directory name", parentId, UUID.randomUUID(), new Date(), userId);
    }
}
