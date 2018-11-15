package fr.elercia.redcloud.business.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class Directory extends SimpleDirectory{

    private List<SimpleDirectory> subFolders;
    private List<File> files;

    public Directory(int id, String name, UUID resourceId, Date creationDate, User user, List<SimpleDirectory> subFolders, List<File> files) {
        super(id, name, resourceId, creationDate, user);
        this.subFolders = subFolders;
        this.files = files;
    }

    public List<SimpleDirectory> getSubFolders() {
        return subFolders;
    }

    public void setSubFolders(List<SimpleDirectory> subFolders) {
        this.subFolders = subFolders;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }
}
