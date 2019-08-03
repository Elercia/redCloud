package fr.elercia.redcloud.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;

public class TokenUtils {

    public static String buildToken(String issuer, String secret, String userUid) {
        Algorithm algorithm = Algorithm.HMAC256(secret);

        return JWT.create()
                .withIssuer(issuer)
                .withSubject(userUid)
                .sign(algorithm);
    }
}
