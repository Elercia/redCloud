package fr.elercia.redcloud.business.service;

import org.apache.commons.codec.digest.DigestUtils;

public class HashPasswordUtils {

    public static String hashString(String originalString) {
        return DigestUtils.sha256Hex(originalString);
    }
}
