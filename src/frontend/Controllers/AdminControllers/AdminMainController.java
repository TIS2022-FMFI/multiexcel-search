package frontend.Controllers.AdminControllers;

import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Tab;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class AdminMainController implements Initializable {
    @FXML
    public Tab categoryTab;
    @FXML
    public Tab userTab;
    @FXML
    private Tab historyTab;
    @FXML
    private Tab searchTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            MainController.setTab("./src/frontend/FXML/HistoryFXML/HistoryMain.fxml", historyTab);
            SESSION.setHistoryTab(historyTab);

            MainController.setTab("./src/frontend/FXML/CategoryManagementFXML/CategoryMain.fxml", categoryTab);

            MainController.setTab("./src/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", searchTab);
            SESSION.setSearchTab(searchTab);

            MainController.setTab("./src/frontend/FXML/UserManagementFXML/UserMain.fxml", userTab);


        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void importButton() {
        String fxmlDocPath = "./src/frontend/FXML/AdminFXML/Import.fxml";
        MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_IMPORT);
    }

    @FXML
    public void logoutUser(ActionEvent event) {
        SESSION.logout(event);
    }


    public void changePassword() {
        String fxmlDocPath = "./src/frontend/FXML/ChangePasswordScreen.fxml";
        MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_CHANGE_PASSWORD);
    }
}
