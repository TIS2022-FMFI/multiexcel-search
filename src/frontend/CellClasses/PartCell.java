package frontend.CellClasses;

import backend.Models.PartBasic;
import frontend.AdminControllers.CategoryMainController;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

/**
 * Class used as Cell in ListView consists of part name and buttons to increase or decrease rating
 */
public class PartCell extends ListCell<PartBasic> {
    HBox hbox = new HBox();
    Button buttonUp = new Button();
    Button buttonDown = new Button();
    Label label = new Label();
    Pane pane = new Pane();
    CategoryMainController categoryMainController;

    /**
     * constructor
     */
    public PartCell(CategoryMainController categoryMainController){
        super();

        this.categoryMainController = categoryMainController;
        hbox.getChildren().addAll(label, pane, buttonUp, buttonDown);
        HBox.setHgrow(pane, Priority.ALWAYS);
    }

    /**
     * This method is override and called on update of cell.
     * @param part - list item.
     * @param empty - empty.
     */
    @Override
    public void updateItem(PartBasic part, boolean empty){
        super.updateItem(part, empty);
        setText(null);
        setGraphic(null);

        if(part != null && !empty){
            label.setText(part.getPartName() + ' ' + part.getPartNumber());
            setGraphic(hbox);
            buttonUp.setText("up");
            buttonUp.setOnAction(x -> {

            });
            buttonDown.setText("down");
            buttonDown.setOnAction(x -> {

            });
        }
    }
}
