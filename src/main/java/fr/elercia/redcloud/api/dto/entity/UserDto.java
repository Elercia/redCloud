package fr.elercia.redcloud.api.dto.entity;

import fr.elercia.redcloud.business.entity.PrivilegeType;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class UserDto extends SimpleUserDto {

    private UUID resourceId;
    private Date createdDate;

    public UserDto(String name, UUID resourceId, List<PrivilegeType> privileges, Date createdDate) {
        super(name, privileges);
        this.resourceId = resourceId;
        this.createdDate = createdDate;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public Date getCreatedDate() {
        return createdDate;
    }
}
