package backend.Entities;

import backend.DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class User {

    private Integer user_id;
    private String user_name;
    private String password;
    private Boolean suspended;

    public Integer getUser_id() {return user_id;}
    public void setUser_id(Integer user_id) {this.user_id = user_id;}

    public String getUser_name() {return user_name;}
    public void setUser_name(String user_name) {this.user_name = user_name;}

    public String getPassword() {return password;}
    public void setPassword(String password) {this.password = password;}

    public Boolean getSuspended() {return suspended;}
    public void setSuspended(Boolean suspended) {this.suspended = suspended;}

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO users (user_name, password, suspended) VALUES (?,?,?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, user_name);
            s.setString(2, password);
            s.setBoolean(3, suspended);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                user_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE users SET user_name = ?, password = ?, suspended = ? WHERE user_id = ?")) {
            s.setString(1, user_name);
            s.setString(2, password);
            s.setBoolean(3, suspended);
            s.setInt(4, user_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM users WHERE user_id = ?")) {
            s.setInt(1, user_id);

            s.executeUpdate();
        }
    }
}