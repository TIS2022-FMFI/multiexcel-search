package backend.Managers;

import backend.DBS;
import backend.Entities.Part_name;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class PartNameManager {
    public static BigInteger getPartNameId(String partName) throws SQLException {
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
        }

    }

}
