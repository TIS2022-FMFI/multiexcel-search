package frontend.BasicControllers;



import backend.Checkers.PasswordChecker;
import backend.Sessions.SESSION;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
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
                throw new RuntimeException(e);
            }
        });
    }

    private void login(ActionEvent event) throws SQLException {
        // Get the username and password from the fields
        String username = usernameField.getText();
        String password = passwordField.getText();

        // Validate the login credentials
        if (validateCredentials(username, password)) {
            // Login successful, go to the next screen
            goToNextScreen(event);

        } else {
            // Login failed, show an error message
            errorLabel.setText("Invalid username or password");
        }
    }

    private boolean validateCredentials(String username, String password) throws SQLException {

        PasswordChecker passwordChacker = new PasswordChecker();
        passwordChacker.setUserName(username);
        passwordChacker.setPassword(password);

        return passwordChacker.correctPassword();
    }

    private void goToNextScreen(ActionEvent event) {
        try {

            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/BasicMain.fxml";

            if (SESSION.getSession().getUser_name().equals("admin")) {
                fxmlDocPath = "./src/frontend/AdminFXML/AdminMain.fxml";
            }

            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            Scene scene = new Scene(root);
            Stage stage =  (Stage)  ((Node)event.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}