package fr.elercia.redcloud.business.entity;

import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.entity.UserBase;

public class BusinessMapper {

    public static UserBase mapToBase(User user) {
        return null;
    }

    public static User mapToUser(UserBase userBase) {
        return new User(userBase.getId(), userBase.getName(), userBase.getResourceId(), userBase.getPassword(), userBase.getPrivileges(), userBase.getCreationDate(), mapToDirectory(userBase.getRootDirectoryBase()));

    }

    public static Directory mapToDirectory(DirectoryBase directoryBase) {
        return mapToDirectory(directoryBase, null);
    }

    public static Directory mapToDirectory(DirectoryBase directoryBase, DirectoryBase parentDirectoryBase) {
        Directory parentDirectory = null;

        if (parentDirectoryBase != null) {
            parentDirectory = mapToDirectory(parentDirectoryBase);
        }

        return new Directory(directoryBase.getId(), directoryBase.getName(), parentDirectory, directoryBase.getResourceId(), directoryBase.getCreationDate());
    }
}
