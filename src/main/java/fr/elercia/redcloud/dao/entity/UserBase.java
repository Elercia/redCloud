package fr.elercia.redcloud.dao.entity;

import fr.elercia.redcloud.business.entity.PrivilegeType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserBase {

    private int id;
    private String name;
    private Date creationDate;
    private UUID resourceId;
    private String password;
    private List<PrivilegeType> privileges;

    public UserBase(int id, String name, Date creationDate, UUID resourceId, String password, List<PrivilegeType> privileges) {
        this.id = id;
        this.name = name;
        this.creationDate = creationDate;
        this.resourceId = resourceId;
        this.password = password;
        this.privileges = privileges;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public List<PrivilegeType> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<PrivilegeType> privileges) {
        this.privileges = privileges;
    }
}
