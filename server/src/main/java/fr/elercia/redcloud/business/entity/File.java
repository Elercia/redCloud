package fr.elercia.redcloud.business.entity;

import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;
import java.util.UUID;

@Entity
public class File {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique = true)
    @Type(type = "uuid-char")
    private UUID resourceId;

    @Column
    private Date creationDate;

    @ManyToOne
    private Directory directory;

    @Column
    private String fileName;

    @Column
    private long size;

    public File() {

    }

    public File(String fileName, Directory parenDirectory, long size) {
        this.fileName = fileName;
        this.size = size;
        this.resourceId = UUID.randomUUID();
        this.creationDate = new Date();
        this.directory = parenDirectory;
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

    public Directory getDirectory() {
        return directory;
    }

    public void setDirectory(Directory directory) {
        this.directory = directory;
    }

    public long getSize() {
        return size;
    }

    public void setSize(long size) {
        this.size = size;
    }
}
