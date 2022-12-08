import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.math.BigInteger;

public class Category_query {
    private Integer category_query_id;
    private BigInteger category_id;
    private BigInteger query_id;


    public Integer getCategory_query_id() {return category_query_id;}
    public void setCategory_query_id(Integer category_query_id) {
        this.category_query_id = category_query_id;
    }

    public BigInteger getCategory_id() {
        return category_id;
    }
    public void setCategory_id(BigInteger category_id) {
        this.category_id = category_id;
    }

    public BigInteger getQuery_id() {
        return query_id;
    }
    public void setQuery_id(BigInteger query_id) {
        this.query_id = query_id;
    }

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO categories_queries (category_id, query_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setBigInteger(1, category_id);
            s.setBigInteger(2, query_id);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                category_query_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE categories_queries SET category_id = ?, query_id = ? WHERE category_query_id = ?")) {
            s.setBigInteger(1, category_id);
            s.setBigInteger(2, query_id);
            s.setInt(3, category_query_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM categories_queries WHERE category_query_id = ?")) {
            s.setInt(1, category_query_id);

            s.executeUpdate();
        }
    }
}