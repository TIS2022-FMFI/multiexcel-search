package backend.Checkers;

import backend.Sessions.DBS;
import backend.Sessions.SESSION;

import backend.Entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordChecker {


    private String userName;
    private String password;

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserName() {
        return userName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    private String getPassword() {
        return password;
    }

    /**
     * Returns user by his/her name
     *
     * @param userName username
     *
     * @return User if succeeded
     */
    private static User getUserByName(String userName) throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM users WHERE user_name = ?")) {
            s.setString(1, userName);

            return getUser(s);
        }
    }


    private static User getUser(PreparedStatement s) throws SQLException {
        ResultSet rs = s.executeQuery();
        if (rs.next()) {
            User user = new User();
            user.setUser_id(rs.getInt("user_id"));
            user.setUser_name(rs.getString("user_name"));
            user.setPassword(rs.getString("password"));
            user.setSuspended(rs.getBoolean("suspended"));

            return user;
        } else {
            return null;
        }
    }

    /**
     * Compare User password with given password, set SESSION
     *
     * @return True if succeeded otherwise False
     */
    public Boolean correctPassword() throws SQLException {

        User user = getUserByName(getUserName());
        // return false or throw error ?
        if (user == null) return false;

        SESSION.setSession(user);

        return getPassword().equals(user.getPassword());
    }
}
