package fr.elercia.redcloud.business.entity;

public enum UserType {
    ADMIN,
    USER;

    // ADD MONITORING type for MonitoringController

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
