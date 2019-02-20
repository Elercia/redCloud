package fr.elercia.redcloud.api.dto.entity;

public class CreateDirectoryDto {

    private String name;

    public CreateDirectoryDto() {

    }

    public CreateDirectoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
