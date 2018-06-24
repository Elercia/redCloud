package fr.elercia.redcloud.dao.entity;

import java.util.Date;
import java.util.UUID;

public class DirectoryBase {

    private int id;
    private String name;
    private UUID resourceID;
    private Date creationDate;

    public DirectoryBase(int id, String name, UUID resourceID, Date creationDate) {
        this.id = id;
        this.name = name;
        this.resourceID = resourceID;
        this.creationDate = creationDate;
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
}
