package backend.Entities;

import backend.Sessions.DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Drawing {

    private Integer drawing_id;
    private byte[] drawing;

    public Integer getDrawing_id() {
        return drawing_id;
    }

    public void setDrawing_id(Integer drawing_id) {
        this.drawing_id = drawing_id;
    }

    public byte[] getDrawing() {
        return drawing;
    }

    public void setDrawing(byte[] drawing) {
        this.drawing = drawing;
    }

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO drawings (drawing) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setBytes(1, drawing);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                drawing_id = r.getInt(1);
                if (drawing_id == 129)
                    System.out.println();
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE drawings SET drawing = ? WHERE drawing_id = ?")) {
            s.setBytes(1, drawing);
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