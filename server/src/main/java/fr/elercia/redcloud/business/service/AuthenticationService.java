package fr.elercia.redcloud.business.service;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.DecodedJWT;
import fr.elercia.redcloud.business.entity.AppUser;
import fr.elercia.redcloud.business.entity.DynamicConfig;
import fr.elercia.redcloud.exceptions.InvalidTokenException;
import fr.elercia.redcloud.exceptions.UserNotFoundException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class AuthenticationService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthenticationService.class);

    private UserService userService;
    private DynamicConfigService dynamicConfigService;
    private JWTVerifier verifier;

    @Autowired
    public AuthenticationService(UserService userService, DynamicConfigService dynamicConfigService) {

        this.userService = userService;
        this.dynamicConfigService = dynamicConfigService;

        Algorithm algorithm = Algorithm.HMAC256(this.dynamicConfigService.getString(DynamicConfig.DynamicConfigName.OAUTH_SECRET));
        verifier = JWT.require(algorithm)
                .withIssuer(this.dynamicConfigService.getString(DynamicConfig.DynamicConfigName.OAUTH_ISSUER))
                .build();
    }

    public AppUser getUserConnected(String jwtToken) throws InvalidTokenException {

        DecodedJWT token = deserializeToken(jwtToken);

        if (token == null) {
            throw new InvalidTokenException();
        }

        UUID uuid = UUID.fromString(token.getSubject());

        try {
            AppUser user = userService.findByResourceId(uuid);

            if (user == null) {
                throw new InvalidTokenException(); // TODO new AppUser ?
            }

            return user;
        } catch (UserNotFoundException e) {

            e.printStackTrace();
            throw new RuntimeException("error");
        }
    }

    private DecodedJWT deserializeToken(String token) {

        //Reusable verifier instance
        try {
            DecodedJWT jwt = verifier.verify(token);

            return jwt;
        } catch (JWTVerificationException exception) {
            return null;
        }
    }
}
