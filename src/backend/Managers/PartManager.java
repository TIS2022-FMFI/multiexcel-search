package backend.Managers;

import backend.DBS;
import backend.Entities.Part;

import java.math.BigInteger;
import java.sql.*;
import java.util.*;
import java.util.stream.Collectors;

public class PartManager {
    private static Double check(Double d){
        return d == 0 ? null : d;
    }
    private static Short check(Short s){
        return s == 0 ? null : s;
    }
    private static Integer check(Integer i) {
        return i == 0 ? null : i;
    }

    public static Part getPartByNumber(String partNumber){
        try {
            PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM parts WHERE part_number = ?");
            s.setString(1, partNumber);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Part part = new Part();
                part.setPart_number(rs.getString("part_number"));
                part.setCustomer_id((BigInteger) rs.getObject("customer_id"));
                part.setPart_name_id((BigInteger) rs.getObject("part_name_id"));
                part.setCategory_id((BigInteger) rs.getObject("category_id"));
                part.setDrawing_id((BigInteger) rs.getObject("drawing_id"));
                part.setRubber(check(rs.getShort("rubber")));
                part.setDiameter_AT(check(rs.getDouble("diameter_AT")));
                part.setDiameter_AT_tol(rs.getString("diameter_AT_tol"));
                part.setLength_L_AT(check(rs.getDouble("length_L_AT")));
                part.setLength_L_AT_tol(rs.getString("length_L_AT_tol"));
                part.setDiameter_IT(check(rs.getDouble("diameter_IT")));
                part.setDiameter_IT_tol(rs.getString("diameter_IT_tol"));
                part.setLength_L_IT(check(rs.getDouble("length_L_IT")));
                part.setLength_L_IT_tol(rs.getString("length_L_IT_tol"));
                part.setDiameter_ZT(check(rs.getDouble("diameter_ZT")));
                part.setDiameter_ZT_tol(rs.getString("diameter_ZT_tol"));
                part.setLength_L_ZT(check(rs.getDouble("length_L_ZT")));
                part.setLength_L_ZT_tol(rs.getString("length_L_ZT_tol"));
                part.setCr_steg(check(rs.getInt("cr_steg")));
                part.setCr_niere(check(rs.getShort("cr_niere")));
                part.setCa(check(rs.getShort("ca")));
                part.setCt(check(rs.getDouble("ct")));
                part.setCk(check(rs.getDouble("ck")));
                part.setRating(check(rs.getInt("rating")));

                return part;
            } else {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    public static boolean AddCategoryToParts(List<Part> parts, BigInteger categoryId){
        try{
            for(Part part : parts) {
                part.setCategory_id(categoryId);
                part.update();
            }
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public static boolean IncreaseRating(List<Part> parts){
        try{
            PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE parts SET rating = rating + 1 WHERE FIND_IN_SET( part_number, ? ) > 0");
            String arrString = parts.stream().map(Part::getPart_number).collect(Collectors.joining(","));
            s.setString(1, arrString);
            s.executeUpdate();
            return true;
        }
        catch (SQLException e) {
            return false;
        }
    }

    public static List<Part> GetPartsByQueryId(Integer queryId){
        try{
            PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM parts p JOIN parts_queries pq ON p.part_number = pq.part_number WHERE pq.query_id = ?");
            s.setInt(1, queryId);
            ResultSet r = s.executeQuery();

            List<Part> parts = new ArrayList<>();
            while (r.next()) {
                Part part = new Part();
                part.setPart_number(r.getString("part_number"));
                part.setCustomer_id((BigInteger) r.getObject("customer_id"));
                part.setPart_name_id((BigInteger) r.getObject("part_name_id"));
                part.setCategory_id((BigInteger) r.getObject("category_id"));
                part.setDrawing_id((BigInteger) r.getObject("drawing_id"));
                part.setRubber(check(r.getShort("rubber")));
                part.setDiameter_AT(check(r.getDouble("diameter_AT")));
                part.setDiameter_AT_tol(r.getString("diameter_AT_tol"));
                part.setLength_L_AT(check(r.getDouble("length_L_AT")));
                part.setLength_L_AT_tol(r.getString("length_L_AT_tol"));
                part.setDiameter_IT(check(r.getDouble("diameter_IT")));
                part.setDiameter_IT_tol(r.getString("diameter_IT_tol"));
                part.setLength_L_IT(check(r.getDouble("length_L_IT")));
                part.setLength_L_IT_tol(r.getString("length_L_IT_tol"));
                part.setDiameter_ZT(check(r.getDouble("diameter_ZT")));
                part.setDiameter_ZT_tol(r.getString("diameter_ZT_tol"));
                part.setLength_L_ZT(check(r.getDouble("length_L_ZT")));
                part.setLength_L_ZT_tol(r.getString("length_L_ZT_tol"));
                part.setCr_steg(check(r.getInt("cr_steg")));
                part.setCr_niere(check(r.getShort("cr_niere")));
                part.setCa(check(r.getShort("ca")));
                part.setCt(check(r.getDouble("ct")));
                part.setCk(check(r.getDouble("ck")));
                part.setRating(check(r.getInt("rating")));

                parts.add(part);
            }
            return parts;
        }
        catch (SQLException e) {
            return null;
        }
    }
}
