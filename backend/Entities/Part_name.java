import DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Part_name {

    private Integer part_name_id;
    private String part_name;

    public Integer getPart_name_id() {return part_name_id;}
    public void setPart_name_id(Integer part_name_id) {this.part_name_id = part_name_id;}

    public String getPart_name() {return part_name;}
    public void setPart_name(String part_name) {this.part_name = part_name;}

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO part_names (part_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, part_name);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                part_name_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE part_names SET part_name = ? WHERE part_name_id = ?")) {
            s.setString(1, part_name);
            s.setInt(2, part_name_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM part_names WHERE part_name_id = ?")) {
            s.setInt(1, part_name_id);

            s.executeUpdate();
        }
    }
}

