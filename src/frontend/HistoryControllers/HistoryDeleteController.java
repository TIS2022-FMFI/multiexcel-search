package frontend.HistoryControllers;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class HistoryDeleteController implements Initializable {

    private HistoryMainController historyMainController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void onCancelAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void onConfirmAction(ActionEvent event) {
        if (historyMainController != null) {
            historyMainController.deleteSelectionConfirmed();
            historyMainController.refreshTable();
        } else {
            System.err.println("historyMainController == null");
        }
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    public void setHistoryMainController(HistoryMainController historyMainController) {
        this.historyMainController = historyMainController;
    }
}
