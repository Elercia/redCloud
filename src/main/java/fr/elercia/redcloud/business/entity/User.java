package fr.elercia.redcloud.business.entity;

import java.util.Date;
import java.util.UUID;

public class User {

    private int id;
    private String name;
    private UUID resourceId;
    private String hashedPassword;
    private UserType userType;
    private Date createdDate;
    private Directory rootDirectory;

    public User(int id, String name, UUID resourceId, String hashedPassword, UserType userType, Date createdDate, Directory rootDirectory) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.hashedPassword = hashedPassword;
        this.userType = userType;
        this.createdDate = createdDate;
        this.rootDirectory = rootDirectory;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    public UserType getUserType() {
        return userType;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setHashedPassword(String hashedPassword) {
        this.hashedPassword = hashedPassword;
    }

    public String getHashedPassword() {
        return hashedPassword;
    }

    public Directory getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;

        return this.id == other.id;
    }
}
