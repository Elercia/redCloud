package fr.elercia.redcloud.dao.entity;

import fr.elercia.redcloud.business.entity.PrivilegeType;
import fr.elercia.redcloud.dao.generated.tables.records.DirectoryRecord;
import fr.elercia.redcloud.dao.generated.tables.records.UserPrivilegeRecord;
import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class BaseMapper {

    public static UserBase recordToBase(UserRecord userRecord, DirectoryRecord directoryRecord, UserPrivilegeRecord userPrivilegeRecord) {
        UserBase userBase = new UserBase(userRecord.getId(),
                userRecord.getName(),
                userRecord.getCreationDate(),
                UUID.fromString(userRecord.getResourceId()),
                userRecord.getPassword(),
                recordToBase(directoryRecord),
                recordToPrivilegeList(userPrivilegeRecord));

        return userBase;
    }

    public static DirectoryBase recordToBase(DirectoryRecord directoryRecord) {

        return new DirectoryBase(directoryRecord.getId(), directoryRecord.getName(), UUID.fromString(directoryRecord.getResourcesId()), directoryRecord.getCreationDate());
    }


    public static List<PrivilegeType> recordToPrivilegeList(UserPrivilegeRecord userPrivilegeRecord) {

        List<PrivilegeType> userPrivileges = new ArrayList<>();
        for (PrivilegeType privilegeType : PrivilegeType.values()) {

            byte b = userPrivilegeRecord.get(privilegeType.name(), Byte.class);
            if (b > 0)
                userPrivileges.add(privilegeType);
        }

        return userPrivileges;
    }
}
