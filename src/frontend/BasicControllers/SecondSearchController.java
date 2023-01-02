package frontend.BasicControllers;

import backend.Sessions.SESSION;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondSearchController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onClickBackButton() {
        BasicController.switchTab("./src/frontend/BasicFXML/FirstSearch.fxml", SESSION.getSearchTab());
    }
}
