package redcloud.business.entity;

import redcloud.dao.schema.TableName;
import redcloud.dao.schema.UserPrivilegeColumn;

import javax.persistence.*;

@Entity
@Table(name = TableName.PRIVILEGE)
public class Privilege {

    public enum Type {
        SUPER_ADMIN,
    }

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = UserPrivilegeColumn.ID)
    private Long id;

    @Column(name = UserPrivilegeColumn.TYPE)
    @Enumerated(value = EnumType.STRING)
    private Type privilegeType;

    public Long getId() {
        return id;
    }

    public Type getPrivilegeType() {
        return privilegeType;
    }
}
