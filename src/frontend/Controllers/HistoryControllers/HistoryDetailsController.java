package frontend.Controllers.HistoryControllers;

import backend.Entities.Category;
import backend.Entities.Customer;
import backend.Entities.Part;
import backend.Entities.Part_name;
import backend.Managers.CategoryManager;
import backend.Managers.CustomerManager;
import backend.Managers.PartManager;
import backend.Managers.PartNameManager;
import backend.Sessions.HistorySession;
import backend.Sessions.SESSION;
import backend.XLSImportExport.Export;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.io.File;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

import static backend.Models.Constants.*;

public class HistoryDetailsController implements Initializable {
    @FXML
    public TableView<Part> table_parts;
    public CheckBox check_customer;
    public CheckBox check_part_name;
    public CheckBox check_category;
    public CheckBox check_drawing;
    public CheckBox check_rubber;
    public CheckBox check_diameter_at;
    public CheckBox check_diameter_at_tol;
    public CheckBox check_length_l_at;
    public CheckBox check_length_l_at_tol;
    public CheckBox check_diameter_it;
    public CheckBox check_diameter_it_tol;
    public CheckBox check_length_l_it;
    public CheckBox check_length_l_it_tol;
    public CheckBox check_diameter_zt;
    public CheckBox check_diameter_zt_tol;
    public CheckBox check_length_l_zt;
    public CheckBox check_length_l_zt_tol;
    public CheckBox check_cr_steg;
    public CheckBox check_cr_niere;
    public CheckBox check_ca;
    public CheckBox check_ct;
    public CheckBox check_ck;
    public Button backButton;
    @FXML
    private TableColumn<Part, String> col_customer_name;
    @FXML
    private TableColumn<Part, String> col_part_name;
    @FXML
    private TableColumn<Part, String> col_category_name;
    @FXML
    private TableColumn<Part, ImageView> col_drawing;
    @FXML
    private TableColumn<Part, String> col_rubber;
    @FXML
    private TableColumn<Part, String> col_diameter_at;
    @FXML
    private TableColumn<Part, String> col_diameter_at_tol;
    @FXML
    private TableColumn<Part, String> col_length_l_at;
    @FXML
    private TableColumn<Part, String> col_length_l_at_tol;
    @FXML
    private TableColumn<Part, String> col_diameter_it;
    @FXML
    private TableColumn<Part, String> col_diameter_it_tol;
    @FXML
    private TableColumn<Part, String> col_length_l_it;
    @FXML
    private TableColumn<Part, String> col_length_l_it_tol;
    @FXML
    private TableColumn<Part, String> col_diameter_zt;
    @FXML
    private TableColumn<Part, String> col_diameter_zt_tol;
    @FXML
    private TableColumn<Part, String> col_length_l_zt;
    @FXML
    private TableColumn<Part, String> col_length_l_zt_tol;
    @FXML
    private TableColumn<Part, String> col_cr_steg;
    @FXML
    private TableColumn<Part, String> col_cr_niere;
    @FXML
    private TableColumn<Part, String> col_ca;
    @FXML
    private TableColumn<Part, String> col_ct;
    @FXML
    private TableColumn<Part, String> col_ck;

    //TODO: export to pdf/excel and hook it to buttons, how to export
    private List<Part> parts;

    private Map<BigInteger, String> idToCustomerName;
    private Map<BigInteger, String> idToCategoryName;
    private Map<BigInteger, String> idToPartName;

    private HistorySession historySession;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        parts = new ArrayList<>();
        historySession = HistorySession.getInstance();
        idToPartName = new HashMap<>();
        idToCategoryName = new HashMap<>();
        idToCustomerName = new HashMap<>();
        if (historySession.getCategoryIdToName() != null) {
            idToCategoryName.putAll(historySession.getCategoryIdToName());
        }
        table_parts.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);

        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/backImage.png")).toExternalForm());
        backButton.setGraphic(imageView);

        initializeAlignment();
        initializeController();
        populateTable();
    }

    @FXML
    private void openHistoryFXML() {
        MainController.switchTab("/frontend/FXML/HistoryFXML/HistoryMain.fxml", SESSION.getHistoryTab());
    }

    public void initializeAlignment() {

        col_customer_name.setStyle("-fx-alignment: CENTER");
        col_part_name.setStyle("-fx-alignment: CENTER");
        col_category_name.setStyle("-fx-alignment: CENTER");
        col_drawing.setStyle("-fx-alignment: CENTER");
        col_rubber.setStyle("-fx-alignment: CENTER");
        col_diameter_at.setStyle("-fx-alignment: CENTER");
        col_diameter_at_tol.setStyle("-fx-alignment: CENTER");
        col_length_l_at.setStyle("-fx-alignment: CENTER");
        col_length_l_at_tol.setStyle("-fx-alignment: CENTER");
        col_diameter_it.setStyle("-fx-alignment: CENTER");
        col_diameter_it_tol.setStyle("-fx-alignment: CENTER");
        col_length_l_it.setStyle("-fx-alignment: CENTER");
        col_length_l_it_tol.setStyle("-fx-alignment: CENTER");
        col_diameter_zt.setStyle("-fx-alignment: CENTER");
        col_diameter_zt_tol.setStyle("-fx-alignment: CENTER");
        col_length_l_zt.setStyle("-fx-alignment: CENTER");
        col_length_l_zt_tol.setStyle("-fx-alignment: CENTER");
        col_cr_steg.setStyle("-fx-alignment: CENTER");
        col_cr_niere.setStyle("-fx-alignment: CENTER");
        col_ca.setStyle("-fx-alignment: CENTER");
        col_ct.setStyle("-fx-alignment: CENTER");
        col_ck.setStyle("-fx-alignment: CENTER");
    }

    private void initializeController() {

        col_customer_name.setCellValueFactory(f -> createCustomerNameWrapperFromCustomerID(f.getValue().getCustomer_id()));
        col_part_name.setCellValueFactory(f -> createPartNameWrapperFromPartNumber(f.getValue().getPart_name_id()));
        col_category_name.setCellValueFactory(f -> createCategoryNameWrapperFromCategoryID(f.getValue().getCategory_id()));
        col_drawing.setCellValueFactory(new PropertyValueFactory<>("image"));
        col_rubber.setCellValueFactory(new PropertyValueFactory<>("rubber"));
        col_diameter_at.setCellValueFactory(new PropertyValueFactory<>("diameter_AT"));
        col_diameter_at_tol.setCellValueFactory(new PropertyValueFactory<>("diameter_AT_tol"));
        col_length_l_at.setCellValueFactory(new PropertyValueFactory<>("length_L_AT"));
        col_length_l_at_tol.setCellValueFactory(new PropertyValueFactory<>("length_L_AT_tol"));
        col_diameter_it.setCellValueFactory(new PropertyValueFactory<>("diameter_IT"));
        col_diameter_it_tol.setCellValueFactory(new PropertyValueFactory<>("diameter_IT_tol"));
        col_length_l_it.setCellValueFactory(new PropertyValueFactory<>("length_L_IT"));
        col_length_l_it_tol.setCellValueFactory(new PropertyValueFactory<>("length_L_IT_tol"));
        col_diameter_zt.setCellValueFactory(new PropertyValueFactory<>("diameter_ZT"));
        col_diameter_zt_tol.setCellValueFactory(new PropertyValueFactory<>("diameter_ZT_tol"));
        col_length_l_zt.setCellValueFactory(new PropertyValueFactory<>("length_L_ZT"));
        col_length_l_zt_tol.setCellValueFactory(new PropertyValueFactory<>("length_L_ZT_tol"));
        col_cr_steg.setCellValueFactory(new PropertyValueFactory<>("cr_steg"));
        col_cr_niere.setCellValueFactory(new PropertyValueFactory<>("cr_niere"));
        col_ca.setCellValueFactory(new PropertyValueFactory<>("ca"));
        col_ct.setCellValueFactory(new PropertyValueFactory<>("ct"));
        col_ck.setCellValueFactory(new PropertyValueFactory<>("ck"));
    }

    private void populateTable() {
        initializeParts();
        table_parts.setItems(FXCollections.observableArrayList(parts));
        System.out.println("Populated table!");
    }

    private ReadOnlyStringWrapper createCustomerNameWrapperFromCustomerID(BigInteger customerId) {
        if (!idToCustomerName.containsKey(customerId)) {
            Customer customer = CustomerManager.getCustomer(customerId);
            if (customer == null) {
                return new ReadOnlyStringWrapper();
            }
            idToCustomerName.put(customerId, customer.getCustomer_name());
        }

        return new ReadOnlyStringWrapper(idToCustomerName.get(customerId));
    }

    private ReadOnlyStringWrapper createCategoryNameWrapperFromCategoryID(BigInteger categoryId) {
        if (!idToCategoryName.containsKey(categoryId)) {
            Category category = CategoryManager.getCategory(categoryId);
            if (category == null) {
                throw new RuntimeException("Unknown category for part");
            }
            idToCategoryName.put(categoryId, category.getCategory_name());
        }

        return new ReadOnlyStringWrapper(idToCategoryName.get(categoryId));
    }

    private ReadOnlyStringWrapper createPartNameWrapperFromPartNumber(BigInteger partId) {
        if (!idToPartName.containsKey(partId)) {
            Part_name partName = PartNameManager.getPartName(partId);
            if (partName == null) {
                return new ReadOnlyStringWrapper();
            }
            idToPartName.put(partId, partName.getPart_name());
        }

        return new ReadOnlyStringWrapper(idToPartName.get(partId));
    }

    /*private <K> ReadOnlyStringWrapper createFromToWrapper(K first){
        if(first == null){
            return new ReadOnlyStringWrapper("null in arguments");
        }
        return new ReadOnlyStringWrapper(String.format("%s", first));
    }*/

    private void initializeParts() {
        parts = new ArrayList<>();
        if (historySession.getSelectedQuery() == null) {
            System.err.println("Passed query is null, cannot recover id. HistoryDetailsController::initializeParts()");
            return;
        }
        int queryId = historySession.getSelectedQuery().getQuery_id();

        List<Part> queryParts = PartManager.GetPartsByQueryId(queryId);
        if (queryParts != null) {
            parts = queryParts;
            System.out.printf("Requested parts associated with queryId = %d.%n Parts size: %d.%n Parts: [ %s ]", queryId, queryParts.size(), queryParts);
        } else {
            System.err.println("requested parts returned null");
        }
    }

    @FXML
    public void exportToExcel() {
        File file = MainController.saveFile("Excel Files", "*.xlsx");
        if (file != null)
            Export.exportPartsToXLS(parts, file.getAbsolutePath());
    }

    @FXML
    public void exportToPdf() {
        File file = MainController.saveFile("PDF Files", "*.pdf");
        if (file != null)
            Export.exportPartsToPdf(parts, file.getAbsolutePath());
    }

    public void setVisibleCustomer() {
        col_customer_name.setVisible(check_customer.isSelected());
    }

    public void setVisiblePartName() {
        col_part_name.setVisible(check_part_name.isSelected());
    }

    public void setVisibleCategory() {
        col_category_name.setVisible(check_category.isSelected());
    }

    public void setVisibleDrawing() {
        col_drawing.setVisible(check_drawing.isSelected());
    }

    public void setVisibleRubber() {
        col_rubber.setVisible(check_rubber.isSelected());
    }

    public void setVisibleDiameterAT() {
        col_diameter_at.setVisible(check_diameter_at.isSelected());
    }

    public void setVisibleDiameterATTOL() {
        col_diameter_at_tol.setVisible(check_diameter_at_tol.isSelected());
    }

    public void setVisibleLengthLAT() {
        col_length_l_at.setVisible(check_length_l_at.isSelected());
    }

    public void setVisibleLengthLATTOL() {
        col_length_l_at_tol.setVisible(check_length_l_at_tol.isSelected());
    }

    public void setVisibleDiameterIT() {
        col_diameter_it.setVisible(check_diameter_it.isSelected());
    }

    public void setVisibleDiameterITTOL() {
        col_diameter_it_tol.setVisible(check_diameter_it_tol.isSelected());
    }

    public void setVisibleLengthLIT() {
        col_length_l_it.setVisible(check_length_l_it.isSelected());
    }

    public void setVisibleLengthLITTOL() {
        col_length_l_it_tol.setVisible(check_length_l_it_tol.isSelected());
    }

    public void setVisibleDiameterZT() {
        col_diameter_zt.setVisible(check_diameter_zt.isSelected());
    }

    public void setVisibleDiameterZTTOL() {
        col_diameter_zt_tol.setVisible(check_diameter_zt_tol.isSelected());
    }

    public void setVisibleLengthLZT() {
        col_length_l_zt.setVisible(check_length_l_zt.isSelected());
    }

    public void setVisibleLengthLZTTOL() {
        col_length_l_zt_tol.setVisible(check_length_l_zt_tol.isSelected());
    }

    public void setVisibleCrSteg() {
        col_cr_steg.setVisible(check_cr_steg.isSelected());
    }

    public void setVisibleCrNiere() {
        col_cr_niere.setVisible(check_cr_niere.isSelected());
    }

    public void setVisibleCa() {
        col_ca.setVisible(check_ca.isSelected());
    }

    public void setVisibleCt() {
        col_ct.setVisible(check_ct.isSelected());
    }

    public void setVisibleCk() {
        col_ck.setVisible(check_ck.isSelected());
    }
}
