package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.QueryParam;
import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.entity.MoveFileDto;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.service.DirectoryService;
import fr.elercia.redcloud.business.service.FileService;
import fr.elercia.redcloud.business.service.FileSystemUtils;
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
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    private FileService fileService;
    private DirectoryService directoryService;

    @Autowired
    public FileController(FileService fileService, DirectoryService directoryService) {

        this.fileService = fileService;
        this.directoryService = directoryService;
    }

    @ApiOperation(value = "Download a file")
    @GetMapping(Route.FILE)
    public ResponseEntity<Resource> download(@RequestParam(QueryParam.FILE_ID) UUID fileId) throws FileNotFoundException {

        LOG.info("download file id:{}", fileId);

        File file = fileService.find(fileId);

        // Load file as Resource
        Resource resource = fileService.downloadFile(file);

        return ResponseEntity.ok()
                .contentType(FileSystemUtils.getMediaTypeFromExtension(file.getFileName()))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getFileName() + "\"")
                .body(resource);
    }

    @ApiOperation(value = "Delete a file")
    @DeleteMapping(Route.FILE)
    public void deleteFile(@RequestParam(QueryParam.FILE_ID) UUID fileId) throws FileNotFoundException {

        File file = fileService.find(fileId);

        fileService.delete(file);
    }

    @ApiOperation(value = "Move a file")
    @PutMapping(Route.FILE)
    public void moveFile(@RequestParam(QueryParam.FILE_ID) UUID fileId, MoveFileDto moveFileDto) throws FileNotFoundException, DirectoryNotFoundException, FileOperationException {

        File file = fileService.find(fileId);
        Directory directory = directoryService.find(moveFileDto.getDirectoryId());

        fileService.move(file, directory);
    }
}