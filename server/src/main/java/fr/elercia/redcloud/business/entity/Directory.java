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

    @OneToMany(mappedBy = "parentDirectory", cascade = CascadeType.REMOVE)
    private List<Directory> subFolders = new ArrayList<>();

    @OneToMany(mappedBy = "directory", cascade = CascadeType.REMOVE)
    private List<File> files = new ArrayList<>();

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

    public void setParentDirectory(Directory parentDirectory) {
        this.parentDirectory = parentDirectory;
    }

    public void updateName(String name) {
        if (name != null)
            this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof Directory)) {
            return false;
        }

        Directory other = (Directory) obj;

        return this.resourceId.equals(other.resourceId);
    }

    @Override
    public int hashCode() {
        return resourceId.hashCode();
    }
}
