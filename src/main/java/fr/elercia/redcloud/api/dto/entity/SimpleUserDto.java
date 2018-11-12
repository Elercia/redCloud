package fr.elercia.redcloud.api.dto.entity;

public class SimpleUserDto {

    private String name;

    public SimpleUserDto() {

    }

    public SimpleUserDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
