package backend.Managers;

import backend.Sessions.DBS;
import backend.Entities.Part_name;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

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
        } catch (SQLException ignored) {
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
        } catch (SQLException ignored) {
            return null;
        }
    }
}
