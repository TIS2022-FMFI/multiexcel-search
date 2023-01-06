package frontend.CellClasses;

import backend.Entities.Category;
import frontend.Controllers.CategoryManagementControllers.CategoryDeleteController;
import frontend.Controllers.CategoryManagementControllers.CategoryMainController;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * Class used as Cell in ListView consists of category name and button to delete category
 */
public class CategoryCell extends ListCell<Category> {
    HBox hbox = new HBox();
    Button button = new Button();
    Label label = new Label();
    Pane pane = new Pane();
    CategoryMainController categoryMainController;

    /**
     * constructor
     */
    public CategoryCell(CategoryMainController categoryMainController) {
        super();

        this.categoryMainController = categoryMainController;
        hbox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
    }

    /**
     * This method is override and called on update of cell.
     *
     * @param category - list item.
     * @param empty    - empty.
     * @throws RuntimeException On fxml loading error.
     */
    @Override
    public void updateItem(Category category, boolean empty) {
        super.updateItem(category, empty);
        setText(null);
        setGraphic(null);

        if (category != null && !empty) {
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
                    controller.init(categoryMainController, category.getCategory_id());

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
