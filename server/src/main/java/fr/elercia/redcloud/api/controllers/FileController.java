package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.QueryParam;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.entity.MoveFileDto;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.service.DriveFolderService;
import fr.elercia.redcloud.business.service.DriveFileService;
import fr.elercia.redcloud.business.service.utils.DriveFileSystemUtils;
import fr.elercia.redcloud.business.service.SecurityUtils;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import fr.elercia.redcloud.exceptions.FileOperationException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(value = "Operations about files.")
@RestController
@RequestMapping("/")
public class FileController extends AbstractController{

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    private DriveFileService driveFileService;
    private DriveFolderService driveFolderService;

    @Autowired
    public FileController(DriveFileService driveFileService, DriveFolderService driveFolderService) {

        this.driveFileService = driveFileService;
        this.driveFolderService = driveFolderService;
    }

    @ApiOperation(value = "Download a file")
    @GetMapping(Route.FILE)
    public ResponseEntity<Resource> download(@RequestParam(QueryParam.FILE_ID) UUID fileId) throws FileNotFoundException {

        LOG.info("download driveFile id:{}", fileId);

        DriveFile driveFile = driveFileService.find(fileId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFile);

        // Load driveFile as Resource
        Resource resource = driveFileService.downloadFile(driveFile);

        return ResponseEntity.ok()
                .contentType(DriveFileSystemUtils.getMediaTypeFromExtension(driveFile.getFileName()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + driveFile.getFileName() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "Delete a file")
    @DeleteMapping(Route.FILE)
    public void deleteFile(@RequestParam(QueryParam.FILE_ID) UUID fileId) throws FileNotFoundException {

        DriveFile driveFile = driveFileService.find(fileId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFile);

        driveFileService.delete(driveFile);
    }

    @ApiOperation(value = "Move a file")
    @PutMapping(Route.FILE)
    public void moveFile(@RequestParam(QueryParam.FILE_ID) UUID fileId, MoveFileDto moveFileDto) throws FileNotFoundException, DirectoryNotFoundException, FileOperationException {

        DriveFile driveFile = driveFileService.find(fileId);
        DriveFolder driveFolder = driveFolderService.find(moveFileDto.getDirectoryId());

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFile);
        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        driveFileService.move(driveFile, driveFolder);
    }
}