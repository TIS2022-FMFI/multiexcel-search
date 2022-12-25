package backend.Managers;

import backend.DBS;
import backend.Entities.Drawing;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DrawingManager {
    /**
     * Returns id of drawing associated with input drawing if drawing exists in database.
     * If it doesn't, insert's input drawing into database and returns associated id
     *
     * @param drawing - input drawing
     * @return id of drawing
     */
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

    /**
     * Returns drawing based on drawing id from database
     *
     * @param drawingId - id of drawing
     * @return Associated drawing or null if drawing doesn't exist
     */
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
