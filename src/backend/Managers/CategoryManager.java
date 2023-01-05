package backend.Managers;

import backend.Entities.Category_query;
import backend.Sessions.DBS;
import backend.Entities.Category;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import java.math.BigInteger;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class CategoryManager {
    /**
     * Returns id of category associated with input name if it exists in database
     * If it doesn't, insert's input category name into database and returns associated id
     *
     * @param categoryName - name of category
     * @return id of category
     */
    public static BigInteger getCategoryId(String categoryName) {

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
        } catch (SQLException ignored) {
            return null;
        }

    }

    /**
     * Returns all categories from database
     *
     * @return List of all Categories
     */
    public static ObservableList<Category> getAllCategories() {
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM multiexcel.categories ORDER BY category_name")) {
            try (ResultSet r = s.executeQuery()) {
                ObservableList<Category> categories = FXCollections.observableArrayList();
                while (r.next()) {
                    Category category = new Category();
                    category.setCategory_id(r.getInt("category_id"));
                    category.setCategory_name(r.getString("category_name"));

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
     * Returns all categories with query id from database
     *
     * @return List of all Categories by query id
     */
    public static List<Category_query> getAllCategoriesByQueryId(int queryId) {
        try (
                PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM multiexcel.categories_queries WHERE query_id=?")
        ) {
            s.setInt(1, queryId);
            try (ResultSet r = s.executeQuery()){
                List<Category_query> category_queries = new ArrayList<>();
                while (r.next()) {
                    Category_query category_query = new Category_query();
                    category_query.setCategory_id(BigInteger.valueOf(r.getInt("category_id")));
                    category_query.setQuery_id(BigInteger.valueOf(r.getInt("query_id")));

                    category_queries.add(category_query);
                }
                return category_queries;
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
     * @param categoryName - name of category
     */
    public static boolean insertCategory(String categoryName) {
        try {
            Category category = new Category();
            category.setCategory_name(categoryName);
            category.insert();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Returns category based on category id from database
     *
     * @param categoryId - id of category
     * @return Associated category or null if category doesn't exist
     */
    public static Category getCategory(BigInteger categoryId) {
        if (categoryId == null)
            return null;

        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT category_name FROM categories where category_id = ?")) {
            s.setObject(1, categoryId);

            ResultSet rs = s.executeQuery();
            if (rs.next()) {
                Category category = new Category();
                category.setCategory_id(categoryId.intValue());
                category.setCategory_name(rs.getString("category_name"));
                return category;
            }
            return null;
        } catch (SQLException ignored) {
            return null;
        }
    }

    /**
     * Delete category from database
     *
     * @param categoryId - id of category
     */
    public static boolean deleteCategory(Integer categoryId) {
        try {
            Category category = new Category();
            category.setCategory_id(categoryId);
            category.delete();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
