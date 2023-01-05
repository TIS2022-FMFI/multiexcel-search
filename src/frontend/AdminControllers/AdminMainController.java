package frontend.AdminControllers;

import backend.Sessions.SESSION;
import frontend.BasicControllers.BasicController;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

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
    @FXML
    private Tab searchTab;

    private void setTab(String path, Tab tab) throws IOException {
        FXMLLoader loader = new FXMLLoader();
        FileInputStream fxmlStream = new FileInputStream(path);
        Parent root = loader.load(fxmlStream);
        tab.setContent(root);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            setTab("./src/frontend/AdminFXML/HistoryFXML/HistoryMain.fxml", historyTab);
            SESSION.setHistoryTab(historyTab);

            setTab("./src/frontend/BasicFXML/SearchFXML/FirstSearch.fxml", searchTab);
            SESSION.setSearchTab(searchTab);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void importButton() {
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/AdminFXML/Import.fxml";
            setScene(loader, fxmlDocPath, "Import");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void setScene(FXMLLoader loader, String fxmlDocPath, String stageTitle) throws IOException {
        BasicController.setScene(loader, fxmlDocPath, stageTitle);
    }

    public void changePassword() {
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/ChangePasswordScreen.fxml";
            setScene(loader, fxmlDocPath, "Change password");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
