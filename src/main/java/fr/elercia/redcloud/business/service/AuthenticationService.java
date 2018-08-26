package fr.elercia.redcloud.business.service;

import fr.elercia.redcloud.business.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class AuthenticationService {

    private Map<String, User> tokensMap;

    @Autowired
    public AuthenticationService() {

    }

    public User retreiveFromToken(String token) {
        return null;
    }

    public String newToken() {
        return null;
    }
}
