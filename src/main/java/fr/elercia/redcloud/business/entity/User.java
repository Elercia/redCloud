package fr.elercia.redcloud.business.entity;

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

    @Column
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

    @OneToMany(mappedBy = "user")
    private List<Directory> directories = new ArrayList<>();

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

    public Directory getRootDirectory() {
        for (Directory d : directories) {
            if (d.getParentDirectory() == null) {
                return d;
            }
        }
        return null;
    }

    public List<Directory> getDirectories() {
        return directories;
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


    public void setRootDirectory(Directory rootDirectory) {

        this.directories.add(rootDirectory);
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
