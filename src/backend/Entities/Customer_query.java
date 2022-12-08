package backend.Entities;

import backend.DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.math.BigInteger;

public class Customer_query {
    private Integer customer_query_id;
    private BigInteger customer_id;
    private BigInteger query_id;

    public Integer getCustomer_query_id() {return customer_query_id;}
    public void setCustomer_query_id(Integer customer_query_id) {this.customer_query_id = customer_query_id;}

    public BigInteger getCustomer_id() {return customer_id;}
    public void setCustomer_id(BigInteger customer_id) {this.customer_id = customer_id;}

    public BigInteger getQuery_id() {return query_id;}
    public void setQuery_id(BigInteger query_id) {this.query_id = query_id;}

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO customers_queries (customer_id, query_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, customer_id);
            s.setObject(2, query_id);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                customer_query_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE customers_queries SET customer_id = ?, query_id = ? WHERE customer_query_id = ?")) {
            s.setObject(1, customer_id);
            s.setObject(2, query_id);
            s.setInt(3, customer_query_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM customers_queries WHERE customer_query_id = ?")) {
            s.setInt(1, customer_query_id);

            s.executeUpdate();
        }
    }
}