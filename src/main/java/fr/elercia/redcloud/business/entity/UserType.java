package fr.elercia.redcloud.business.entity;

public enum UserType {
    ADMIN,
    USER;

    public boolean isAdmin() {
        return this == ADMIN;
    }
}
