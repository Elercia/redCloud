package redcloud.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.concurrent.TimeUnit;

public class SessionConfig {

    public static final TimeUnit STORE_TIME_UNIT = TimeUnit.HOURS;
    public static final long STORE_TIME = 1;
    public static int MAX_SESSION_STORE = Integer.MAX_VALUE;
}
