package backend;

import backend.Entities.Category;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class CategoryManager {
    /**
     * Returns all categories from database
     *
     * @return List of all Categories
     */
    public static List<Category> getAllCategories(){
        try (PreparedStatement s = DBS.getConnection().prepareStatement("SELECT * FROM multiexcel.categories")) {
            try (ResultSet r = s.executeQuery()) {
                List<Category> categories = new ArrayList<>();
                while (r.next()) {
                    Category category = new Category();
                    category.setCategory_id(r.getInt("category_id"));
                    category.setCustomer_name(r.getString("customer_name"));

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
     * @param categoryId - id of category
     * @param customerName - name of customer
     */
    public static boolean insertCatrgory(Integer categoryId, String customerName){
        try{
            Category category = new Category();
            category.setCategory_id(categoryId);
            category.setCustomer_name(customerName);
            category.insert();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }

    /**
     * Delete category from database
     *
     * @param categoryId - id of category
     */
    public static boolean deleteCatrgory(Integer categoryId){
        try{
            Category category = new Category();
            category.setCategory_id(categoryId);
            category.delete();
            return true;
        } catch (SQLException e) {
            return false;
        }
    }
}
