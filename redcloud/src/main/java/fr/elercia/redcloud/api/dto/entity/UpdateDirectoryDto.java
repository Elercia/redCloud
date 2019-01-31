package fr.elercia.redcloud.api.dto.entity;

public class UpdateDirectoryDto {

    private String name;

    public UpdateDirectoryDto() {
    }

    public UpdateDirectoryDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
