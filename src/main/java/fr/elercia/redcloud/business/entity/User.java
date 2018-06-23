package fr.elercia.redcloud.business.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class User {

    private int id;
    private String name;
    private UUID resourceId;
    private String password;
    private List<PrivilegeType> privileges;
    private Date createdDate;
    private Directory rootDirectory;

    public User(int id, String name, UUID resourceId, String password, List<PrivilegeType> privileges, Date createdDate, Directory rootDirectory) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.password = password;
        this.privileges = privileges;
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

    public List<PrivilegeType> getPrivilegesTypes() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeType> privileges) {
        this.privileges = privileges;
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

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Directory getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;

        return this.id == other.id;
    }
}
