package redcloud.api.dto.entity;

import redcloud.business.entity.PrivilegeType;

import java.util.List;

public class SimpleUserDto {

    private String name;
    private List<PrivilegeType> privileges;

    public SimpleUserDto(String name, List<PrivilegeType> privileges) {
        this.name = name;
        this.privileges = privileges;
    }

    public List<PrivilegeType> getPrivileges() {
        return privileges;
    }

    public String getName() {
        return name;
    }
}
