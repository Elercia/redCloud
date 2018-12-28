package fr.elercia.redcloud.api.dto;

import fr.elercia.redcloud.api.dto.entity.*;
import fr.elercia.redcloud.business.entity.*;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    public static UserDto entityToDto(User user) {
        return new UserDto(user.getName(), user.getResourceId(), user.getUserType(), user.getCreationDate(), entityToDto(user.getRootDirectory()));
    }

    public static DirectoryDto entityToDto(Directory directory) {
        return new DirectoryDto(directory.getName(), directory.getResourceId(), directory.getCreationDate(), simpleDirectoryEntitiesToDto(directory.getSubFolders()), fileEntitiesToDto(directory.getFiles()));
    }

    public static List<FileDto> fileEntitiesToDto(List<File> files) {
        return files.stream().map(f -> new FileDto(f.getName(), f.getResourceId(), f.getCreationDate())).collect(Collectors.toList());
    }

    public static List<SimpleDirectoryDto> simpleDirectoryEntitiesToDto(List<Directory> directories) {
        return directories.stream().map(d -> new SimpleDirectoryDto(d.getName(), d.getResourceId(), d.getCreationDate())).collect(Collectors.toList());
    }

    public static List<UserDto> entityToDto(List<User> user) {
        return user.stream().map(DtoMapper::entityToDto).collect(Collectors.toList());
    }

    public static TokenDto map(Token token) {
        return new TokenDto(token.getAccessToken(), token.getTokenType(), token.getExpireIn(), token.getRefreshToken());
    }
}
