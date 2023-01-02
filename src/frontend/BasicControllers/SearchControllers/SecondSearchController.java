package frontend.BasicControllers.SearchControllers;

import backend.Sessions.SESSION;
import frontend.BasicControllers.BasicController;
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
        BasicController.switchTab("./src/frontend/BasicFXML/SearchFXML/FirstSearch.fxml", SESSION.getSearchTab());
    }
}
