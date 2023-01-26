package frontend.Controllers.CategoryManagementControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CategoryEditController implements Initializable {

    public Text editTextArea;
    public Button editButton;
    public Button closeButton;
    public TextField categoryNewNameField;
    public Label errorLabel;

    private CategoryMainController mainController;
    private Integer categoryIdToEdit;

    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Initializes reffernce of main controller and category id to delete
     *
     * @param controller - refference to CatogoryMainContorller
     * @param cateogryId - Id of category to delete
     */
    public void init(CategoryMainController controller, Integer cateogryId) {
        mainController = controller;
        categoryIdToEdit = cateogryId;
    }

    /**
     * Renames cateogry
     */
    public void renameCategory() {
        String newCategoryName = categoryNewNameField.getText();
        ObservableList<Category> allCategories = CategoryManager.getAllCategories();

        if (Objects.equals(newCategoryName, "")) {
            errorLabel.setText("Please enter a category name");
            return;
        }

        if (allCategories != null) {
            for (Category category : allCategories) {
                if (Objects.equals(category.getCategory_name(), newCategoryName)) {
                    errorLabel.setText("Category with this name already exists");
                    return;
                }
            }
        }

        if (!CategoryManager.renameCategory(newCategoryName, BigInteger.valueOf(categoryIdToEdit))) {
            errorLabel.setText("Renaming category has failed");
            return;
        }
        Stage stage = (Stage) editButton.getScene().getWindow();
        stage.close();
        mainController.updateCategoryList();
    }

    /**
     * Closes window for editing a category
     */
    public void closeEditWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
