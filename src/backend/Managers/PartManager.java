package backend.Managers;

import backend.Entities.Part;
import backend.Models.PartBasic;
import backend.Sessions.DBS;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.scene.control.Alert;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static backend.Models.Constants.WITHOUT_CATEGORY_ID;

public class PartManager {
    public static Integer count;

    private static Double check(Double d) {
        return d == 0 ? null : d;
    }

    public static Short check(Short s) {
        return s == 0 ? null : s;
    }

    private static Integer check(Integer i) {
        return i == 0 ? null : i;
    }

    private static Part getPart(ResultSet rs) throws SQLException {
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
        part.setRating(rs.getInt("rating"));
        part.setInternal_rating(rs.getInt("internal_rating"));

        return part;
    }

    /**
     * Returns part associated with input part number or null if part number doesn't exist in database
     *
     * @param partNumber - input part number
     * @return part from database
     */
    public static Part getPartByPartNumber(String partNumber) {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM parts WHERE part_number = ?")) {
            s.setString(1, partNumber);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return getPart(rs);
            } else {
                return null;
            }
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }

    public static boolean AddCategoryToParts(List<Part> parts, BigInteger categoryId) {
        try {
            for (Part part : parts) {
                part.setCategory_id(categoryId);
                part.update();
            }
            return true;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return false;
        }
    }

    public static boolean IncreaseRating(List<Part> parts) {
        try {
            PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE parts SET rating = rating + 1 WHERE FIND_IN_SET( part_number, ? ) > 0");
            String arrString = parts.stream().map(Part::getPart_number).collect(Collectors.joining(","));
            s.setString(1, arrString);
            s.executeUpdate();
            return true;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return false;
        }
    }

    public static List<Part> GetPartsByQueryId(Integer queryId) {
        try {
            PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM parts p JOIN parts_queries pq ON p.part_number = pq.part_number WHERE pq.query_id = ?");
            s.setInt(1, queryId);
            ResultSet rs = s.executeQuery();

            List<Part> parts = new ArrayList<>();
            while (rs.next()) {
                parts.add(getPart(rs));
            }
            return parts;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }

    /**
     * Returns id of drawing associated with part number from database
     *
     * @param partNumber - part number
     * @return id of drawing or null if part doesn't have an associated drawing
     */
    public static BigInteger getDrawingIdFromPartNumber(String partNumber) {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT drawing_id FROM parts WHERE part_number = ?")) {
            s.setString(1, partNumber);

            ResultSet rs = s.executeQuery();
            if (rs.next())
                return BigInteger.valueOf(rs.getInt("drawing_id"));

            return null;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }

    public static ObservableList<PartBasic> getPartsBasicByCategoryId(Integer categoryId, Integer limit, Integer offset) {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT p.part_number, pn.part_name, p.rating FROM parts p " +
                "JOIN part_names pn ON p.part_name_id = pn.part_name_id " +
                "WHERE p.category_id = ? ORDER BY p.rating DESC, p.internal_rating " +
                "LIMIT ? OFFSET ?")) {
            s.setInt(1, categoryId);
            s.setInt(2, limit);
            s.setInt(3, offset);

            ObservableList<PartBasic> result = FXCollections.observableArrayList();
            ResultSet rs = s.executeQuery();
            while (rs.next()) {
                PartBasic partBasic = new PartBasic();
                partBasic.setPartNumber(rs.getString("part_number"));
                partBasic.setPartName(rs.getString("part_name"));
                partBasic.setRating(rs.getInt("rating"));

                result.add(partBasic);
            }
            return result;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }

    public static Integer getPartCountForCategoryId(Integer categoryId) {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT count(*) count FROM parts WHERE category_id = ?")) {
            s.setInt(1, categoryId);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
            return 0;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return 0;
        }
    }

    public static boolean swapRatings(String partNumber1, String partNumber2) {
        try {
            DBS.getConnection().setAutoCommit(false);
            Part part1 = getPartByPartNumber(partNumber1);
            Part part2 = getPartByPartNumber(partNumber2);
            if (part1 == null || part2 == null)
                return false;

            Integer part1Rating = part1.getRating();
            Integer part2Rating = part2.getRating();

            if (Objects.equals(part1Rating, part2Rating)) {
                Integer part1InternalRating = part1.getInternal_rating();
                Integer part2InternalRating = part2.getInternal_rating();
                setInternalRating(partNumber1, part2InternalRating);
                setInternalRating(partNumber2, part1InternalRating);
            } else {
                part1.setRating(part2Rating);
                part2.setRating(part1Rating);
                part1.update();
                part2.update();
            }

            DBS.getConnection().commit();
            DBS.getConnection().setAutoCommit(true);
            return true;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            try {
                DBS.getConnection().rollback();
                DBS.getConnection().setAutoCommit(true);
            } catch (SQLException ignored1) {
            }
            return false;
        }
    }

    public static Integer getCount() {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT count(*) count FROM parts")) {
            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return rs.getInt("count");
            }
        } catch (SQLException ex) {
            return null;
        }
        return null;
    }

    private static void setInternalRating(String partNumber, Integer internalRating) throws SQLException {
        PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE parts SET internal_rating = ? WHERE part_number = ?");
        s.setInt(1, internalRating);
        s.setString(2, partNumber);
        s.executeUpdate();
    }


    private static List<Part> getPartsByCategoryId(Integer categoryID) throws SQLException {
        PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM parts p WHERE p.category_id = ?");
        s.setInt(1, categoryID);
        ResultSet rs = s.executeQuery();

        List<Part> parts = new ArrayList<>();
        while (rs.next()) {
            parts.add(getPart(rs));
        }
        return parts;
    }

    public static void setPartsOfCategoryToWithoutCategory(Integer categoryID) {
        try {
            DBS.getConnection().setAutoCommit(false);
            List<Part> parts = getPartsByCategoryId(categoryID);
            for (Part part : parts) {
                part.setCategory_id(WITHOUT_CATEGORY_ID);
                part.update();
            }
            DBS.getConnection().commit();
            DBS.getConnection().setAutoCommit(true);
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            try {
                DBS.getConnection().rollback();
                DBS.getConnection().setAutoCommit(true);
            } catch (SQLException ignored1) {
            }
        }
    }
}
