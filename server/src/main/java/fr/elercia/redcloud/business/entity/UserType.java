package fr.elercia.redcloud.business.entity;

public enum UserType {
    ADMIN,
    USER,
    MONITOR;

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
