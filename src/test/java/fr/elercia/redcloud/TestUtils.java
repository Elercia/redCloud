package fr.elercia.redcloud;

import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.entity.FileBase;
import fr.elercia.redcloud.dao.entity.UserBase;

import java.util.Date;
import java.util.UUID;

public class TestUtils {

    public static UserBase createUserBase() {
        return new UserBase(0, generateName(), new Date(), UUID.randomUUID(), "password", UserType.USER);
    }

    public static DirectoryBase createDirectoryBase(int userId) {
        return createDirectoryBase(null, userId);
    }

    public static DirectoryBase createDirectoryBase(Integer parentId, int userId) {
        return new DirectoryBase(0, generateName(), parentId, UUID.randomUUID(), new Date(), userId);
    }

    public static FileBase createFileBase(int directoryId) {
        return new FileBase(0, generateName(), UUID.randomUUID(), new Date(), directoryId);
    }

    public static String generateName() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
