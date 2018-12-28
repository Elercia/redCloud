package fr.elercia.redcloud.api.route;

public class Route {
    public static final String BASE = "/api";

    public static final String TEST = BASE + "/test";
    public static final String PING = TEST + "/ping";
    public static final String TEST_API = TEST + "/test";

    public static final String MONITORING = BASE + "/monitoring";
    public static final String MONITORING_STATUS = MONITORING + "/status";

    public static final String USERS = BASE + "/users";
    public static final String USERS_NAME = USERS + "/name";
    public static final String USER = BASE + "/user/" + QueryParam.USER_ID + "";

    public static final String DIRECTORIES = BASE + "/directories";
    public static final String DIRECTORY = BASE + "/directory/" + QueryParam.DIRECTORY_ID;
    public static final String DIRECTORY_MOVE = DIRECTORY + "/move/" ;

    public static final String FILES = BASE + "/files";
    public static final String FILE = BASE + "/file/" + QueryParam.FILE_ID;

    public static final String SESSION = BASE + "/session";
    public static final String LOGIN = SESSION + "/login";
    public static final String LOGOUT = SESSION + "/logout";

    private Route() {
    }
}
