package fr.elercia.redcloud.api.dto.entity.drive;

import java.util.Date;
import java.util.UUID;

public class FileDto {

    private String name;
    private UUID resourceId;
    private Date creationDate;
    private long size;

    public FileDto() {

    }

    public FileDto(String name, UUID resourceId, Date creationDate, long size) {
        this.name = name;
        this.resourceId = resourceId;
        this.creationDate = creationDate;
        this.size = size;
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

    public long getSize() {
        return size;
    }
}
