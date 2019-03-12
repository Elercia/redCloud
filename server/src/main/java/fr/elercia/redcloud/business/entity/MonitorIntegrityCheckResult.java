package fr.elercia.redcloud.business.entity;

import fr.elercia.redcloud.business.entity.drive.DriveFile;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class MonitorIntegrityCheckResult {

    private MonitoringIntegrityCheckType actionType;
    private List<File> unwantedFiles;
    private List<InternalErrorForFile> internalErrorInForFile;
    private List<File> unusedFolderOnFileSystem;
    private List<File> filesNotInDb;
    private List<DriveFile> filesNotOnFileSystem;

    public MonitorIntegrityCheckResult() {
        this.unwantedFiles = new ArrayList<>();
        this.internalErrorInForFile = new ArrayList<>();
        this.unusedFolderOnFileSystem = new ArrayList<>();
        this.filesNotInDb = new ArrayList<>();
        this.filesNotOnFileSystem = new ArrayList<>();
    }

    public MonitoringIntegrityCheckType getActionType() {
        return actionType;
    }

    public void addUnwantedFile(File file) {
        unwantedFiles.add(file);
    }

    public void addInternalError(File file, Throwable t) {

        InternalErrorForFile error = new InternalErrorForFile();
        error.file = file;
        error.error = t;

        this.internalErrorInForFile.add(error);
    }

    public void addUnusedFolder(File folder) {
        this.unusedFolderOnFileSystem.add(folder);
    }

    public void addFileNotInDb(File file) {
        this.filesNotInDb.add(file);
    }

    public void addFileNotOnFileSystem(DriveFile file) {
        this.filesNotOnFileSystem.add(file);
    }

    public List<File> getUnwantedFiles() {
        return unwantedFiles;
    }

    public List<InternalErrorForFile> getInternalErrorInForFile() {
        return internalErrorInForFile;
    }

    public List<File> getUnusedFolderOnFileSystem() {
        return unusedFolderOnFileSystem;
    }

    public List<File> getFilesNotInDb() {
        return filesNotInDb;
    }

    public List<DriveFile> getFilesNotOnFileSystem() {
        return filesNotOnFileSystem;
    }

    public class InternalErrorForFile {
        public File file;
        public Throwable error;
    }
}
