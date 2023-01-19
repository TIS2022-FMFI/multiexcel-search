package backend.Managers;

import backend.Entities.Part_name;
import backend.Sessions.DBS;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.scene.control.Alert;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class PartNameManager {
    /**
     * Returns part name id associated with input part name from database
     * If part name doesn't exist in database, inserts new part name and returns associated id
     *
     * @param partName - input part name
     * @return associated part name id
     */
    public static BigInteger getPartNameId(String partName) {
        if (partName == null)
            return null;

        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT part_name_id FROM part_names WHERE part_name = ?")) {
            s.setString(1, partName);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return BigInteger.valueOf(rs.getInt("part_name_id"));
            } else {
                Part_name newPartName = new Part_name();
                newPartName.setPart_name(partName);
                newPartName.insert();
                return BigInteger.valueOf(newPartName.getPart_name_id());
            }
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }


    /**
     * Returns part name based on part name id from database
     *
     * @param partNameId - id of part name
     * @return Associated part name or null if part name doesn't exist
     */
    public static Part_name getPartName(BigInteger partNameId) {
        if (partNameId == null)
            return null;

        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT part_name FROM part_names where part_name_id = ?")) {
            s.setObject(1, partNameId);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Part_name partName = new Part_name();
                partName.setPart_name_id(partNameId.intValue());
                partName.setPart_name(rs.getString("part_name"));
                return partName;
            }
            return null;
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }


    /**
     * Returns all part names from database
     *
     * @return List of all Part names
     */
    public static List<Part_name> getAllPartNames() {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM multiexcel.part_names")) {
            try (ResultSet r = s.executeQuery()) {
                List<Part_name> partNames = new ArrayList<>();
                while (r.next()) {
                    Part_name partName = new Part_name();
                    partName.setPart_name_id(r.getInt("part_name_id"));
                    partName.setPart_name(r.getString("part_name"));

                    partNames.add(partName);
                }
                return partNames;
            } catch (SQLException e) {
                MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
                return null;
            }
        } catch (SQLException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }
}
