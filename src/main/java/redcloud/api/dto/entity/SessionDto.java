package redcloud.api.dto.entity;

public class SessionDto {

    private String token;

    public SessionDto(String token) {
        this.token = token;
    }

    public String getToken() {
        return token;
    }
}
