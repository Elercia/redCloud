package fr.elercia.redcloud.api.dto.entity.drive;

public class CreateFolderDto {

    private String name;

    public CreateFolderDto() {

    }

    public CreateFolderDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
