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
    @FXML
    public Tab searchTab;

    public static <T> T loadNewFXML(String fxmlDocPath, String windowName) {
        try {
            FXMLLoader loader = new FXMLLoader();
            setScene(loader, fxmlDocPath, windowName);
            return loader.getController();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void switchTab(String fxmlDocPath, Tab tab) {
        try {
            FXMLLoader loader = new FXMLLoader();
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            tab.setContent(root);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

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

    public void changePassword() {
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/ChangePasswordScreen.fxml";
            setScene(loader, fxmlDocPath, "Change password");

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void setScene(FXMLLoader loader, String fxmlDocPath, String stageTitle) throws IOException {
        FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
        AnchorPane root = loader.load(fxmlStream);

        Scene scene = new Scene(root);
        Stage stage = new Stage();
        stage.setResizable(false);
        stage.setTitle(stageTitle);
        stage.setScene(scene);
        stage.show();
    }

}
