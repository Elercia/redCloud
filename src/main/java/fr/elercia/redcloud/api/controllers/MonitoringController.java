package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.LoginDto;
import fr.elercia.redcloud.api.dto.entity.TokenDto;
import fr.elercia.redcloud.api.route.Route;
import fr.elercia.redcloud.api.security.PermitAll;
import fr.elercia.redcloud.business.entity.Token;
import fr.elercia.redcloud.business.service.AuthenticationService;
import fr.elercia.redcloud.exceptions.InvalidLoginException;
import fr.elercia.redcloud.exceptions.TokenNotFoundException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Operations to monitor the REST server", description = "Monitor the server")
@RestController
@RequestMapping("/")
public class MonitoringController extends ControllerUtils {

    @Autowired
    public MonitoringController() {

    }

    @GetMapping(Route.MONITORING_STATUS)
    @ApiOperation(value = "Monitoring API")
    @PermitAll
    public ResponseEntity<Void> login() {
        return new ResponseEntity<>(HttpStatus.OK);
    }
}
