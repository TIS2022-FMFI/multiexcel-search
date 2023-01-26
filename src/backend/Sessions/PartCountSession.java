package backend.Sessions;

import backend.Managers.PartManager;

import java.sql.SQLException;

public class PartCountSession {

    private static Integer partCount = null;

    public static void initPartCount() throws SQLException {
        partCount = PartManager.getCount();
        if (partCount == null)
            throw new SQLException("Parts count init failed");
    }

    public static Integer getPartCount() {
        return partCount;
    }

    public static void incrememntPartCount() {
        partCount++;
    }
}
