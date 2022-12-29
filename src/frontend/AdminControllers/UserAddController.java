package frontend.AdminControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserAddController implements Initializable {

    public TextField userNameField;
    public PasswordField passwordField;
    public Button addButton;
    public Button closeButton;

    private UserMainController mainController;

    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {}

    /**
     * Initializes reffernce of main controller
     * @param controller - refference to UserMainContorller
     */
    public void init(UserMainController controller){
        mainController = controller;
    }

    /**
     * Checks if username or password from text field is not empty
     * Checks if user with name from text field is not already in database
     * Calls User Manager to insert new user to database
     */
    @FXML
    private void addUser(){
        String userName = userNameField.getText();
        String password = passwordField.getText();

        ObservableList<User> allUsers = UserManager.getUsers(false);

        if(Objects.equals(userName, "")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Failure");
            errorAlert.setContentText("Please enter user name");
            errorAlert.showAndWait();
            return;
        }

        if(Objects.equals(password, "")){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Failure");
            errorAlert.setContentText("Please enter password");
            errorAlert.showAndWait();
            return;
        }

        if(allUsers != null){
            for(User user : allUsers){
                if(Objects.equals(user.getUser_name(), userName)){
                    Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                    errorAlert.setHeaderText("Failure");
                    errorAlert.setContentText("User with this name already exists");
                    errorAlert.showAndWait();
                    return;
                }
            }
        }

        if(!UserManager.addUser(userName, password, false)){
            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
            errorAlert.setHeaderText("Failure");
            errorAlert.setContentText("Adding user has failed");
            errorAlert.showAndWait();
            return;
        }
        Stage stage = (Stage) addButton.getScene().getWindow();
        stage.close();
        mainController.updateList();
    }

    /**
     * Closes window for adding a user
     */
    @FXML
    public void Close() {
        Stage stage = (Stage) closeButton.getScene().getWindow();
        stage.close();
    }
}
