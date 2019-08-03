package fr.elercia.redcloud.api.dto.entity;

public class TokenDto {

    private String accessToken;

    public TokenDto() {

    }

    public TokenDto(String accessToken) {

        this.accessToken = accessToken;
    }

    public String getAccessToken() {
        return accessToken;
    }
}
