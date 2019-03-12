package fr.elercia.redcloud.api.dto.entity;

import fr.elercia.redcloud.business.entity.MonitoringIntegrityCheckType;

import java.util.List;

public class MonitorIntegrityCheckResultDto {

    private List<String> unwantedFilePaths;
    private List<String> internalErrorInForFilePaths;
    private List<String> unusedFolderOnFileSystem;
    private List<String> filesNotInDbPaths;
    private List<String> filesNotOnFileSystemIds;
    private MonitoringIntegrityCheckType actionType;

    public MonitorIntegrityCheckResultDto(List<String> unwantedFilePaths, List<String> internalErrorInForFilePaths, List<String> unusedFolderOnFileSystem, List<String> filesNotInDbPaths, List<String> filesNotOnFileSystemIds, MonitoringIntegrityCheckType actionType) {
        this.unwantedFilePaths = unwantedFilePaths;
        this.internalErrorInForFilePaths = internalErrorInForFilePaths;
        this.unusedFolderOnFileSystem = unusedFolderOnFileSystem;
        this.filesNotInDbPaths = filesNotInDbPaths;
        this.filesNotOnFileSystemIds = filesNotOnFileSystemIds;
        this.actionType = actionType;
    }

    public List<String> getUnwantedFilePaths() {
        return unwantedFilePaths;
    }

    public List<String> getInternalErrorInForFilePaths() {
        return internalErrorInForFilePaths;
    }

    public List<String> getUnusedFolderOnFileSystem() {
        return unusedFolderOnFileSystem;
    }

    public List<String> getFilesNotInDbPaths() {
        return filesNotInDbPaths;
    }

    public List<String> getFilesNotOnFileSystemIds() {
        return filesNotOnFileSystemIds;
    }

    public MonitoringIntegrityCheckType getActionType() {
        return actionType;
    }
}
