package fr.elercia.redcloud.business.entity;

import fr.elercia.redcloud.dao.generated.tables.records.DirectoryRecord;
import fr.elercia.redcloud.dao.generated.tables.records.UserRecord;

import java.util.List;
import java.util.UUID;

public class Mapper {

    public UserRecord mapToBase(User user) {
        return null;
    }

    public User mapToUser(UserRecord userBase, DirectoryRecord rootDirectoryBase, List<PrivilegeType> privileges) {
        return new User(userBase.getId(), userBase.getName(), UUID.fromString(userBase.getResourceId()), userBase.getPassword(), privileges, userBase.getCreationDate(), mapToDirectory(rootDirectoryBase));
    }

    public Directory mapToDirectory(DirectoryRecord directoryBase) {
        return null;
    }
}
