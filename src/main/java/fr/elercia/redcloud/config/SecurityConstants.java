package fr.elercia.redcloud.config;

public class SecurityConstants {
    public static final long EXPIRATION_TIME = 1 * 60 * 60 * 1000; // on hour
    public static final String TOKEN_TYPE = "Bearer";
    public static final String REQUEST_HEADER_NAME = "Authorization";
    public static final String RESPONSE_HEADER_NAME = "WWW-Authenticate";
    public static final String RESPONSE_HEADER_VALUE = TOKEN_TYPE + " realm=\"example\"";

}