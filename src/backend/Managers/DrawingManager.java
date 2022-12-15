package backend.Managers;

import backend.DBS;
import backend.Entities.Drawing;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DrawingManager {

    public static BigInteger getDrawingId(byte[] drawing) {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT drawing_id FROM drawings WHERE drawing = ?")) {
            s.setBytes(1, drawing);

            ResultSet rs = s.executeQuery();
            if (rs.next())
                return BigInteger.valueOf(rs.getInt("drawing_id"));

            Drawing newDrawing = new Drawing();
            newDrawing.setDrawing(drawing);
            newDrawing.insert();
            return BigInteger.valueOf(newDrawing.getDrawing_id());
        } catch (SQLException ignored){
            return null;
        }
    }

    public static BigInteger getDrawingIdFromPartName(String partName) {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT drawing_id FROM parts WHERE part_number = ?")) {
            s.setString(1, partName);

            ResultSet rs = s.executeQuery();
            if (rs.next())
                return BigInteger.valueOf(rs.getInt("drawing_id"));

            return null;
        } catch (SQLException ignored) {
            return null;
        }
    }

    public static Drawing getDrawing(BigInteger drawingId) {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT drawing FROM drawings WHERE drawing_id = ?")) {
            s.setObject(1, drawingId);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Drawing drawing = new Drawing();
                drawing.setDrawing_id(drawingId.intValue());
                drawing.setDrawing(rs.getBytes("drawing"));
                return drawing;
            }
            return null;
        } catch (SQLException ignored) {
            return null;
        }
    }
}
