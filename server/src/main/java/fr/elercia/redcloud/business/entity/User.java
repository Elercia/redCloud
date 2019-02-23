package fr.elercia.redcloud.business.entity;

import fr.elercia.redcloud.business.entity.drive.DriveFolder;
import fr.elercia.redcloud.business.service.PasswordEncoder;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Entity
public class User {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    private String name;

    @Column(unique = true)
    @Type(type = "uuid-char")
    private UUID resourceId;

    @Column
    private String hashedPassword;

    @Column
    @Enumerated(EnumType.STRING)
    private UserType userType;

    @Column
    private Date creationDate;

    @OneToMany(mappedBy = "user", cascade = CascadeType.REMOVE)
    private List<DriveFolder> driveFolders = new ArrayList<>();

    public User() {

    }

    public User(String name, String hashedPassword, UserType userType) {
        this.name = name;
        this.hashedPassword = hashedPassword;
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


    public String getHashedPassword() {
        return hashedPassword;
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

    public void updateUnhashedPassword(String notHashedPassword) {
        if (notHashedPassword != null)
            this.hashedPassword = PasswordEncoder.encode(notHashedPassword);
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

        if (!(obj instanceof User)) {
            return false;
        }

        User other = (User) obj;

        return this.resourceId.equals(other.resourceId);
    }

    @Override
    public int hashCode() {
        return resourceId.hashCode();
    }
}
