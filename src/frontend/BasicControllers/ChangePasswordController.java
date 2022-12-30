package frontend.BasicControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import backend.Sessions.SESSION;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.paint.Color;

import java.sql.SQLException;

public class ChangePasswordController {
    @FXML
    private PasswordField passwordField;
    @FXML
    private PasswordField newPasswordFieldOne;
    @FXML
    private PasswordField newPasswordFieldTwo;
    @FXML
    private Button changeButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        // Initialize components and add event listeners
        changeButton.setOnAction(event -> {
            try {
                changePassword();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        });
    }

    private void changePassword() throws SQLException {
        if (validateChange()) {
            if (UserManager.changeUserPassword(SESSION.getSession(), newPasswordFieldOne.getText())) {
                errorLabel.setTextFill(Color.color(0, 1, 0));
                errorLabel.setText("Password was changed");
            }
        }
    }

    private boolean validateChange() {

        String password = passwordField.getText();
        String newPasswordOne = newPasswordFieldOne.getText();
        String newPasswordTwo = newPasswordFieldTwo.getText();

        if (!SESSION.getSession().getPassword().equals(password)) {
            errorLabel.setText("Invalid password for User: " + SESSION.getSession().getUser_name());
            return false;
        }

        if (!newPasswordOne.equals(newPasswordTwo)) {
            errorLabel.setText("Invalid input: New passwords don't match");
            return false;
        }

        if (newPasswordOne.equals(SESSION.getSession().getPassword())) {
            errorLabel.setText("Invalid input: New password is same as old one");
            return false;
        }

        return true;
    }

    private void goToNextScreen() {
        // TODO: Implement navigation to the next screen
    }
}
