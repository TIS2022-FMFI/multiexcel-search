package frontend.AdminControllers;

import backend.Entities.Category;
import backend.Entities.Part;
import backend.Managers.CategoryManager;
import backend.Managers.PartManager;
import backend.Models.PartBasic;
import frontend.CellClasses.CategoryCell;
import frontend.CellClasses.PartCell;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryMainController implements Initializable {

    @FXML
    public ListView<Category> categoryList;
    public ListView<PartBasic> partList;
    public Button prevPageButton;
    public Button nextPageButton;

    private int currentPage = 0;
    private int itemsPerPage = 10;

    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryList.setOnMouseClicked(x -> {
            ObservableList<PartBasic> parts = PartManager.getPartsBasicByCatogoryId(
                    categoryList.getSelectionModel().getSelectedItem().getCategory_id(), itemsPerPage, currentPage + 1);
            partList.setItems(parts);
            partList.setCellFactory(y -> new PartCell(this));
        });
        updateCategoryList();
    }

    /**
     * Updates categories ListView from database
     */
    public void updateCategoryList(){
        ObservableList<Category> categories = CategoryManager.getAllCategories();
        if(categories == null)
            return;

        categoryList.setItems(categories);
        categoryList.setCellFactory(x -> new CategoryCell(this));
    }

    /**
     * Opens new window for adding new category
     */
    @FXML
    public void addCategory() {
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/AdminFXML/CategoryAdd.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            CategoryAddController controller = loader.getController();
            controller.init(CategoryMainController.this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void prevPage(ActionEvent actionEvent) {
    }

    @FXML
    public void nextPage(ActionEvent actionEvent) {
    }
}
