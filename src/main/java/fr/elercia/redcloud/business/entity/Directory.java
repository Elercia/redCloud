package fr.elercia.redcloud.business.entity;

import java.util.Date;
import java.util.UUID;

public class Directory {

    private int id;
    private String name;
    private Directory parentDirectory;
    private UUID resourceId;
    private Date creationDate;

    public Directory(int id, String name, Directory parentDirectory, UUID resourceId, Date creationDate) {
        this.id = id;
        this.name = name;
        this.parentDirectory = parentDirectory;
        this.resourceId = resourceId;
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

    public Directory getDirectory() {
        return parentDirectory;
    }

    public void setDirectory(Directory directory) {
        this.parentDirectory = directory;
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
}
