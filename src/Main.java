import backend.DBS;
import backend.Entities.Category;
import backend.Entities.Category_query;
import backend.Entities.User;

import java.math.BigInteger;
import java.sql.Connection;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello world!");
        try {
            Connection connection = DriverManager.getConnection("jdbc:mariadb://localhost:3306/multiexcel", "root", "root");
            DBS.setConnection(connection);
//            Category_query c = new Category_query();
//            c.setCategory_id(new BigInteger("1"));
//            c.setQuery_id(new BigInteger("1"));
//            c.insert();
            System.out.println("Success");
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }

    }
}