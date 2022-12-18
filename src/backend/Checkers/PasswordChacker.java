package backend.Checkers;

import backend.DBS;
import backend.Entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PasswordChacker {


    private String user_name;
    private String password;

    public void setUser_name(String user_name) {this.user_name = user_name;}
    public String getUser_name() {return user_name;}

    public void setPassword(String password) {this.password = password;}
    private String getPassword() {return password;}

    /*
    Return User instance filtered by user_name
     */
    private static User getUserByName(String user_name) throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM users WHERE user_name = ?")) {
            s.setString(1, user_name);

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
    }

    /*
    Return true when passwords are equal
     */
    public Boolean correctPassword() throws SQLException {

        User user = getUserByName(getUser_name());
        // return false or throw error ?
        if (user == null) return false;
        return getPassword().equals(user.getPassword());
    }
}
