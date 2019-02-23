package fr.elercia.redcloud.business.entity.drive;

import fr.elercia.redcloud.business.entity.User;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class DriveFolder {

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
    private DriveFolder parentDriveFolder;

    @OneToMany(mappedBy = "parentDriveFolder", cascade = CascadeType.REMOVE)
    private List<DriveFolder> subFolders = new ArrayList<>();

    @OneToMany(mappedBy = "parent", cascade = CascadeType.REMOVE)
    private List<DriveFile> driveFiles = new ArrayList<>();

    public DriveFolder() {

    }

    public DriveFolder(String name, User user, DriveFolder parentDriveFolder) {
        this.name = name;
        this.resourceId = UUID.randomUUID();
        this.creationDate = new Date();
        this.user = user;
        this.parentDriveFolder = parentDriveFolder;
        this.driveFiles = new ArrayList<>();
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

    public List<DriveFolder> getSubFolders() {
        return subFolders;
    }

    public List<DriveFile> getDriveFiles() {
        return driveFiles;
    }

    public DriveFolder getParentDriveFolder() {
        return parentDriveFolder;
    }

    public void setParentDriveFolder(DriveFolder parentDriveFolder) {
        this.parentDriveFolder = parentDriveFolder;
    }

    public void updateName(String name) {
        if (name != null)
            this.name = name;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof DriveFolder)) {
            return false;
        }

        DriveFolder other = (DriveFolder) obj;

        return this.resourceId.equals(other.resourceId);
    }

    @Override
    public int hashCode() {
        return resourceId.hashCode();
    }
}
