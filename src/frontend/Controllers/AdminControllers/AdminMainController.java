package frontend.Controllers.AdminControllers;

import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
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
            MainController.setTab("/frontend/FXML/HistoryFXML/HistoryMain.fxml", historyTab);
            SESSION.setHistoryTab(historyTab);

            MainController.setTab("/frontend/FXML/CategoryManagementFXML/CategoryMain.fxml", categoryTab);

            MainController.setTab("/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", searchTab);
            SESSION.setSearchTab(searchTab);

            MainController.setTab("/frontend/FXML/UserManagementFXML/UserMain.fxml", userTab);


        } catch (IOException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
        }
    }

    @FXML
    public void importButton() {
        String fxmlDocPath = "/frontend/FXML/AdminFXML/Import.fxml";
        MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_IMPORT);
    }

    @FXML
    public void logoutUser(ActionEvent event) {
        SESSION.logout(event);
    }


    public void changePassword() {
        String fxmlDocPath = "/frontend/FXML/ChangePasswordScreen.fxml";
        MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_CHANGE_PASSWORD);
    }
}
