package backend.Managers;

import backend.DBS;
import backend.Entities.Part;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartManager {
    public static Part getPartByName(String partNumber) throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM parts WHERE part_number = ?")) {
            s.setString(1, partNumber);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Part part = new Part();
                part.setPart_number(rs.getString("part_number"));
                part.setCustomer_id((BigInteger) rs.getObject("customer_id"));
                part.setPart_name_id((BigInteger) rs.getObject("part_name_id"));
                part.setCategory_id((BigInteger) rs.getObject("category_id"));
                part.setDrawing_id((BigInteger) rs.getObject("drawing_id"));
                part.setRubber(rs.getShort("rubber"));
                part.setDiameter_AT(rs.getDouble("diameter_AT"));
                part.setDiameter_AT_tol(rs.getString("diameter_AT_tol"));
                part.setLength_L_AT(rs.getDouble("length_L_AT"));
                part.setLength_L_AT_tol(rs.getString("length_L_AT_tol"));
                part.setDiameter_IT(rs.getDouble("diameter_IT"));
                part.setDiameter_IT_tol(rs.getString("diameter_IT_tol"));
                part.setLength_L_IT(rs.getDouble("length_L_IT"));
                part.setLength_L_IT_tol(rs.getString("length_L_IT_tol"));
                part.setDiameter_ZT(rs.getDouble("diameter_ZT"));
                part.setDiameter_ZT_tol(rs.getString("diameter_ZT_tol"));
                part.setLength_L_ZT(rs.getDouble("length_L_ZT"));
                part.setLength_L_ZT_tol(rs.getString("length_L_ZT_tol"));
                part.setCr_steg(rs.getInt("cr_steg"));
                part.setCr_niere(rs.getShort("cr_niere"));
                part.setCa(rs.getShort("ca"));
                part.setCt(rs.getDouble("ct"));
                part.setCk(rs.getDouble("ck"));
                part.setRating(rs.getInt("rating"));

                return part;
            } else {
                return null;
            }
        }
    }
}
