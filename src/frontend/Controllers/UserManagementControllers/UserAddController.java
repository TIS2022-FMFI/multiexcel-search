package frontend.Controllers.UserManagementControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserAddController implements Initializable {

    public TextField userNameField;
    public Button addButton;
    public Button closeButton;
    public Label errorLabel;
    public PasswordField passwordField1;
    public PasswordField passwordField2;

    private UserMainController mainController;

    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
    }

    /**
     * Initializes reffernce of main controller
     *
     * @param controller - refference to UserMainContorller
     */
    public void init(UserMainController controller) {
        mainController = controller;
    }

    /**
     * Checks if username or password from text field is not empty
     * Checks if user with name from text field is not already in database
     * Calls User Manager to insert new user to database
     */
    @FXML
    private void addUser() {
        String userName = userNameField.getText();
        String password1 = passwordField1.getText();
        String password2 = passwordField2.getText();

        ObservableList<User> allUsers = UserManager.getAllUsers(false);

        if (Objects.equals(userName, "")) {
            errorLabel.setText("Please enter user name");
            return;
        }

        if (Objects.equals(password1, "") || Objects.equals(password2, "")) {
            errorLabel.setText("Please enter password");
            return;
        }

        if (!Objects.equals(password1, password2)) {
            errorLabel.setText("Passwords do not match");
            return;
        }

        if (allUsers != null) {
            for (User user : allUsers) {
                if (Objects.equals(user.getUser_name(), userName)) {
                    errorLabel.setText("User with this name already exists");
                    return;
                }
            }
        }

        if (!UserManager.addUser(userName, password1, false)) {
            errorLabel.setText("Adding user has failed");
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
