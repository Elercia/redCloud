package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.*;
import fr.elercia.redcloud.api.route.QueryParam;
import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.business.entity.Directory;
import fr.elercia.redcloud.business.entity.File;
import fr.elercia.redcloud.business.service.DirectoryService;
import fr.elercia.redcloud.business.service.FileService;
import fr.elercia.redcloud.exceptions.DirectoryNotFoundException;
import fr.elercia.redcloud.exceptions.FileNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Api(value = "Operations about files.", description = "Manage files and get info about them.")
@RestController
@RequestMapping("/")
public class FileController {

    private static final Logger LOG = LoggerFactory.getLogger(FileController.class);

    private FileService fileService;

    @Autowired
    public FileController(FileService fileService) {

        this.fileService = fileService;
    }

    @ApiOperation(value = "Get a file")
    @GetMapping(Route.FILE)
    public HttpEntity<byte[]> download(@RequestParam(QueryParam.FILE_ID) UUID fileId) throws FileNotFoundException {

        LOG.info("download file id:{}", fileId);

        File file = fileService.find(fileId);

        byte[] documentBody = fileService.getFileContent(file);

        HttpHeaders header = new HttpHeaders();
        header.setContentType(MediaType.APPLICATION_PDF);
        header.set(HttpHeaders.CONTENT_DISPOSITION,
                "attachment; filename=" + file.getName().replace(" ", "_"));
        header.setContentLength(documentBody.length);

        return new HttpEntity<>(documentBody, header);
    }

    @ApiOperation(value = "Get a file")
    @DeleteMapping(Route.FILE)
    public void deleteFile(@RequestParam(QueryParam.FILE_ID) UUID fileId) throws FileNotFoundException {

        File file = fileService.find(fileId);

        fileService.delete(file);
    }

    @ApiOperation(value = "Get a file")
    @PutMapping(Route.FILE)
    public void moveFile(@RequestParam(QueryParam.FILE_ID) UUID fileId, MoveFileDto moveFileDto) throws FileNotFoundException {

        File file = fileService.find(fileId);

        fileService.move(file, moveFileDto);
    }
}