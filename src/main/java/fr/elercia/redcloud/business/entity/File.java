package fr.elercia.redcloud.business.entity;

import java.util.Date;
import java.util.UUID;

public class File {

    private int id;
    private String name;
    private UUID resourceId;
    private Date creationDate;
    private SimpleDirectory parenDirectory;

    public File(int id, String name, UUID resourceId, Date creationDate, SimpleDirectory parenDirectory) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.creationDate = creationDate;
        this.parenDirectory = parenDirectory;
    }

    public int getId() {
        return id;
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

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public SimpleDirectory getParenDirectory() {
        return parenDirectory;
    }

    public void setParenDirectory(SimpleDirectory parenDirectory) {
        this.parenDirectory = parenDirectory;
    }
}
