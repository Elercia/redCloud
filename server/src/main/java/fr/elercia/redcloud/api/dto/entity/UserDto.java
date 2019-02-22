package fr.elercia.redcloud.api.dto.entity;

import fr.elercia.redcloud.business.entity.UserType;

import java.util.Date;
import java.util.UUID;

public class UserDto extends SimpleUserDto {

    private UUID resourceId;
    private Date createdDate;
    private UserType userType;
    private FolderDto rootFolder;

    public UserDto() {

    }

    public UserDto(String name, UUID resourceId, UserType userType, Date createdDate, FolderDto rootFolder) {
        super(name);
        this.resourceId = resourceId;
        this.createdDate = createdDate;
        this.userType = userType;
        this.rootFolder = rootFolder;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public UserType getUserType() {
        return userType;
    }

    public FolderDto getRootFolder() {
        return rootFolder;
    }
}
