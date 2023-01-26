package frontend.Controllers.CategoryManagementControllers;

import backend.Managers.CategoryManager;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class CategoryDeleteController implements Initializable {

    public Text deleteTextArea;
    public Button deleteButton;
    public Button closeButton;

    private CategoryMainController mainController;
    private Integer categoryIdToDelete;

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
        categoryIdToDelete = cateogryId;
    }

    /**
     * Calls Category Manager to delete category from database
     */
    @FXML
    public void deleteCategory() {
        if (!CategoryManager.deleteCategory(categoryIdToDelete)) {
            MainController.showAlert(Alert.AlertType.ERROR, "Failure", "Deleting category has failed");
            return;
        }
        Stage stage = (Stage) deleteButton.getScene().getWindow();
        stage.close();
        mainController.updateCategoryList();
        mainController.clearPartsList();
    }

    /**
     * Closes window for deleting a category
     */
    @FXML
    public void closeDeleteWindow() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
