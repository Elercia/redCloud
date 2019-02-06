package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Parameters;
import fr.elercia.redcloud.api.controllers.params.QueryParam;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.*;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.service.DirectoryService;
import fr.elercia.redcloud.business.service.FileService;
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

    private DirectoryService directoryService;
    private FileService fileService;

    @Autowired
    public DirectoryController(DirectoryService directoryService, FileService fileService) {

        this.directoryService = directoryService;
        this.fileService = fileService;
    }

    @ApiOperation(value = "Create a directory")
    @PostMapping(Route.DIRECTORY)
    public ResponseEntity<DirectoryDto> addSubDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID parentDirectoryId, @RequestBody CreateDirectoryDto wantedDirectory) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("create directory for parent {} ", parentDirectoryId);

        Directory parentDir = directoryService.find(parentDirectoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), parentDir);

        DirectoryDto directory = DtoMapper.entityToDto(directoryService.createSubDirectory(parentDir, wantedDirectory));

        return new ResponseEntity<>(directory, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a directory")
    @GetMapping(value = Route.DIRECTORY)
    public DirectoryDto findDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId) throws DirectoryNotFoundException {

        LOG.info("Find directory id:{}", directoryId);

        Directory directory = directoryService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), directory);

        return DtoMapper.entityToDto(directory);
    }

    @ApiOperation(value = "Delete a directory")
    @DeleteMapping(Route.DIRECTORY)
    public void deleteDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("delete directory id:{}", directoryId);

        Directory directory = directoryService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), directory);

        directoryService.deleteDirectory(directory);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY)
    public void updateDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestBody UpdateDirectoryDto updateDirectoryDto) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("update directory id:{}", directoryId);

        Directory directory = directoryService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), directory);

        directoryService.update(directory, updateDirectoryDto);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY_MOVE)
    public void moveDirectory(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestBody MoveDirectoryDto moveDirectoryDto) throws DirectoryNotFoundException, UnauthorizedDirectoryActionException {

        LOG.info("move directory from:{} - to:{}", directoryId, moveDirectoryDto.getMoveToDirectoryId());

        Directory directory = directoryService.find(directoryId);
        Directory moveToDirectory = directoryService.find(moveDirectoryDto.getMoveToDirectoryId());

        SecurityUtils.checkUserRightOn(getConnectedUser(), directory);
        SecurityUtils.checkUserRightOn(getConnectedUser(), moveToDirectory);

        directoryService.move(directory, moveToDirectory);
    }

    @PostMapping(Route.DIRECTORY_UPLOAD_FILE)
    public FileDto uploadFile(@PathVariable(QueryParam.DIRECTORY_ID) UUID directoryId, @RequestParam(Parameters.UPLOAD_FILE) MultipartFile file) throws DirectoryNotFoundException, FileNameFormatException, FileStorageException {

        Directory directory = directoryService.find(directoryId);

        SecurityUtils.checkUserRightOn(getConnectedUser(), directory);

        File storedFile = fileService.storeFile(directory, file);

        return DtoMapper.entityToDto(storedFile);
    }
}
