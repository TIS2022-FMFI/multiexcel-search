package backend.Entities;

import backend.DBS;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Part_query {

    private Integer part_query_id;
    private BigInteger part_number;
    private BigInteger query_id;

    public Integer getPart_query_id() {
        return part_query_id;
    }

    public void setPart_query_id(Integer part_query_id) {
        this.part_query_id = part_query_id;
    }

    public BigInteger getPart_number() {
        return part_number;
    }

    public void setPart_number(BigInteger part_number) {
        this.part_number = part_number;
    }

    public BigInteger getQuery_id() {
        return query_id;
    }

    public void setQuery_id(BigInteger query_id) {
        this.query_id = query_id;
    }

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO parts_queries (part_number, query_id) VALUES (?, ?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setObject(1, part_number);
            s.setObject(2, query_id);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                part_query_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE parts_queries SET part_number = ?, query_id = ? WHERE part_query_id = ?")) {
            s.setObject(1, part_number);
            s.setObject(2, query_id);
            s.setInt(3, part_query_id);

            s.executeUpdate();
        }
    }

    public void delete() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("DELETE FROM parts_queries WHERE part_query_id = ?")) {
            s.setInt(1, part_query_id);

            s.executeUpdate();
        }
    }
}