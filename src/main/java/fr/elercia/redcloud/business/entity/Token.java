package fr.elercia.redcloud.business.entity;

import java.util.Date;

public class Token {

    private String accessToken;
    private String tokenType;
    private long expireIn;
    private Date creationDate;
    private String refreshToken;
    private User storedUser;

    public Token(String accessToken, String tokenType, long expireIn, String refreshToken, User storedUser) {

        this.accessToken = accessToken;
        this.tokenType = tokenType;
        this.expireIn = expireIn;
        this.creationDate = new Date();
        this.refreshToken = refreshToken;
        this.storedUser = storedUser;
    }

    public Date getCreationDate() {
        return creationDate;
    }

    public User getStoredUser() {
        return storedUser;
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

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
    }

    public void setTokenType(String tokenType) {
        this.tokenType = tokenType;
    }

    public void setExpireIn(long expireIn) {
        this.expireIn = expireIn;
    }

    public void setCreationDate(Date creationDate) {
        this.creationDate = creationDate;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public void setStoredUser(User storedUser) {
        this.storedUser = storedUser;
    }
}
