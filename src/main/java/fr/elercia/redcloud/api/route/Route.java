package fr.elercia.redcloud.api.route;

public class Route {
    public static final String BASE = "/api/";

    public static final String TEST = BASE + "test/";
    public static final String PING = TEST + "ping/";
    public static final String TEST_SERVICE = TEST + "testservice/";

    public static final String USERS = BASE + "users/";
    public static final String USERS_NAME = USERS + "name/";
    public static final String USER = BASE + "user/" + QueryParam.USER_ID + "/";

    public static final String SESSION = BASE + "session/";
    public static final String LOGIN = SESSION + "login/";
    public static final String LOGOUT = SESSION + "logout/";

    private Route() {
    }
}
