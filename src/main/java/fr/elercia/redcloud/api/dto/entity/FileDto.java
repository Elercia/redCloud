package fr.elercia.redcloud.api.dto.entity;

import java.util.Date;
import java.util.UUID;

public class FileDto {

    private String name;
    private UUID resourceId;
    private Date creationDate;

    public FileDto() {

    }

    public FileDto(String name, UUID resourceId, Date creationDate) {
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
