package fr.elercia.redcloud.business.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class Directory {

    @Id
    @GeneratedValue
    private int id;

    @Column
    private String name;

    @Column(unique = true)
    @Type(type = "uuid-char")
    private UUID resourceId;

    @Column
    private Date creationDate;

    @ManyToOne
    private User user;

    @ManyToOne
    Directory parentDirectory;

    @OneToMany(mappedBy = "parentDirectory")
    private List<Directory> subFolders;

    @OneToMany(mappedBy = "directory")
    private List<File> files;

    public Directory() {

    }

    public Directory(String name, User user, Directory parentDirectory) {
        this.name = name;
        this.resourceId = UUID.randomUUID();
        this.creationDate = new Date();
        this.user = user;
        this.parentDirectory = parentDirectory;
        this.files = new ArrayList<>();
        this.subFolders = new ArrayList<>();
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

    public List<Directory> getSubFolders() {
        return subFolders;
    }

    public List<File> getFiles() {
        return files;
    }

    public Directory getParentDirectory() {
        return parentDirectory;
    }
}
