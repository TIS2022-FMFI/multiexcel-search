package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Entities.Category;
import backend.Entities.Part;
import backend.Managers.CategoryManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.RadioButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.math.BigInteger;
import java.net.URL;
import java.sql.SQLException;
import java.util.List;
import java.util.ResourceBundle;

public class SecondSearchEditCategory implements Initializable {

    @FXML
    public VBox scrollParameterFilter;
    ToggleGroup group = new ToggleGroup();
    SecondSearchController secondSearchController;
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

    public void setSecondSearchController(SecondSearchController secondSearchController) {
        this.secondSearchController = secondSearchController;
    }

    public void onCancelAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void onConfirmAction(ActionEvent event) throws SQLException {

        RadioButton radioButton = (RadioButton) group.getSelectedToggle();
        part.setCategory_id(CategoryManager.getCategoryId(radioButton.getText()));
        part.update();

        secondSearchController.updateTable();
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

}
