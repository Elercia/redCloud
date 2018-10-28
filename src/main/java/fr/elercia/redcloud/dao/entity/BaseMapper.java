package fr.elercia.redcloud.dao.entity;

import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.dao.generated.tables.records.DirectoryRecord;
import fr.elercia.redcloud.dao.generated.tables.records.FileRecord;
import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;

import java.util.UUID;

public class BaseMapper {

    public static FileBase recordToBase(FileRecord fileRecord) {
        return null;
    }

    public static UserBase recordToBase(UserRecord userRecord) {
        UserBase userBase = new UserBase(userRecord.getId(),
                userRecord.getName(),
                userRecord.getCreationDate(),
                UUID.fromString(userRecord.getResourceId()),
                userRecord.getHashedpassword(),
                UserType.valueOf(userRecord.getUserType()));

        return userBase;
    }

    public static DirectoryBase recordToBase(DirectoryRecord directoryRecord) {

        return new DirectoryBase(
                directoryRecord.getId(),
                directoryRecord.getName(),
                directoryRecord.getParentId(),
                UUID.fromString(directoryRecord.getResourceId()),
                directoryRecord.getCreationDate());
    }
}
