package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Entities.Category;
import backend.Entities.Part;
import backend.Managers.CategoryManager;
import frontend.Controllers.AbstractControllers.FilterMasterController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
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
    ToggleGroup group = new ToggleGroup();
    FilterMasterController filterMasterController;
    Part part;


    public void setPart(Part part) {

        this.part = part;

        List<Category> parameters = CategoryManager.getAllCategories();

        if (parameters != null) {

            for (Category c : parameters) {

                RadioButton radioButton = new RadioButton();
                radioButton.setText(c.getName());

                if (BigInteger.valueOf(c.getCategory_id()).equals(part.getCategory_id())) {
                    radioButton.setSelected(true);
                }
                radioButton.setToggleGroup(group);

                scrollParameterFilter.getChildren().add(radioButton);
            }

        }
    }

    public void setFilterMasterController(FilterMasterController filterMasterController) {
        this.filterMasterController = filterMasterController;
    }

    public void onCancelAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void onConfirmAction(ActionEvent event) throws SQLException {

        RadioButton radioButton = (RadioButton) group.getSelectedToggle();
        part.setCategory_id(CategoryManager.getCategoryId(radioButton.getText()));
        part.update();

        filterMasterController.updateTable();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {}

}
