package backend.Entities;

import backend.Models.Filterable;
import backend.Sessions.DBS;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Objects;

public class Category implements Filterable {
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Category category = (Category) o;
        return Objects.equals(category_id, category.category_id) && Objects.equals(category_name, category.category_name);
    }

    private Integer category_id;
    private String category_name;

    public Integer getCategory_id() {
        return category_id;
    }

    public void setCategory_id(Integer category_id) {
        this.category_id = category_id;
    }

    public String getCategory_name() {
        return category_name;
    }

    public void setCategory_name(String category_name) {
        this.category_name = category_name;
    }

    public void insert() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("INSERT INTO categories (category_name) VALUES (?)", Statement.RETURN_GENERATED_KEYS)) {
            s.setString(1, category_name);
            s.executeUpdate();

            try (ResultSet r = s.getGeneratedKeys()) {
                r.next();
                category_id = r.getInt(1);
            }
        }
    }

    public void update() throws SQLException {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("UPDATE categories SET category_name = ? WHERE category_id = ?")) {
            s.setString(1, category_name);
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

    @Override
    public Integer getId() {
        return getCategory_id();
    }

    @Override
    public String getName() {
        return getCategory_name();
    }
}

