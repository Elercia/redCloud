package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.api.dto.entity.MonitorIntegrityCheckRequestDto;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckRequest;
import fr.elercia.redcloud.business.entity.MonitorIntegrityCheckResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class MonitoringService {

    @Autowired
    public MonitoringService() {

    }

    public MonitorIntegrityCheckResult checkSystemIntegrity(MonitorIntegrityCheckRequest monitorIntegrityCheckRequest) {
        return null; //TODO
    }
}
