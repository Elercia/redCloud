package fr.elercia.redcloud.business.entity;

import fr.elercia.redcloud.dao.entity.DirectoryBase;
import fr.elercia.redcloud.dao.entity.FileBase;
import fr.elercia.redcloud.dao.entity.UserBase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BusinessMapper {

    public static DirectoryBase mapToBase(SimpleDirectory directory, Integer parentId) {
        return new DirectoryBase(directory.getId(), directory.getName(), parentId, directory.getResourceId(), directory.getCreationDate(), directory.getUser().getId());
    }

    public static User mapToUser(UserBase userBase) {
        return mapToUser(userBase, null);
    }

    public static User mapToUser(UserBase userBase, Directory rootDirectory) {
        return new User(userBase.getId(), userBase.getName(), userBase.getResourceId(), userBase.getPassword(), userBase.getUserType(), userBase.getCreationDate(), rootDirectory);
    }

    public static Directory mapToDirectory(DirectoryBase directoryBase, User user) {
        return new Directory(directoryBase.getId(), directoryBase.getName(), directoryBase.getResourceId(), directoryBase.getCreationDate(), user, new ArrayList<>(), new ArrayList<>());
    }

    public static Directory mapToDirectory(DirectoryBase directoryBase, List<DirectoryBase> subDirectoryBases, List<FileBase> fileBases, User user) {
        Directory directory = new Directory(directoryBase.getId(), directoryBase.getName(), directoryBase.getResourceId(), directoryBase.getCreationDate(), user, mapSubDirectories(subDirectoryBases, user), null);
        directory.setFiles(mapFiles(fileBases, directory));
        return directory;
    }

    private static List<File> mapFiles(List<FileBase> fileBases, SimpleDirectory parentDirectory) {
        return fileBases.stream().map(f -> new File(f.getId(), f.getName(), f.getResourceId(), f.getCreationDate(), parentDirectory)).collect(Collectors.toList());
    }

    private static List<SimpleDirectory> mapSubDirectories(List<DirectoryBase> subDirectoryBases, User user) {
        return subDirectoryBases.stream().map(s -> new SimpleDirectory(s.getId(), s.getName(), s.getResourceId(), s.getCreationDate(), user)).collect(Collectors.toList());
    }
}
