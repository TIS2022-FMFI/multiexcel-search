import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306", "root", "root");
            System.out.println("Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}