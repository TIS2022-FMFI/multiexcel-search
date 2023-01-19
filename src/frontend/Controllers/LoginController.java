package frontend.Controllers;


import backend.Managers.UserManager;
import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.sql.SQLException;

public class LoginController {
    @FXML
    private TextField usernameField;
    @FXML
    private PasswordField passwordField;
    @FXML
    private Button loginButton;
    @FXML
    private Label errorLabel;

    @FXML
    private void initialize() {
        // Initialize components and add event listeners
        loginButton.setOnAction(event -> {
            try {
                login(event);
            } catch (SQLException e) {
                MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            }
        });
    }

    private void login(ActionEvent event) throws SQLException {
        // Get the username and password from the fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate the login credentials
        if (UserManager.validateCredentials(username, password)) {
            // Login successful, go to the next screen
            if (SESSION.getSession().getSuspended())
                errorLabel.setText("Account suspended");
            else
                goToNextScreen(event);

        } else {
            // Login failed, show an error message
            errorLabel.setText("Invalid username or password");
        }
    }

    private void goToNextScreen(ActionEvent event) {
        try {
            String fxmlDocPath = "/frontend/FXML/BasicFXML/BasicMain.fxml";

            if (SESSION.getSession().getUser_name().equals("admin")) {
                fxmlDocPath = "/frontend/FXML/AdminFXML/AdminMain.fxml";
            }

            MainController.replaceStageByEvent(fxmlDocPath, Constants.WINDOW_TITLE_LOGGEDIN, event);
        } catch (Exception e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
        }
    }
}