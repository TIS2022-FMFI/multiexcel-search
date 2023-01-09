package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.net.URL;
import java.util.ResourceBundle;

public class SecondSearchConfirm implements Initializable {

    private SecondSearchController secondSearchController;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void onClickCancel(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void onClickConfirm(ActionEvent event) {
//        HistoryManager.saveSearchToHistory(SESSION.getCriteria(), secondSearchController.selectedParts, secondSearchController.categories);
//        PartManager.IncreaseRating(secondSearchController.selectedParts);
        SESSION.clearCriteria();

        MainController.switchTab("./src/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", SESSION.getSearchTab());

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void onClickExportToPdf() {
//        File file = MainController.saveFile("PDF Files", "*.pdf");
//        if (file != null)
//            Export.exportPartsToPdf(secondSearchController.parts, file.getAbsolutePath());
    }

    @FXML
    public void onClickExportToExcel() {
//        File file = MainController.saveFile("Excel Files", "*.xlsx");
//        if (file != null)
//            Export.exportPartsToXLS(secondSearchController.parts, file.getAbsolutePath());
    }
}
