package redcloud.business.entity;

import redcloud.dao.schema.TableName;
import redcloud.dao.schema.UserPrivilegeColumn;

public class Privilege {

    private Long id;
    private PrivilegeType privilegeType;

    public Long getId() {
        return id;
    }

    public PrivilegeType getPrivilegeType() {
        return privilegeType;
    }
}
