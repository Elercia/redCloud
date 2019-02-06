package fr.elercia.redcloud.api.dto.entity;

public class TokenDto {

    private String accessToken;
    private String tokenType;
    private long expireIn;
    private String refreshToken;

    public TokenDto() {

    }

    public TokenDto(String accessToken, String tokenType, long expireIn, String refreshToken) {

        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expireIn = expireIn;
        this.refreshToken = refreshToken;
    }

    public String getAccessToken() {
        return accessToken;
    }

    public String getTokenType() {
        return tokenType;
    }

    public long getExpireIn() {
        return expireIn;
    }

    public String getRefreshToken() {
        return refreshToken;
    }
}
