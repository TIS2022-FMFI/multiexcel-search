import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        try {
            java.util.Properties prop = new Properties();
            prop.loadFromXML(new FileInputStream("configuration/configuration.xml"));
            Connection connection = DriverManager.getConnection(
                    prop.getProperty("database"),
                    prop.getProperty("user"),
                    prop.getProperty("password"));

        } catch (SQLException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}