package fr.elercia.redcloud.business.entity;

import java.util.Date;
import java.util.UUID;

public class SimpleDirectory {
    private int id;
    private String name;
    private UUID resourceId;
    private Date creationDate;
    private User user;

    public SimpleDirectory(String name, User user) {
        this(0, name, UUID.randomUUID(), new Date(), user);
    }

    public SimpleDirectory(int id, String name, UUID resourceId, Date creationDate, User user) {
        this.id = id;
        this.name = name;
        this.resourceId = resourceId;
        this.creationDate = creationDate;
        this.user = user;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
