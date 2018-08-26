package fr.elercia.redcloud.api.dto.entity;

public class LoginDto {
    private String usename;
    private String password;

    public LoginDto(String usename, String password) {
        this.usename = usename;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsename() {
        return usename;
    }
}
