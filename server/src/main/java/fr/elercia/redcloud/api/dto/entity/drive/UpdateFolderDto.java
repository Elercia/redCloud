package fr.elercia.redcloud.api.dto.entity.drive;

public class UpdateFolderDto {

    private String name;

    public UpdateFolderDto() {
    }

    public UpdateFolderDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
