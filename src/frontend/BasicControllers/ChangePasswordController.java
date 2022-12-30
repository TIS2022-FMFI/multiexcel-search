package frontend.BasicControllers;

import backend.Managers.UserManager;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;

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

        String password = passwordField.getText();
        String newPasswordOne = newPasswordFieldOne.getText();
        String newPasswordTwo = newPasswordFieldTwo.getText();

        if (!newPasswordOne.equals(newPasswordTwo)) {
            errorLabel.setText("New passwords are not same");
        }

        if (validateChange(password, newPasswordOne)) {
            // Password change successful, go to the next screen
            goToNextScreen();
        } else {
            // Password change failed, show an error message
            errorLabel.setText("Invalid username or password");
        }
    }

    private boolean validateChange(String password, String newPassword) throws SQLException {

        UserManager userManager = new UserManager();

        return true;
    }

    private void goToNextScreen() {
        // TODO: Implement navigation to the next screen
    }
}
