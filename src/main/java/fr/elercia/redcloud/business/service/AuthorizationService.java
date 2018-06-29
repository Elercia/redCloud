package fr.elercia.redcloud.business.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import fr.elercia.redcloud.business.entity.User;
import fr.elercia.redcloud.config.SessionConfig;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import fr.elercia.redcloud.exceptions.WrongLoginException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.UUID;

@Service
public class AuthorizationService {

    private UserService userService;
    private Cache<String, User> tokens;


    @Autowired
    public AuthorizationService(UserService userService) {

        this.userService = userService;

        tokens = CacheBuilder.newBuilder()
                .maximumSize(SessionConfig.MAX_SESSION_STORE) // Taille Max
                .expireAfterWrite(SessionConfig.STORE_TIME, SessionConfig.STORE_TIME_UNIT) // TTL
                .build();
    }

    /**
     * Log a user in. Check if the username and password match
     *
     * @param username The given username
     * @param password The given password
     * @return The token if the connexion is successful
     * @throws WrongLoginException If no user is found for the given username
     * @throws WrongLoginException If the given password is not the user's password
     */
    public String login(String username, String password) throws WrongLoginException {

        try {
            User user = userService.findByName(username);

            if (!user.getPassword().equals(password)) { //TODO hash password maybe ? (I mean is the password already hash or have we to hash it here ?)
                throw new WrongLoginException();
            }

            // Create and store a new Session token
            String token = getToken(user);

            if (token == null) {
                token = UUID.randomUUID().toString().replaceAll("-", "");
            }

            tokens.put(token, user);

            return token;
        } catch (UserNotFoundException e) {
            e.printStackTrace();
            throw new WrongLoginException();
        }
    }

    /**
     * Get the session user from a token
     * @param token The specified token
     * @return The user if the token is present. Null otherwise
     */
    public User getUserToken(String token) {
        return tokens.getIfPresent(token);
    }

    /**
     * Remove the given token from the cached session token
     * @param token The token to remove
     */
    public void logout(String token) {

        tokens.invalidate(token);
    }

    /**
     * Get the token of a user if this user is present in the session token mapToBase. Otherwise return null.
     *
     * @param user The user to search in the mapToBase.
     * @return The token or null if the user is not in the mapToBase
     */
    private String getToken(User user) {

        for (Map.Entry<String, User> entry : tokens.asMap().entrySet()) {
            if (entry.getValue().equals(user)) {
                return entry.getKey();
            }
        }

        return null;
    }
}
