package redcloud.entity;

import redcloud.dao.schema.AbstractTableColumn;
import redcloud.dao.schema.TableName;
import redcloud.dao.schema.UserColumn;
import redcloud.dao.schema.UserPrivilegeColumn;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table(name = TableName.USER)
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = AbstractTableColumn.ID)
    private Long id;

    @Column(name = UserColumn.NAME, nullable = false)
    private String name;

    @OneToMany(cascade = CascadeType.ALL,
            fetch = FetchType.LAZY)
    @JoinColumn(name = UserPrivilegeColumn.USER_ID)
    private List<Privilege> privileges;

    @Column(name = UserColumn.CREATION_DATE, nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createdDate; // TODO Timestamp are not managed the same between mysql server and Java

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Privilege> getPrivileges() {
        return privileges;
    }

    public void setPrivileges(List<Privilege> privileges) {
        this.privileges = privileges;
    }

    public Date getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Date createdDate) {
        this.createdDate = createdDate;
    }
}
