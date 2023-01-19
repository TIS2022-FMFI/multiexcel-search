import backend.Models.Constants;
import backend.Sessions.DBS;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Objects;
import java.util.Properties;

public class Main extends Application {
    public static void main(String[] args) {
        try {
            java.util.Properties prop = new Properties();
            prop.loadFromXML(Files.newInputStream(Paths.get("./configuration.xml")));
            Connection connection = DriverManager.getConnection(
                    prop.getProperty("database"),
                    prop.getProperty("user"),
                    prop.getProperty("password"));
            DBS.setConnection(connection);
        } catch (SQLException | IOException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
        }
        Application.launch();
    }

    @Override
    public void start(Stage stage) throws Exception {
        String fxmlDocPath = "/frontend/FXML/LoginScreen.fxml";
        AnchorPane root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(fxmlDocPath)));

        Scene scene = new Scene(root);
//        stage.setMaximized(true);
        stage.setScene(scene);
        //stage.setOnCloseRequest(event -> Platform.exit());
        stage.setTitle(Constants.WINDOW_TITLE_LOGIN);
        stage.show();
    }
}