package fr.elercia.redcloud.business.entity;

import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class AppUser {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    @Type(type = "uuid-char")
    private UUID resourceId;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    private Date creationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<DriveFolder> driveFolders = new ArrayList<>();

    public AppUser() {

    }

    public AppUser(String name, UserType userType) {
        this.name = name;
        this.userType = userType;
        this.creationDate = new Date();
        this.resourceId = UUID.randomUUID();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public UserType getUserType() {
        return userType;
    }

    public Date getCreationDate() {
        return creationDate;
    }


    public UUID getResourceId() {
        return resourceId;
    }

    public DriveFolder getRootFolder() {
        for (DriveFolder d : driveFolders) {
            if (d.getParentDriveFolder() == null) {
                return d;
            }
        }
        return null;
    }

    public List<DriveFolder> getDriveFolders() {
        return driveFolders;
    }

    public void updateUserType(UserType userType) {
        if (userType != null)
            this.userType = userType;
    }

    public void updateName(String name) {
        if (name != null)
            this.name = name;
    }


    public void setRootDriveFolder(DriveFolder rootDriveFolder) {

        this.driveFolders.add(rootDriveFolder);
    }

    public void setUserType(UserType userType) {
        this.userType = userType;
    }

    @Override
    public boolean equals(Object obj) {

        if (!(obj instanceof AppUser)) {
            return false;
        }

        AppUser other = (AppUser) obj;

        return this.resourceId.equals(other.resourceId);
    }

    @Override
    public int hashCode() {
        return resourceId.hashCode();
    }
}
