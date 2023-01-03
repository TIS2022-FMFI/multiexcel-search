package frontend.AdminControllers;

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
import frontend.BasicControllers.BasicController;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;

import java.math.BigInteger;
import java.net.URL;
import java.util.*;

public class HistoryDetailsController implements Initializable {

    private final String CUSTOMER_NAME_NOT_FOUND = "<unknown_customer>";
    private final String CATEGORY_NAME_NOT_FOUND = "<unknown_category>";
    private final String PART_NAME_NOT_FOUND = "<unknown_part>";
    @FXML
    public TableView<Part> table_parts;
    @FXML
    private TableColumn<Part, String> col_customer_name;
    @FXML
    private TableColumn<Part, String> col_part_name;
    @FXML
    private TableColumn<Part, String> col_category_name;
    @FXML
    private TableColumn<Part, String> col_drawing;
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
        initializeController();
        populateTable();
    }

    @FXML
    private void openHistoryFXML() {
        BasicController.switchTab("./src/frontend/BasicFXML/HistoryFXML/HistoryMain.fxml", SESSION.getHistoryTab());
    }

    private void initializeController() {

        col_customer_name.setCellValueFactory(f -> createCustomerNameWrapperFromCustomerID(f.getValue().getCustomer_id()));
        col_part_name.setCellValueFactory(f -> createPartNameWrapperFromPartNumber(f.getValue().getPart_name_id()));
        col_category_name.setCellValueFactory(f -> createCategoryNameWrapperFromCategoryID(f.getValue().getCategory_id()));
        col_drawing.setCellValueFactory(new PropertyValueFactory<>("drawing_id"));
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
                return new ReadOnlyStringWrapper(CUSTOMER_NAME_NOT_FOUND);
            }
            idToCustomerName.put(customerId, customer.getCustomer_name());
        }

        return new ReadOnlyStringWrapper(idToCustomerName.get(customerId));
    }

    private ReadOnlyStringWrapper createCategoryNameWrapperFromCategoryID(BigInteger categoryId) {
        if (!idToCategoryName.containsKey(categoryId)) {
            Category category = CategoryManager.getCategory(categoryId);
            if (category == null) {
                return new ReadOnlyStringWrapper(CATEGORY_NAME_NOT_FOUND);
            }
            idToCategoryName.put(categoryId, category.getCategory_name());
        }

        return new ReadOnlyStringWrapper(idToCategoryName.get(categoryId));
    }

    private ReadOnlyStringWrapper createPartNameWrapperFromPartNumber(BigInteger partId) {
        if (!idToPartName.containsKey(partId)) {
            Part_name partName = PartNameManager.getPartName(partId);
            if (partName == null) {
                return new ReadOnlyStringWrapper(PART_NAME_NOT_FOUND);
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
}
