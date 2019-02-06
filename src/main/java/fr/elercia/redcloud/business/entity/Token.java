package fr.elercia.redcloud.business.entity;

import fr.elercia.redcloud.config.SecurityConstants;

import javax.persistence.*;
import java.util.Date;

@Entity
public class Token {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long id;
    @Column
    private String accessToken;
    @Column
    private String refreshToken;
    @Column
    private Date expiringDate;
    @Column
    private Date creationDate;
    @OneToOne
    private User storedUser;

    public Token() {

    }

    public Token(String accessToken, String refreshToken, User storedUser) {
        this.accessToken = accessToken;
        this.refreshToken = refreshToken;
        this.creationDate = new Date();
        this.expiringDate = new Date(new Date().getTime() + SecurityConstants.EXPIRATION_TIME);
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

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setAccessToken(String accessToken) {
        this.accessToken = accessToken;
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

    public Date getExpiringDate() {
        return expiringDate;
    }

    public void setExpiringDate(Date expiringDate) {
        this.expiringDate = expiringDate;
    }
}
