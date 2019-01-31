package fr.elercia.redcloud.api.dto.entity;

public class UpdateUserDto extends SimpleUserDto{

    private String password;

    public UpdateUserDto() {

    }

    public UpdateUserDto(String name, String password) {
        super(name);
        this.password = password;
    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String getPassword() {
        return password;
    }
}
