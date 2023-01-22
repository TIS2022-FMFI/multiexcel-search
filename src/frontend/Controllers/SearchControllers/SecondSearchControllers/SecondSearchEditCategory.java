package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Entities.Category;
import backend.Entities.Part;
import backend.Managers.CategoryManager;
import backend.Models.Constants;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;


import java.math.BigInteger;
import java.net.URL;
import java.util.*;

public class SecondSearchEditCategory implements Initializable {

    @FXML
    public VBox scrollParameterFilter;
    private Part part;
    List<CheckBox> checkBoxes;


    public void setPart(Part part) {this.part = part;}

    public SecondSearchEditCategory(Part part, String docPath) {

        this.part = part;
        SecondSearchEditCategory secondSearchEditCategory = MainController.setNewStage(docPath, Constants.WINDOW_TITLE_EDIT_CATEGORY);

    }
    public void onCancelAction(ActionEvent actionEvent) {
    }

    public void onConfirmAction(ActionEvent actionEvent) {
        int selected = 0;
        for (CheckBox checkBox: checkBoxes){
            if (checkBox.isSelected()){
                selected++;
                System.out.println(checkBox.getText());
            }

        }

        if (selected > 1) return;


    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

        checkBoxes = new ArrayList<>();
        List<Category> parameters = CategoryManager.getAllCategories();

        if (parameters != null) {

            for(Category c: parameters) {

                CheckBox partNameCheckBox = new CheckBox();
                partNameCheckBox.setText(c.getName());
                if (BigInteger.valueOf(c.getCategory_id()).equals(part.getCategory_id())){
                    partNameCheckBox.setSelected(true);
                }
                checkBoxes.add(partNameCheckBox);
            }

        }
    }
}
