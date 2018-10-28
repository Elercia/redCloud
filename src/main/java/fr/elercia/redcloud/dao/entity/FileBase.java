package fr.elercia.redcloud.dao.entity;

import java.util.Date;
import java.util.UUID;

public class FileBase {
    private int id;
    private String name;
    private UUID resourceId;
    private Date creationDate;
    private int directoryId;


    public FileBase(int id, String name, UUID resourceId, Date creationDate, int directoryId) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.creationDate = creationDate;
        this.directoryId = directoryId;
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
        return resourceId;
    }

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public int getDirectoryId() {
        return directoryId;
    }

    public void setDirectoryId(int directoryId) {
        this.directoryId = directoryId;
    }
}
