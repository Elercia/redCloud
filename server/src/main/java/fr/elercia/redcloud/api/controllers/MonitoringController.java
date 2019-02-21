package fr.elercia.redcloud.api.controllers;

import fr.elercia.redcloud.api.controllers.params.Route;
import fr.elercia.redcloud.api.dto.BusinessMapper;
import fr.elercia.redcloud.api.dto.DtoMapper;
import fr.elercia.redcloud.api.dto.entity.MonitorIntegrityCheckRequestDto;
import fr.elercia.redcloud.api.dto.entity.MonitorIntegrityCheckResultDto;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckRequest;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckResult;
import fr.elercia.redcloud.api.security.RequireUserType;
import fr.elercia.redcloud.business.entity.UserType;
import fr.elercia.redcloud.business.service.MonitoringService;
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

    private MonitoringService monitoringService;

    @Autowired
    public MonitoringController(MonitoringService monitoringService) {

        this.monitoringService = monitoringService;
    }

    @GetMapping(Route.MONITORING_STATUS)
    @ApiOperation(value = "Monitoring API")
    @RequireUserType({UserType.ADMIN, UserType.MONITOR})
    public ResponseEntity<Void> monitor() {
        return new ResponseEntity<>(HttpStatus.OK);// TODO Maybe add something like get server version
    }

    @GetMapping(Route.MONITORING_INTEGRITY_CHECK)
    @ApiOperation(value = "Check the system integrity")
    @RequireUserType({UserType.ADMIN, UserType.MONITOR})
    public MonitorIntegrityCheckResultDto checkSystemIntegrity(@RequestBody MonitorIntegrityCheckRequestDto monitorIntegrityCheckRequestDto) {

        return DtoMapper.entityToDto(monitoringService.checkSystemIntegrity(BusinessMapper.dtoToEntity(monitorIntegrityCheckRequestDto)));
    }
}
