package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.CreateDirectoryDto;
import fr.elercia.redcloud.api.dto.entity.DirectoryDto;
import fr.elercia.redcloud.api.dto.entity.UserDto;
import fr.elercia.redcloud.api.route.QueryParam;
import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.service.DirectoryService;
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
    public ResponseEntity<DirectoryDto> createDirectory(@RequestParam(QueryParam.DIRECTORY_ID) UUID parentDirectoryId, @RequestBody CreateDirectoryDto wantedDirectory) {

        LOG.info("create directory for parent {} ", parentDirectoryId);

        Directory parentDir = directoryService.find(parentDirectoryId);

        DirectoryDto directory = DtoMapper.entityToDto(directoryService.createSubSirectory(parentDir, wantedDirectory));

        return new ResponseEntity<>(directory, HttpStatus.CREATED);
    }
}
