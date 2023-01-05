package frontend.AdminControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
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
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Initializes reffernce of main controller
     * @param controller - refference to CatogoryMainContorller
     */
    public void init(CategoryMainController controller){
        mainController = controller;
    }

    /**
     * Checks if input from text field is not empty
     * Checks if category with name from text field is not already in database
     * Calls Category Manager to insert new category to database
     */
    @FXML
    private void addCategory(){
        String newCategoryName = categoryNameField.getText();
        ObservableList<Category> allCategories = CategoryManager.getAllCategories();

        if(Objects.equals(newCategoryName, "")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Failure");
            errorAlert.setContentText("Please enter a category name");
            errorAlert.showAndWait();
            return;
        }

        if(allCategories != null){
            for(Category category : allCategories){
                if(Objects.equals(category.getCategory_name(), newCategoryName)){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Failure");
                    errorAlert.setContentText("Category with this name already exists");
                    errorAlert.showAndWait();
                    return;
                }
            }
        }

        if(!CategoryManager.insertCategory(newCategoryName)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Failure");
            errorAlert.setContentText("Adding category has failed");
            errorAlert.showAndWait();
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
