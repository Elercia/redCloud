package fr.elercia.redcloud.api.dto.entity;

import java.util.Date;
import java.util.UUID;

public class SimpleFolderDto {
    private String name;
    private UUID resourceId;
    private Date creationDate;

    public SimpleFolderDto() {

    }

    public SimpleFolderDto(String name, UUID resourceId, Date creationDate) {
        this.name = name;
        this.resourceId = resourceId;
        this.creationDate = creationDate;
    }

    public String getName() {
        return name;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public Date getCreationDate() {
        return creationDate;
    }
}
