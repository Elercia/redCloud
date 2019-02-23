package fr.elercia.redcloud.business.entity.drive;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class DriveFile {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    @Type(type = "uuid-char")
    private UUID resourceId;

    @Column
    private Date creationDate;

    @ManyToOne
    private DriveFolder parent;

    @Column
    private String fileName;

    @Column
    private long size;

    public DriveFile() {

    }

    public DriveFile(String fileName, DriveFolder parenDriveFolder, long size) {
        this.fileName = fileName;
        this.size = size;
        this.resourceId = UUID.randomUUID();
        this.creationDate = new Date();
        this.parent = parenDriveFolder;
    }

    public int getId() {
        return id;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public UUID getResourceId() {
        return resourceId;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public DriveFolder getParent() {
        return parent;
    }

    public void setParent(DriveFolder parent) {
        this.parent = parent;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
