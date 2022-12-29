package frontend.AdminControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class CategoryMainController implements Initializable {

    @FXML
    public ListView<Category> categoryList;

    /**
     * Inner class used as Cell in ListView consists of category name and button to delete category
     */
    class Cell extends ListCell<Category> {
        HBox hbox = new HBox();
        Button button = new Button();
        Label label = new Label();
        Pane pane = new Pane();

        /**
         * constructor
         */
        public Cell(){
            super();

            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
        }

        /**
         * This method is override and called on update of cell.
         * @param category - list item.
         * @param empty - empty.
         * @exception RuntimeException On fxml loading error.
         */
        @Override
        public void updateItem(Category category, boolean empty){
            super.updateItem(category, empty);
            setText(null);
            setGraphic(null);

            if(category != null && !empty){
                label.setText(category.getCategory_name());
                setGraphic(hbox);
                button.setText("Delete");
                button.setOnAction(x -> {
                    try {
                        FXMLLoader loader = new FXMLLoader();
                        String fxmlDocPath = "./src/frontend/AdminFXML/CategoryDelete.fxml";
                        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
                        AnchorPane root = loader.load(fxmlStream);

                        CategoryDeleteController controller = loader.getController();
                        controller.init(CategoryMainController.this, category.getCategory_id());

                        Scene scene = new Scene(root);
                        Stage stage = new Stage();
                        stage.setResizable(false);
                        stage.setScene(scene);
                        stage.show();
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                });
            }
        }
    }


    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        updateList();
    }

    /**
     * Updates categories ListView from database
     */
    public void updateList(){
        ObservableList<Category> categories = CategoryManager.getAllCategories();
        if(categories == null)
            return;

        categoryList.setItems(categories);
        categoryList.setCellFactory(x -> new Cell());
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
}
