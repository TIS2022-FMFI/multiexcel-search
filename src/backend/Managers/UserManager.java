package backend.Managers;

import backend.DBS;
import backend.Entities.User;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserManager {

    public static boolean addUser(String user_name, String password, Boolean suspended) {
        try {
            User user = new User();
            user.setUser_name(user_name);
            user.setPassword(password);
            user.setSuspended(suspended);
            user.insert();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public static boolean suspendAccount(User accountToSuspend) {
        try {
            accountToSuspend.setSuspended(true);
            accountToSuspend.update();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    public static List<User> getUsers(boolean onlyActive) {
        try (
                PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM users");
                ResultSet r = s.executeQuery()
        ) {
            List<User> allUsers = new ArrayList<>();
            while (r.next()) {
                User c = new User();
                c.setUser_id(r.getInt("user_id"));
                c.setUser_name(r.getString("user_name"));
                c.setPassword(r.getString("password"));
                c.setSuspended(r.getBoolean("suspended"));
                if (onlyActive) {
                    if (!c.getSuspended()) {
                        allUsers.add(c);
                    }
                } else {
                    allUsers.add(c);
                }
            }
            return allUsers;
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean changeUserPassword(User user, String password) {
        try {
            user.setPassword(password);
            user.update();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }
}

