package fr.elercia.redcloud.api.dto.entity;

public class CreateUserDto extends SimpleUserDto {

    private String unHashedPassword;

    public CreateUserDto() {
        super(null);
    }

    public CreateUserDto(String name, String unHashedPassword) {
        super(name);
        this.unHashedPassword = unHashedPassword;
    }

    public String getUnHashedPassword() {
        return unHashedPassword;
    }
}
