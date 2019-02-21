package fr.elercia.redcloud.business.entity;

public class MonitorIntegrityCheckRequest {

    private MonitoringIntegrityCheckType actionType;

    public MonitorIntegrityCheckRequest(MonitoringIntegrityCheckType actionType) {
        this.actionType = actionType;
    }

    public MonitoringIntegrityCheckType getActionType() {
        return actionType;
    }
}
