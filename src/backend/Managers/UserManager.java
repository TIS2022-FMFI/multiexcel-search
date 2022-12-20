package backend;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import backend.Entities.User;

public class UserManager {

    /**
     * Adds new user to database
     *
     * @param userName username
     * @param password password (encrypted)
     * @param suspended true if you want to create suspended account
     *
     * @return True if succeeded otherwise False
     */
    public static boolean addUser(String userName, String password, Boolean suspended) {
        try {
            User user = new User();
            user.setUser_name(userName);
            user.setPassword(password);
            user.setSuspended(suspended);
            user.insert();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Suspends user account
     *
     * @param accountToSuspend user
     *
     * @return True if succeeded otherwise False
     */
    public static boolean suspendAccount(User accountToSuspend){
        try{
            accountToSuspend.setSuspended(true);
            accountToSuspend.update();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }

    /**
     * Enable suspended user account
     *
     * @param accountToEnable user
     *
     * @return True if succeeded otherwise False
     */
    public static boolean enableAccount(User accountToEnable){
        try{
            accountToEnable.setSuspended(false);
            accountToEnable.update();
        } catch (SQLException e) {
            return false;
        }
        return true;
    }


    /**
     * Get list of all or only active users
     *
     * @param onlyActive set to True if you want only active accounts. False if all
     *
     * @return List if succeeded otherwise returns NULL
     */
    public static List<User> getUsers(boolean onlyActive){
        try(
                PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM multiexcel.users");
                ResultSet r = s.executeQuery()
        ){
            List<User> allUsers = new ArrayList<>();
            while (r.next()){
                User c = new User();
                c.setUser_id(r.getInt("user_id"));
                c.setUser_name(r.getString("user_name"));
                c.setPassword(r.getString("password"));
                c.setSuspended(r.getBoolean("suspended"));
                if(onlyActive){
                    if(!c.getSuspended()){
                        allUsers.add(c);
                    }
                }else{
                    allUsers.add(c);
                }
            }
            return allUsers;
        }catch (SQLException e){
            return null;
        }
    }

    /**
     * Get list of all or only active users
     *
     * @param user user
     * @param password new password (encrypted)
     *
     * @return True if succeeded otherwise False
     */
    public static boolean changeUserPassword(User user, String password){
        try{
            user.setPassword(password);
            user.update();
        }catch (SQLException e){
            return  false;
        }
        return true;
    }
}

