package fr.elercia.redcloud.api.dto.entity.drive;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class FolderDto extends SimpleFolderDto {

    private List<SimpleFolderDto> subFolders;
    private List<FileDto> files;

    public FolderDto() {

    }

    public FolderDto(String name, UUID resourceId, Date creationDate, List<SimpleFolderDto> subFolders, List<FileDto> files) {
        super(name, resourceId, creationDate);
        this.subFolders = subFolders;
        this.files = files;
    }

    public List<SimpleFolderDto> getSubFolders() {
        return subFolders;
    }

    public List<FileDto> getFiles() {
        return files;
    }
}
