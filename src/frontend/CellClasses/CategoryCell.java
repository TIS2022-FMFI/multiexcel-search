package frontend.CellClasses;

import backend.Entities.Category;
import backend.Models.Constants;
import frontend.Controllers.AbstractControllers.MainController;
import frontend.Controllers.CategoryManagementControllers.CategoryDeleteController;
import frontend.Controllers.CategoryManagementControllers.CategoryEditController;
import frontend.Controllers.CategoryManagementControllers.CategoryMainController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.math.BigInteger;
import java.util.Objects;

import static backend.Models.Constants.WITHOUT_CATEGORY_ID;

/**
 * Class used as Cell in ListView consists of category name and button to delete category
 */
public class CategoryCell extends ListCell<Category> {
    HBox hbox = new HBox();
    Button deleteButton = new Button();
    Button editButton = new Button();
    Label label = new Label();
    Pane pane = new Pane();
    CategoryMainController categoryMainController;

    /**
     * constructor
     *
     * @param categoryMainController - refference to categoryMainController
     */
    public CategoryCell(CategoryMainController categoryMainController) {
        super();

        this.categoryMainController = categoryMainController;
        hbox.getChildren().addAll(label, pane, editButton, deleteButton);
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

            ImageView editImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/editImage.png")).toExternalForm());
            editButton.setGraphic(editImageView);
            editButton.setOnAction(x -> {
                String fxmlDocPath = "/frontend/FXML/CategoryManagementFXML/CategoryEdit.fxml";

                CategoryEditController controller = MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_CATEGORY_MANAGMENT_EDIT);

                if (controller != null)
                    controller.init(categoryMainController, category.getCategory_id());
            });

            ImageView deleteImageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/deleteImage.png")).toExternalForm());
            deleteButton.setGraphic(deleteImageView);
            deleteButton.setOnAction(x -> {
                String fxmlDocPath = "/frontend/FXML/CategoryManagementFXML/CategoryDelete.fxml";

                CategoryDeleteController controller = MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_CATEGORY_MANAGMENT_DELETE);

                if (controller != null)
                    controller.init(categoryMainController, category.getCategory_id());
            });
            if (BigInteger.valueOf(category.getCategory_id()).equals(WITHOUT_CATEGORY_ID)) {
                deleteButton.setVisible(false);
                editButton.setVisible(false);
            }
        }
    }
}
