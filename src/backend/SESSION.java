package backend;

import backend.Entities.User;

public class SESSION {

    private static User user;

    public static User getSession() {
        if (user == null) {
            throw new IllegalStateException("User must be set before calling this method");
        }
        return user;
    }

    public static void setSession(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        SESSION.user = user;
    }

    public static void clear() {
        user = null;
    }

}