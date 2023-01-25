package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Entities.Category;
import backend.Entities.Part;
import backend.Managers.CategoryManager;
import frontend.Controllers.AbstractControllers.FilterMasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import static backend.Models.Constants.WITHOUT_CATEGORY_ID;

public class SecondSearchEditCategory implements Initializable {

    @FXML
    public VBox scrollParameterFilter;
    List<CheckBox> checkBoxes;
    FilterMasterController filterMasterController;
    Part part;


    public void setPart(Part part) {

        this.part = part;
        checkBoxes = new ArrayList<>();
        List<Category> parameters = CategoryManager.getAllCategories();

        if (parameters != null) {

            for (Category c : parameters) {

                CheckBox partNameCheckBox = new CheckBox();
                partNameCheckBox.setText(c.getName());
                if (BigInteger.valueOf(c.getCategory_id()).equals(part.getCategory_id())) {
                    partNameCheckBox.setSelected(true);
                }
                checkBoxes.add(partNameCheckBox);
                scrollParameterFilter.getChildren().add(partNameCheckBox);
            }

        }
    }

    public void setFilterMasterController(FilterMasterController filterMasterController) {this.filterMasterController = filterMasterController;}

    public void onCancelAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void onConfirmAction(ActionEvent event) throws SQLException {
        int selected = 0;

        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                selected++;
            }
        }

        if (selected > 1){
            return;

        } else if (selected == 0) {
            part.setCategory_id(WITHOUT_CATEGORY_ID);
            part.update();

        } else {
            for (CheckBox checkBox: checkBoxes) {
                if (checkBox.isSelected()) {
                    part.setCategory_id(CategoryManager.getCategoryId(checkBox.getText()));
                    part.update();
                }
            }
        }

        filterMasterController.updateTable();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}
}
