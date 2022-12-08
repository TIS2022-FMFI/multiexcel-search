import DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Blob;

public class Drawings {

    private Integer drawing_id;
    private Blob drawing;

    public Integer getDrawing_id() {return drawing_id;}
    public void setDrawing_id(Integer drawing_id) {this.drawing_id = drawing_id;}

    public Blob getDrawing() {return drawing;}
    public void setDrawing(Blob drawing) {this.drawing = drawing;}

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO drawings (drawing) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setBlob(1, drawing);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                drawing_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE drawings SET drawing = ? WHERE drawing_id = ?")) {
            s.setBlob(1, drawing);
            s.setInt(2, drawing_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM drawings WHERE drawing_id = ?")) {
            s.setInt(1, drawing_id);

            s.executeUpdate();
        }
    }
}