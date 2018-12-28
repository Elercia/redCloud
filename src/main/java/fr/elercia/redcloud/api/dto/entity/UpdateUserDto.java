package fr.elercia.redcloud.api.dto.entity;

public class UpdateUserDto extends SimpleUserDto{

    private String password;

    public UpdateUserDto() {

    }

    @Override
    public String getName() {
        return super.getName();
    }

    public String getPassword() {
        return password;
    }
}
