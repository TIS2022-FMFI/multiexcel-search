package frontend.BasicControllers;

import backend.Sessions.SESSION;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BasicController implements Initializable {

    @FXML
    public Tab historyTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/HistoryMain.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            Parent root = loader.load(fxmlStream);
            historyTab.setContent(root);
            SESSION.setHistoryTab(historyTab);

        } catch(IOException e) {
            throw new RuntimeException(e);
        }
    }
    public void changePassword() {
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/ChangePasswordScreen.fxml";
            setScene(loader, fxmlDocPath);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setScene(FXMLLoader loader, String fxmlDocPath) throws IOException {
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        AnchorPane root = loader.load(fxmlStream);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setScene(scene);
        stage.show();
    }

}
