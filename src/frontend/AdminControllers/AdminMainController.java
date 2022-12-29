package frontend.AdminControllers;

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
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {
    @FXML
    public Tab categoryTab;
    @FXML
    private Button importButton;
    @FXML
    private Button logoffButton;
    @FXML
    private Button settingsButton;
    @FXML
    private TabPane tabPane;


    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try
        {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/AdminFXML/CategoryMain.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            Parent root = loader.load(fxmlStream);
            categoryTab.setContent(root);
        }
        catch(IOException e)
        {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void importButton(){
        try {

            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/AdminFXML/Import.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
