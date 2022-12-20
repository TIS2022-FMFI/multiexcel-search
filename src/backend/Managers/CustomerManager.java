package backend.Managers;

import backend.DBS;
import backend.Entities.Category;
import backend.Entities.Customer;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerManager {
    public static BigInteger getCustomerId(String customerName) throws SQLException {
        if (customerName == null)
            return null;
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT customer_id FROM customers WHERE customer_name = ?")) {
            s.setString(1, customerName);

            ResultSet rs = s.executeQuery();
            if (rs.next())
                return BigInteger.valueOf(rs.getInt("customer_id"));

            Customer customer = new Customer();
            customer.setCustomer_name(customerName);
            customer.insert();
            return BigInteger.valueOf(customer.getCustomer_id());
        } catch (SQLException ignored){
            return null;
        }
    }

    public static Customer getCustomer(BigInteger customerId) {
        if (customerId == null)
            return null;

        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT customer_name FROM customers where customer_id = ?")){
            s.setObject(1, customerId);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomer_id(customerId.intValue());
                customer.setCustomer_name(rs.getString("customer_name"));
                return customer;
            }
            return null;
        } catch (SQLException ignored){
            return null;
        }
    }
}

