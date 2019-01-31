package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.security.PermitAll;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Api(value = "Operations to monitor the REST server")
@RestController
@RequestMapping("/")
public class MonitoringController extends AbstractController {

    @Autowired
    public MonitoringController() {
        // Add all monitoring dependencies
    }

    @GetMapping(Route.MONITORING_STATUS)
    @ApiOperation(value = "Monitoring API")
    @PermitAll
    public ResponseEntity<Void> monitor() {
        return new ResponseEntity<>(HttpStatus.OK);
    }

    //TODO Add something like CheckSystemIntegrity (check database and file system coherence)
}
