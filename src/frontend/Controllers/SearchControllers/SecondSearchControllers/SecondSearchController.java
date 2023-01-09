package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondSearchController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    public void onClickConfirmButton(){
        MainController.setNewStage("./src/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchConfirm.fxml", Constants.WINDOW_TITLE_CONFIRM_SEARCH);
    }

    @FXML
    public void onClickBackButton() {
        MainController.switchTab("./src/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", SESSION.getSearchTab());
    }
}
