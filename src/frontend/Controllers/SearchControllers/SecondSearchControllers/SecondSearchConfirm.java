package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Entities.Category;
import backend.Entities.Part;
import backend.Managers.HistoryManager;
import backend.Managers.PartManager;
import backend.Sessions.SESSION;
import backend.XLSImportExport.Export;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.stage.Stage;

import java.io.File;
import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class SecondSearchConfirm implements Initializable {

    private SecondSearchController secondSearchController;
    private List<Part> selectedParts;

    private List<Category> categories;

    public void setSelectedParts(List<Part> selectedParts) {this.selectedParts = selectedParts;}

    public void setCategories(List<Category> categories) {this.categories = categories;}

    @Override
    public void initialize(URL location, ResourceBundle resources) {
    }

    @FXML
    public void onClickCancel(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void onClickConfirm(ActionEvent event) {
        HistoryManager.saveSearchToHistory(SESSION.getCriteria(), selectedParts, categories);
        PartManager.IncreaseRating(selectedParts);
        SESSION.clearCriteria();

        MainController.switchTab("/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", SESSION.getSearchTab());

        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    public void onClickExportToPdf() {
        File file = MainController.saveFile("PDF Files", "*.pdf");
        if (file != null)
            Export.exportPartsToPdf(selectedParts, file.getAbsolutePath());
    }

    @FXML
    public void onClickExportToExcel() {
        File file = MainController.saveFile("Excel Files", "*.xlsx");
        if (file != null)
            Export.exportPartsToXLS(selectedParts, file.getAbsolutePath());
    }
}
