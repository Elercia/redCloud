package fr.elercia.redcloud.api.dto.entity;

import fr.elercia.redcloud.business.entity.MonitoringIntegrityCheckType;

public class MonitorIntegrityCheckRequestDto {

    private MonitoringIntegrityCheckType actionType;

    public MonitorIntegrityCheckRequestDto() {
        // Default constructor
    }

    public MonitoringIntegrityCheckType getActionType() {
        return actionType;
    }
}
