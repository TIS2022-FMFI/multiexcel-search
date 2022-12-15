package backend.Managers;

import backend.DBS;
import backend.Entities.Category;
import backend.Entities.Part_name;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    public static BigInteger getCategoryId(String categoryName) throws SQLException {

        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT category_id FROM categories WHERE category_name = ?")) {
            s.setString(1, categoryName);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                return BigInteger.valueOf(rs.getInt("category_id"));
            } else {
                Category category = new Category();
                category.setCategory_name(categoryName);
                category.insert();
                return BigInteger.valueOf(category.getCategory_id());
            }
        }

    }

    /**
     * Returns all categories from database
     *
     * @return List of all Categories
     */
    public static List<Category> getAllCategories() {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM multiexcel.categories")) {
            try (ResultSet r = s.executeQuery()) {
                List<Category> categories = new ArrayList<>();
                while (r.next()) {
                    Category category = new Category();
                    category.setCategory_id(r.getInt("category_id"));
                    category.setCategory_name(r.getString("customer_name"));

                    categories.add(category);
                }
                return categories;
            } catch (SQLException e) {
                return null;
            }
        } catch (SQLException e) {
            return null;
        }
    }

    /**
     * Inserts category to database
     *
     * @param categoryId   - id of category
     * @param customerName - name of customer
     */
    public static boolean insertCatrgory(Integer categoryId, String customerName) {
        try {
            Category category = new Category();
            category.setCategory_id(categoryId);
            category.setCategory_name(customerName);
            category.insert();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    public static Category getCategory(BigInteger categoryId){
        if (categoryId == null)
            return null;

        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT category_name FROM categories where category_id = ?")){
            s.setObject(1, categoryId);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setCategory_id(categoryId.intValue());
                category.setCategory_name(rs.getString("category_name"));
                return category;
            }
            return null;
        } catch (SQLException ignored){
            return null;
        }
    }
}
