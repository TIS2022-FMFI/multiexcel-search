package frontend.Controllers.CategoryManagementControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CategoryAddController implements Initializable {

    public TextField categoryNameField;
    public Button addButton;
    public Button closeButton;

    private CategoryMainController mainController;

    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Initializes reffernce of main controller
     *
     * @param controller - refference to CatogoryMainContorller
     */
    public void init(CategoryMainController controller) {
        mainController = controller;
    }

    /**
     * Checks if input from text field is not empty
     * Checks if category with name from text field is not already in database
     * Calls Category Manager to insert new category to database
     */
    @FXML
    private void addCategory() {
        String newCategoryName = categoryNameField.getText();
        ObservableList<Category> allCategories = CategoryManager.getAllCategories();

        if (Objects.equals(newCategoryName, "")) {
            MainController.showAlert(Alert.AlertType.ERROR, "Failure", "Please enter a category name");
            return;
        }

        if (allCategories != null) {
            for (Category category : allCategories) {
                if (Objects.equals(category.getCategory_name(), newCategoryName)) {
                    MainController.showAlert(Alert.AlertType.ERROR, "Failure", "Category with this name already exists");
                    return;
                }
            }
        }

        if (!CategoryManager.insertCategory(newCategoryName)) {
            MainController.showAlert(Alert.AlertType.ERROR, "Failure", "Adding category has failed");
            return;
        }
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        mainController.updateCategoryList();
    }

    /**
     * Closes window for adding a category
     */
    @FXML
    public void Close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
