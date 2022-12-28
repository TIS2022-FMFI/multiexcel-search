package frontend.HistoryControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class CategoryFilterController implements Initializable {
    @FXML
    public VBox scrollCategoryFilter;

    private Map<String, Category> nameToCategory;
    private List<CheckBox> checkBoxes;
    private HistoryMainController historyMainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxes = new ArrayList<>();
        nameToCategory = new HashMap<>();
        List<Category> categories = CategoryManager.getAllCategories();
        if(categories != null){
            for (Category c : categories) {
                CheckBox categoryCheckbox = new CheckBox();
                categoryCheckbox.setText(c.getCategory_name());
                nameToCategory.put(c.getCategory_name(), c);
                checkBoxes.add(categoryCheckbox);
                scrollCategoryFilter.getChildren().add(categoryCheckbox);
            }
        }
    }

    @FXML
    public void onSelectAllAction(){
        for (CheckBox checkBox: checkBoxes) {
            checkBox.setSelected(true);
        }
    }

    @FXML
    public void onClearSelectionAction(){
        for (CheckBox checkBox: checkBoxes) {
            checkBox.setSelected(false);
        }
    }

    @FXML
    public void onCancelAction(ActionEvent event){
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    @FXML void onConfirmAction(ActionEvent event){
        if(historyMainController != null){
            historyMainController.setCategoryFilter(collectCheckedCategories());
            //historyMainController.refreshTable(); need to click filter instead
        }else{
            System.err.println("historyMainController == null");
        }
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    private void preSelect(){
        List<Category> categories = historyMainController.getCategoryFilter();
        if(categories == null){
            onSelectAllAction();
            return;
        }
        if(categories.size() == 0){
            onSelectAllAction();
            return;
        }

        for (Category c : categories) {
            for (CheckBox checkBox : checkBoxes) {
                if(checkBox.getText().equals(c.getCategory_name())){
                    checkBox.setSelected(true);
                }
            }
        }
    }

    private List<Category> collectCheckedCategories(){
        List<Category> categories = new ArrayList<>();
        for (CheckBox checkBox: checkBoxes) {
            if(checkBox.isSelected()){
                if(nameToCategory.containsKey(checkBox.getText())){
                    categories.add(nameToCategory.get(checkBox.getText()));
                }
            }
        }
        return categories;
    }

    public void setHistoryMainController(HistoryMainController historyMainController){
        this.historyMainController = historyMainController;
        preSelect();
    }
}
