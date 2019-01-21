package fr.elercia.redcloud.business.service;

import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.stereotype.Component;

@Component
public class PasswordEncoder {

    public static String encode(String rawPassword) {
        return DigestUtils.sha256Hex(rawPassword);
    }

    public static boolean matches(String rawPassword, String encodedPassword) {
        return encode(rawPassword).equals(encodedPassword);
    }
}
