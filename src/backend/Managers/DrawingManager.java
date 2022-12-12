package backend.Managers;

import backend.DBS;
import backend.Entities.Drawing;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DrawingManager {

    public static BigInteger getDrawingId(byte[] drawing) throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT drawing_id FROM drawings WHERE drawing = ?")) {
            s.setBytes(1, drawing);

            ResultSet rs = s.executeQuery();
            if (rs.next())
                return BigInteger.valueOf(rs.getInt("drawing_id"));

            Drawing newDrawing = new Drawing();
            newDrawing.setDrawing(drawing);
            newDrawing.insert();
            return BigInteger.valueOf(newDrawing.getDrawing_id());
        }
    }

    public static BigInteger getDrawingIdFromPartName(String partName) throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT drawing_id FROM parts WHERE part_number = ?")) {
            s.setString(1, partName);

            ResultSet rs = s.executeQuery();
            if (rs.next())
                return BigInteger.valueOf(rs.getInt("drawing_id"));

            return null;
        }
    }
}
