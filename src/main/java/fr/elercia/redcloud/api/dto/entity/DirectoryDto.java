package fr.elercia.redcloud.api.dto.entity;

import java.util.Date;
import java.util.List;
import java.util.UUID;

public class DirectoryDto extends SimpleDirectoryDto {

    private List<SimpleDirectoryDto> subFolders;
    private List<FileDto> files;

    public DirectoryDto() {

    }

    public DirectoryDto(String name, UUID resourceId, Date creationDate, List<SimpleDirectoryDto> subFolders, List<FileDto> files) {
        super(name, resourceId, creationDate);
        this.subFolders = subFolders;
        this.files = files;
    }

    public List<SimpleDirectoryDto> getSubFolders() {
        return subFolders;
    }

    public List<FileDto> getFiles() {
        return files;
    }
}
