package fr.elercia.redcloud.business.service.utils;

import java.util.UUID;

public class StringUtils {

    private StringUtils() {

    }

    public static boolean isNullOrEmpty(String str) {
        return isNull(str) || isEmpty(str);
    }

    public static boolean isNull(String str) {
        return str == null;
    }

    public static boolean isEmpty(String str) {
        return str.trim().isEmpty();
    }

    public static String randomString() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
}
