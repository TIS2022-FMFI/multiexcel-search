package frontend.HistoryControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;

public class UserFilterController implements Initializable {

    @FXML
    public VBox scrollUserFilter;

    private Map<String, User> usernameToUser;
    private List<CheckBox> checkBoxes;
    private HistoryMainController historyMainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxes = new ArrayList<>();
        usernameToUser = new HashMap<>();
        List<User> users = UserManager.getUsers(false);
        if(users != null){
            for (User u : users) {
                CheckBox userCheckbox = new CheckBox();
                userCheckbox.setText(u.getUser_name());
                usernameToUser.put(u.getUser_name(), u);
                checkBoxes.add(userCheckbox);
                scrollUserFilter.getChildren().add(userCheckbox);
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
            historyMainController.setUserFilter(collectCheckedUsers());
            historyMainController.refreshTable();
        }else{
            System.err.println("historyMainController == null");
        }
        ((Stage)(((Button)event.getSource()).getScene().getWindow())).close();
    }

    private void preSelect(){
        List<User> users = historyMainController.getUserFilter();
        if(users == null){
            onSelectAllAction();
            return;
        }
        if(users.size() == 0){
            onSelectAllAction();
            return;
        }

        for (User u : users) {
            for (CheckBox checkBox : checkBoxes) {
                if(checkBox.getText().equals(u.getUser_name())){
                    checkBox.setSelected(true);
                }
            }
        }
    }

    private List<User> collectCheckedUsers(){
        List<User> users = new ArrayList<>();
        for (CheckBox checkBox: checkBoxes) {
            if(checkBox.isSelected()){
                if(usernameToUser.containsKey(checkBox.getText())){
                    users.add(usernameToUser.get(checkBox.getText()));
                }
            }
        }
        return users;
    }

    public void setHistoryMainController(HistoryMainController historyMainController){
        this.historyMainController = historyMainController;
        preSelect();
    }


}
