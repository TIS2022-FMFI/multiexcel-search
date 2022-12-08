import DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Customer {

    private Integer customer_id;
    private String customer_name;

    public Integer getCustomer_id() {return customer_id;}
    public void setCustomer_id(Integer customer_id) {this.customer_id = customer_id;}

    public String getCustomer_name() {return customer_name;}
    public void setCustomer_name(String customer_name) {this.customer_name = customer_name;}

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO customers (customer_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, customer_name);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                customer_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE customers SET customer_name = ? WHERE customers_id = ?")) {
            s.setString(1, customer_name);
            s.setInt(2, customer_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM customers WHERE customer_id = ?")) {
            s.setInt(1, customer_id);

            s.executeUpdate();
        }
    }
}

