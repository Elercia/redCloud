package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Parameters;
import fr.elercia.redcloud.api.controllers.params.QueryParam;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.*;
import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.service.DriveFileService;
import fr.elercia.redcloud.business.service.DriveFolderService;
import fr.elercia.redcloud.business.service.SecurityUtils;
import fr.elercia.redcloud.exceptions.FileNameFormatException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import fr.elercia.redcloud.exceptions.FolderNotFoundException;
import fr.elercia.redcloud.exceptions.UnauthorizedFolderActionException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

@Api(value = "Operations about folders.")
@RestController
@RequestMapping("/")
public class FolderController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(FolderController.class);

    private DriveFolderService driveFolderService;
    private DriveFileService driveFileService;

    @Autowired
    public FolderController(DriveFolderService driveFolderService, DriveFileService driveFileService) {

        this.driveFolderService = driveFolderService;
        this.driveFileService = driveFileService;
    }

    @ApiOperation(value = "Create a directory")
    @PostMapping(Route.DIRECTORY)
    public ResponseEntity<FolderDto> addSubFolder(@PathVariable(QueryParam.DIRECTORY_ID) UUID parentFolderId, @RequestBody CreateFolderDto wantedFolder) throws FolderNotFoundException, UnauthorizedFolderActionException {

        LOG.info("create directory for parent {} ", parentFolderId);

        DriveFolder parentDir = driveFolderService.find(parentFolderId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), parentDir);

        FolderDto directory = DtoMapper.entityToDto(driveFolderService.createSubFolder(parentDir, wantedFolder));

        return new ResponseEntity<>(directory, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a directory")
    @GetMapping(value = Route.DIRECTORY)
    public FolderDto findFolder(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId) throws FolderNotFoundException {

        LOG.info("Find driveFolder id:{}", directoryId);

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        return DtoMapper.entityToDto(driveFolder);
    }

    @ApiOperation(value = "Delete a directory")
    @DeleteMapping(Route.DIRECTORY)
    public void deleteFolder(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId) throws FolderNotFoundException, UnauthorizedFolderActionException {

        LOG.info("delete driveFolder id:{}", directoryId);

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        driveFolderService.deleteFolder(driveFolder);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY)
    public void updateFolder(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestBody UpdateFolderDto updateFolderDto) throws FolderNotFoundException, UnauthorizedFolderActionException {

        LOG.info("update driveFolder id:{}", directoryId);

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        driveFolderService.update(driveFolder, updateFolderDto);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY_MOVE)
    public void moveFolder(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestBody MoveFolderDto moveFolderDto) throws FolderNotFoundException, UnauthorizedFolderActionException {

        LOG.info("move driveFolder from:{} - to:{}", directoryId, moveFolderDto.getMoveToFolderId());

        DriveFolder driveFolder = driveFolderService.find(directoryId);
        DriveFolder moveToDriveFolder = driveFolderService.find(moveFolderDto.getMoveToFolderId());

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);
        SecurityUtils.checkUserRightOn(getConnectedUser(), moveToDriveFolder);

        driveFolderService.move(driveFolder, moveToDriveFolder);
    }

    @PostMapping(Route.DIRECTORY_UPLOAD_FILE)
    public FileDto uploadFile(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestParam(Parameters.UPLOAD_FILE) MultipartFile file) throws FolderNotFoundException, FileNameFormatException, FileStorageException {

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        DriveFile storedDriveFile = driveFileService.storeFile(driveFolder, file);

        return DtoMapper.entityToDto(storedDriveFile);
    }
}
