package fr.elercia.redcloud.business.service.monitoring;

import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckRequest;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckResult;
import fr.elercia.redcloud.business.entity.drive.DriveFile;
import fr.elercia.redcloud.business.service.drive.DriveFileService;
import fr.elercia.redcloud.business.service.drive.DriveFileSystemService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.File;
import java.util.*;

@Service
public class MonitoringService {

    private static final Logger LOG = LoggerFactory.getLogger(MonitoringService.class);

    private DriveFileService driveFileService;
    private DriveFileSystemService driveFileSystemService;

    @Autowired
    public MonitoringService(DriveFileService driveFileService, DriveFileSystemService driveFileSystemService) {

        this.driveFileService = driveFileService;
        this.driveFileSystemService = driveFileSystemService;
    }

    public MonitorIntegrityCheckResult checkSystemIntegrity(MonitorIntegrityCheckRequest monitorIntegrityCheckRequest) {

        List<DriveFile> databaseFiles = driveFileService.findAllFiles();
        List<File> systemFiles = driveFileSystemService.listUsersDirectories();

        return new MonitoringIntegrityCheckProcessor(databaseFiles, systemFiles).process();
    }
}
