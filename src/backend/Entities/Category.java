package backend.Entities;

import DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Category {

    private Integer category_id;
    private String customer_name;

    public Integer getCategory_id() {return category_id;}
    public void setCategory_id(Integer category_id) {this.category_id = category_id;}

    public String getCustomer_name() {return customer_name;}
    public void setCustomer_name(String customer_name) {this.customer_name = customer_name;}

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO categories (customer_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, customer_name);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                category_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE categories SET customer_name = ? WHERE category_id = ?")) {
            s.setString(1, customer_name);
            s.setInt(2, category_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM categories WHERE category_id = ?")) {
            s.setInt(1, category_id);

            s.executeUpdate();
        }
    }
}

