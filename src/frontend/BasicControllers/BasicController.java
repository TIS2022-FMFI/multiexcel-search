package frontend.BasicControllers;

import backend.Sessions.SESSION;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.control.Tab;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BasicController implements Initializable {

    @FXML
    public Tab historyTab;

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
}
