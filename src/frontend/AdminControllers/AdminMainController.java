package frontend.AdminControllers;

import backend.Sessions.SESSION;
import frontend.BasicControllers.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.net.URL;
import java.util.*;

public class AdminMainController implements Initializable {
    @FXML
    private Button importButton;
    @FXML
    private Button logoffButton;
    @FXML
    private Button settingsButton;
    @FXML
    private TabPane tabPane;
    @FXML
    private Tab historyTab;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/HistoryMain.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            Parent root = loader.load(fxmlStream);
            historyTab.setContent(root);
            SESSION.setHistoryTab(historyTab);

        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void importButton() {
        try {

            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/AdminFXML/Import.fxml";
            setScene(loader, fxmlDocPath);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setScene(FXMLLoader loader, String fxmlDocPath) throws IOException {
        BasicController.setScene(loader, fxmlDocPath);
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
}
