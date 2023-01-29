package frontend.Controllers.BasicControllers;

import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import frontend.Controllers.HistoryControllers.HistoryMainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;

public class BasicMainController implements Initializable {
    @FXML
    public TabPane mainTabPane;
    @FXML
    public Tab historyTab;
    @FXML
    public Tab searchTab;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        try {
            MainController.setTab("/frontend/FXML/HistoryFXML/HistoryMain.fxml", historyTab);
            SESSION.setHistoryTab(historyTab);

            MainController.setTab("/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", searchTab);

            mainTabPane.getSelectionModel().selectedItemProperty().addListener(
                    (o, oldTab, newTab) -> {
                        if (newTab.equals(historyTab))
                            HistoryMainController.refreshTab();
                    }
            );
        } catch (IOException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
        }
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
