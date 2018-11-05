package fr.elercia.redcloud.dao.entity;

import java.util.Date;
import java.util.UUID;

public class DirectoryBase {

    private int id;
    private String name;
    private Integer parentId;
    private UUID resourceID;
    private Date creationDate;
    private int userId;

    public DirectoryBase(int id, String name, Integer parentId, UUID resourceID, Date creationDate, int userId) {
        this.id = id;
        this.name = name;
        this.parentId = parentId;
        this.resourceID = resourceID;
        this.creationDate = creationDate;
        this.userId = userId;
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

    public UUID getResourceId() {
        return resourceID;
    }

    public void setResourceID(UUID resourceID) {
        this.resourceID = resourceID;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
