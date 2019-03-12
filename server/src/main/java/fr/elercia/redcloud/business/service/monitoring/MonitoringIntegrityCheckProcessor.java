package fr.elercia.redcloud.business.service.monitoring;

import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckResult;
import fr.elercia.redcloud.business.entity.drive.DriveFile;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.*;
import java.util.stream.Collectors;

public class MonitoringIntegrityCheckProcessor {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringIntegrityCheckProcessor.class);

    private Map<UUID, List<DriveFile>> userFilesMap;
    private List<File> systemFiles;
    private MonitorIntegrityCheckResult result;

    public MonitoringIntegrityCheckProcessor(List<DriveFile> databaseFiles, List<File> systemFiles) {
        this.systemFiles = systemFiles;
        this.userFilesMap = getUserFilesMap(databaseFiles);
        this.result = new MonitorIntegrityCheckResult();
    }

    public MonitorIntegrityCheckResult process() {

        LOG.info("Begin check of drive files");
        long start = System.currentTimeMillis();

        checkUserFolders();

        LOG.info("End check of drive files (elapsed time : {})", System.currentTimeMillis() - start);

        return result;
    }

    private void checkUserFolders() {

        for (File userFolder : systemFiles) {
            try {
                if (!userFolder.isDirectory()) {
                    // The directory is not a drive directory
                    result.addUnwantedFile(userFolder);
                    continue;
                }

                checkUserFiles(userFolder, userFolder.getName());
            } catch (Throwable t) {
                LOG.warn("Catch error while processing user folder {}. Error {}", userFolder.getName(), t);
                result.addInternalError(userFolder, t);
            }
        }
    }

    private void checkUserFiles(File userFolder, String userIdStr) {

        List<DriveFile> driveFiles = userFilesMap.get(UUID.fromString(userIdStr));
        List<File> files = Arrays.asList(userFolder.listFiles());

        if (driveFiles == null) {
            // The user could not be in the database anymore or something like that
            result.addUnusedFolder(userFolder);
            return;
        }

        Map<UUID, DriveFile> driveFileMap = driveFiles.stream().collect(Collectors.toMap(DriveFile::getResourceId, x -> x));

        // find the missing files and the extra files
        for (File file : files) {

            UUID fileId = UUID.fromString(file.getName());
            DriveFile correspondingDriveFile = driveFileMap.get(fileId);

            if (correspondingDriveFile == null) {
                // The file is the file system but not in db
                result.addFileNotInDb(file);
                continue;
            }

            // we remove the file from the list because it can't appear a second time
            driveFileMap.remove(fileId);
        }

        // driveFileMap now contains all the file that are in the db but not on the filesystem
        for (DriveFile file : driveFileMap.values()) {
            result.addFileNotOnFileSystem(file);
        }
    }

    private Map<UUID, List<DriveFile>> getUserFilesMap(List<DriveFile> databaseFiles) {

        Map<UUID, List<DriveFile>> map = new HashMap<>();

        for (DriveFile file : databaseFiles) {

            UUID userId = file.getParent().getUser().getResourceId();
            List<DriveFile> userDriveFiles = map.get(userId);

            if (userDriveFiles == null) {
                userDriveFiles = new ArrayList<>();
            }

            userDriveFiles.add(file);

            map.put(userId, userDriveFiles);
        }

        return map;
    }
}
