package fr.elercia.redcloud.api.dto.entity;

import fr.elercia.redcloud.business.entity.MonitoringIntegrityCheckType;

public class MonitorIntegrityCheckResultDto {

    private MonitoringIntegrityCheckType actionType;

    public MonitorIntegrityCheckResultDto(MonitoringIntegrityCheckType actionType) {
        this.actionType = actionType;
    }

    public MonitoringIntegrityCheckType getActionType() {
        return actionType;
    }
}
