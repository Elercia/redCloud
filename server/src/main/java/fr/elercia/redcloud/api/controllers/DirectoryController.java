package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Parameters;
import fr.elercia.redcloud.api.controllers.params.QueryParam;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.*;
import fr.elercia.redcloud.business.entity.DriveFolder;
import fr.elercia.redcloud.business.entity.DriveFile;
import fr.elercia.redcloud.business.service.DriveFolderService;
import fr.elercia.redcloud.business.service.DriveFileService;
import fr.elercia.redcloud.business.service.SecurityUtils;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import fr.elercia.redcloud.exceptions.FileNameFormatException;
import fr.elercia.redcloud.exceptions.FileStorageException;
import fr.elercia.redcloud.exceptions.UnauthorizedDirectoryActionException;
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

@Api(value = "Operations about directories.")
@RestController
@RequestMapping("/")
public class DirectoryController extends AbstractController {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryController.class);

    private DriveFolderService driveFolderService;
    private DriveFileService driveFileService;

    @Autowired
    public DirectoryController(DriveFolderService driveFolderService, DriveFileService driveFileService) {

        this.driveFolderService = driveFolderService;
        this.driveFileService = driveFileService;
    }

    @ApiOperation(value = "Create a directory")
    @PostMapping(Route.DIRECTORY)
    public ResponseEntity<DirectoryDto> addSubDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID parentDirectoryId, @RequestBody CreateDirectoryDto wantedDirectory) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("create directory for parent {} ", parentDirectoryId);

        DriveFolder parentDir = driveFolderService.find(parentDirectoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), parentDir);

        DirectoryDto directory = DtoMapper.entityToDto(driveFolderService.createSubDirectory(parentDir, wantedDirectory));

        return new ResponseEntity<>(directory, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a directory")
    @GetMapping(value = Route.DIRECTORY)
    public DirectoryDto findDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId) throws DirectoryNotFoundException {

        LOG.info("Find driveFolder id:{}", directoryId);

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        return DtoMapper.entityToDto(driveFolder);
    }

    @ApiOperation(value = "Delete a directory")
    @DeleteMapping(Route.DIRECTORY)
    public void deleteDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("delete driveFolder id:{}", directoryId);

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        driveFolderService.deleteDirectory(driveFolder);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY)
    public void updateDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestBody UpdateDirectoryDto updateDirectoryDto) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("update driveFolder id:{}", directoryId);

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        driveFolderService.update(driveFolder, updateDirectoryDto);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY_MOVE)
    public void moveDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestBody MoveDirectoryDto moveDirectoryDto) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("move driveFolder from:{} - to:{}", directoryId, moveDirectoryDto.getMoveToDirectoryId());

        DriveFolder driveFolder = driveFolderService.find(directoryId);
        DriveFolder moveToDriveFolder = driveFolderService.find(moveDirectoryDto.getMoveToDirectoryId());

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);
        SecurityUtils.checkUserRightOn(getConnectedUser(), moveToDriveFolder);

        driveFolderService.move(driveFolder, moveToDriveFolder);
    }

    @PostMapping(Route.DIRECTORY_UPLOAD_FILE)
    public FileDto uploadFile(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestParam(Parameters.UPLOAD_FILE) MultipartFile file) throws DirectoryNotFoundException, FileNameFormatException, FileStorageException {

        DriveFolder driveFolder = driveFolderService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), driveFolder);

        DriveFile storedDriveFile = driveFileService.storeFile(driveFolder, file);

        return DtoMapper.entityToDto(storedDriveFile);
    }
}
