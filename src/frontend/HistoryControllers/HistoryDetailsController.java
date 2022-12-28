package frontend.HistoryControllers;

import backend.Entities.*;
import backend.Managers.*;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.util.*;

public class HistoryDetailsController implements Initializable {

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

    @FXML
    private TableColumn<Part, Boolean> col_checkbox;

    //TODO: how to add pictures?
    //TODO: do we show rating?
    //TODO: export to pdf/excel and hook it to buttons
    //TODO: vymysliet chekboxy
    //DONE: get part name from id
    //DONE: get customer name from id
    //DONE: get category name from id

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
        initializeController();
        populateTable();
    }

    @FXML
    private void openHistoryFXML(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/HistoryMain.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);


            Scene scene = new Scene(root);

            Stage stage =  (Stage)  ((Node)actionEvent.getSource()).getScene().getWindow();
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private void initializeController(){

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
        col_checkbox.setCellValueFactory( new PropertyValueFactory<>( "isSelected" ));
        col_checkbox.setCellFactory( CheckBoxTableCell.forTableColumn(col_checkbox));

    }

    private void populateTable(){
        initializeParts(); //remove, only for testing
        table_parts.setItems(FXCollections.observableArrayList(parts));
        System.out.println("Populated table!");
    }

    private ReadOnlyStringWrapper createCustomerNameWrapperFromCustomerID(BigInteger customerId){
        if(!idToCustomerName.containsKey(customerId)){
            Customer customer = CustomerManager.getCustomer(customerId);
            if(customer == null){
                return new ReadOnlyStringWrapper("not existing customer");
            }
            idToCustomerName.put(customerId, customer.getCustomer_name());
        }

        return new ReadOnlyStringWrapper(idToCustomerName.get(customerId));
    }

    private ReadOnlyStringWrapper createCategoryNameWrapperFromCategoryID(BigInteger categoryId){
        if(!idToCategoryName.containsKey(categoryId)){
            Category category = CategoryManager.getCategory(categoryId);
            if(category == null){
                return new ReadOnlyStringWrapper("not existing category");
            }
            idToCategoryName.put(categoryId, category.getCategory_name());
        }

        return new ReadOnlyStringWrapper(idToCategoryName.get(categoryId));
    }

    private ReadOnlyStringWrapper createPartNameWrapperFromPartNumber(BigInteger partId){
        if(!idToPartName.containsKey(partId)){
            Part_name partName = PartNameManager.getPartName(partId);
            if(partName == null){
                return new ReadOnlyStringWrapper("not existing part_name (Part_name::getPartName())");
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

    private void initializeParts(){
        parts = new ArrayList<>();
        if (historySession.getSelectedQuery() == null){
            System.err.println("Passed query is null, cannot recover id. HistoryDetailsController::initializeParts()");
            return;
        }
        int queryId = historySession.getSelectedQuery().getQuery_id();

        List<Part> queryParts = PartManager.GetPartsByQueryId(queryId);
        if(queryParts != null){
            parts = queryParts;
            System.out.printf("Requested parts associated with queryId = %d.%n Parts size: %d.%n Parts: [ %s ]", queryId, queryParts.size(), queryParts);
        }else{
            System.err.println("requested parts returned null");
        }
    }
}