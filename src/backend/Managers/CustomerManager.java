package backend.Managers;

import backend.DBS;
import backend.Entities.Customer;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CustomerManager {
    /**
     * Returns id of customer associated with input name if it exists in database
     * If it doesn't, insert's input customer name into database and returns associated id
     *
     * @param customerName - name of customer
     * @return id of customer
     */
    public static BigInteger getCustomerId(String customerName) {
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
        } catch (SQLException ignored) {
            return null;
        }
    }

    /**
     * Returns customer based on customer id from database
     *
     * @param customerId - id of customer
     * @return Associated customer or null if cystomer doesn't exist
     */
    public static Customer getCustomer(BigInteger customerId) {
        if (customerId == null)
            return null;

        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT customer_name FROM customers where customer_id = ?")) {
            s.setObject(1, customerId);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Customer customer = new Customer();
                customer.setCustomer_id(customerId.intValue());
                customer.setCustomer_name(rs.getString("customer_name"));
                return customer;
            }
            return null;
        } catch (SQLException ignored) {
            return null;
        }
    }
}

