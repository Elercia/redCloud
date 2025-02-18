package fr.elercia.redcloud.api.dto;

import fr.elercia.redcloud.api.dto.entity.MonitorIntegrityCheckResultDto;
import fr.elercia.redcloud.api.dto.entity.TokenDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.api.dto.entity.drive.FileDto;
import fr.elercia.redcloud.api.dto.entity.drive.FolderDto;
import fr.elercia.redcloud.api.dto.entity.drive.SimpleFolderDto;
import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckResult;
import fr.elercia.redcloud.business.entity.drive.DriveFile;
import fr.elercia.redcloud.business.entity.drive.DriveFolder;

import java.io.File;
import java.util.List;
import java.util.stream.Collectors;

public class DtoMapper {

    private DtoMapper() {

    }

    public static UserDto entityToDto(AppUser user) {
        return new UserDto(user.getName(), user.getResourceId(), user.getUserType(), user.getCreationDate(), entityToDto(user.getRootFolder()));
    }

    public static FolderDto entityToDto(DriveFolder driveFolder) {

        if (driveFolder == null)
            return null;

        return new FolderDto(driveFolder.getName(), driveFolder.getResourceId(), driveFolder.getCreationDate(), simpleFolderEntitiesToDto(driveFolder.getSubFolders()), fileEntitiesToDto(driveFolder.getDriveFiles()));
    }

    public static List<FileDto> fileEntitiesToDto(List<DriveFile> driveFiles) {
        return driveFiles.stream().map(DtoMapper::entityToDto).collect(Collectors.toList());
    }

    public static List<SimpleFolderDto> simpleFolderEntitiesToDto(List<DriveFolder> folders) {
        return folders.stream().map(d -> new SimpleFolderDto(d.getName(), d.getResourceId(), d.getCreationDate())).collect(Collectors.toList());
    }

    public static List<UserDto> entityToDto(List<AppUser> user) {
        return user.stream().map(DtoMapper::entityToDto).collect(Collectors.toList());
    }

    public static TokenDto mapToToken(String token) {
        return new TokenDto(token);
    }

    public static FileDto entityToDto(DriveFile driveFile) {
        return new FileDto(driveFile.getFileName(), driveFile.getResourceId(), driveFile.getCreationDate(), driveFile.getSize());
    }

    public static MonitorIntegrityCheckResultDto entityToDto(MonitorIntegrityCheckResult checkSystemIntegrity) {
        return new MonitorIntegrityCheckResultDto(
                checkSystemIntegrity.getUnwantedFiles().stream().map(File::getPath).collect(Collectors.toList()),
                checkSystemIntegrity.getInternalErrorInForFile().stream().map(e -> e.file.getPath()).collect(Collectors.toList()),
                checkSystemIntegrity.getUnusedFolderOnFileSystem().stream().map(File::getPath).collect(Collectors.toList()),
                checkSystemIntegrity.getFilesNotInDb().stream().map(File::getPath).collect(Collectors.toList()),
                checkSystemIntegrity.getFilesNotOnFileSystem().stream().map(d -> d.getResourceId().toString()).collect(Collectors.toList()),
                checkSystemIntegrity.getActionType());
    }
}