package fr.elercia.redcloud.business.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class User {

    private Long id;
    private String name;
    private UUID resourceId;
    private String password;
    private List<Privilege> privileges;
    private Date createdDate;
    private Directory rootDirectory;

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

    public List<PrivilegeType> getPrivilegesTypes() {
        return privileges.stream().map(Privilege::getPrivilegeType).collect(Collectors.toList());
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

    public void setResourceId(UUID resourceId) {
        this.resourceId = resourceId;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public Directory getRootDirectory() {
        return rootDirectory;
    }

    public void setRootDirectory(Directory rootDirectory) {
        this.rootDirectory = rootDirectory;
    }

    @Override
    public boolean equals(Object obj) {

        if (obj == null || !(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;

        return this.id.equals((other.id));
    }
}
