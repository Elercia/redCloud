package fr.elercia.redcloud.api.dto;

import fr.elercia.redcloud.api.dto.entity.MonitorIntegrityCheckResultDto;
import fr.elercia.redcloud.api.dto.entity.TokenDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.api.dto.entity.drive.FileDto;
import fr.elercia.redcloud.api.dto.entity.drive.FolderDto;
import fr.elercia.redcloud.api.dto.entity.drive.SimpleFolderDto;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckResult;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.business.entity.drive.DriveFile;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.config.SecurityConstants;

import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    private DtoMapper() {

    }

    public static UserDto entityToDto(User user) {
        return new UserDto(user.getName(), user.getResourceId(), user.getUserType(), user.getCreationDate(), entityToDto(user.getRootFolder()));
    }

    public static FolderDto entityToDto(DriveFolder driveFolder) {

        if(driveFolder == null)
            return null;

        return new FolderDto(driveFolder.getName(), driveFolder.getResourceId(), driveFolder.getCreationDate(), simpleFolderEntitiesToDto(driveFolder.getSubFolders()), fileEntitiesToDto(driveFolder.getDriveFiles()));
    }

    public static List<FileDto> fileEntitiesToDto(List<DriveFile> driveFiles) {
        return driveFiles.stream().map(DtoMapper::entityToDto).collect(Collectors.toList());
    }

    public static List<SimpleFolderDto> simpleFolderEntitiesToDto(List<DriveFolder> folders) {
        return folders.stream().map(d -> new SimpleFolderDto(d.getName(), d.getResourceId(), d.getCreationDate())).collect(Collectors.toList());
    }

    public static List<UserDto> entityToDto(List<User> user) {
        return user.stream().map(DtoMapper::entityToDto).collect(Collectors.toList());
    }

    public static TokenDto map(Token token) {
        return new TokenDto(token.getAccessToken(), SecurityConstants.TOKEN_TYPE, SecurityConstants.EXPIRATION_TIME, token.getRefreshToken());
    }

    public static FileDto entityToDto(DriveFile driveFile) {
        return new FileDto(driveFile.getFileName(), driveFile.getResourceId(), driveFile.getCreationDate(), driveFile.getSize());
    }

    public static MonitorIntegrityCheckResultDto entityToDto(MonitorIntegrityCheckResult checkSystemIntegrity) {
        return new MonitorIntegrityCheckResultDto(checkSystemIntegrity.getActionType());
    }
}