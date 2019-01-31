package fr.elercia.redcloud.api.dto.entity;

public class LoginDto {
    private String username;
    private String password;

    public LoginDto() {

    }

    public LoginDto(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    public String getUsername() {
        return username;
    }
}
