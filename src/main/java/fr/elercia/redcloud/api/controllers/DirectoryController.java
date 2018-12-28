package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.DirectoryDto;
import fr.elercia.redcloud.api.dto.entity.MoveDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UpdateDirectoryDto;
import fr.elercia.redcloud.api.route.QueryParam;
import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.service.DirectoryService;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(value = "Operations about directories.", description = "Manage directories and get info about them.")
@RestController
@RequestMapping("/")
public class DirectoryController {

    private static final Logger LOG = LoggerFactory.getLogger(DirectoryController.class);

    private DirectoryService directoryService;

    @Autowired
    public DirectoryController(DirectoryService directoryService) {

        this.directoryService = directoryService;
    }

    @ApiOperation(value = "Create a directory")
    @PostMapping(Route.DIRECTORY)
    public ResponseEntity<DirectoryDto> addSubDirectory(@RequestParam(QueryParam.DIRECTORY_ID) UUID parentDirectoryId, @RequestBody CreateDirectoryDto wantedDirectory) throws DirectoryNotFoundException {

        LOG.info("create directory for parent {} ", parentDirectoryId);

        Directory parentDir = directoryService.find(parentDirectoryId);

        DirectoryDto directory = DtoMapper.entityToDto(directoryService.createSubDirectory(parentDir, wantedDirectory));

        return new ResponseEntity<>(directory, HttpStatus.CREATED);
    }

    @ApiOperation(value = "Get a directory")
    @GetMapping(Route.DIRECTORY)
    public DirectoryDto findDirectory(@RequestParam(QueryParam.DIRECTORY_ID) UUID directoryId) throws DirectoryNotFoundException {

        LOG.info("Find directory id:{}", directoryId);

        return DtoMapper.entityToDto(directoryService.find(directoryId));
    }

    @ApiOperation(value = "Delete a directory")
    @DeleteMapping(Route.DIRECTORY)
    public void deleteDirectory(@RequestParam(QueryParam.DIRECTORY_ID) UUID directoryId) throws DirectoryNotFoundException {

        LOG.info("delete directory id:{}", directoryId);

        Directory directory = directoryService.find(directoryId);

        directoryService.deleteDirectory(directory);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY)
    public void updateDirectory(@RequestParam(QueryParam.DIRECTORY_ID) UUID directoryId, UpdateDirectoryDto updateDirectoryDto) throws DirectoryNotFoundException {

        LOG.info("update directory id:{}", directoryId);

        Directory directory = directoryService.find(directoryId);

        directoryService.update(directory, updateDirectoryDto);
    }

    @ApiOperation(value = "Delete a directory")
    @PutMapping(Route.DIRECTORY_MOVE)
    public void moveDirectory(@RequestParam(QueryParam.DIRECTORY_ID) UUID directoryId, MoveDirectoryDto moveDirectoryDto) throws DirectoryNotFoundException {

        LOG.info("move directory from:{} - to:{}", directoryId, moveDirectoryDto.getMoveToDirectoryId());

        Directory directory = directoryService.find(directoryId);
        Directory moveToDirectory = directoryService.find(moveDirectoryDto.getMoveToDirectoryId());

        directoryService.move(directory, moveToDirectory);
    }
}
