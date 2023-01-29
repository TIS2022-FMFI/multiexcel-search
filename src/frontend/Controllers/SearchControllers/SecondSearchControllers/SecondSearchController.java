package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Entities.Part;
import backend.Managers.FirstSearchManager;
import backend.Managers.SecondSearchManager;
import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.MainController;
import frontend.Wrappers.PartWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;

import java.net.URL;
import java.util.*;

public class SecondSearchController implements Initializable {

    public CheckBox check_part_number;
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

    public TableView<PartWrapper> table_parts;
    public TableColumn<PartWrapper, CheckBox> col_check_box;
    public TableColumn<PartWrapper, Button> col_button_add_cat;
    public TableColumn<PartWrapper, String> col_part_number;
    public TableColumn<PartWrapper, String> col_part_name;
    public TableColumn<PartWrapper, String> col_category;
    public TableColumn<PartWrapper, String> col_rubber;
    public TableColumn<PartWrapper, String> col_diameter_at;
    public TableColumn<PartWrapper, String> col_length_l_at;
    public TableColumn<PartWrapper, String> col_diameter_it;
    public TableColumn<PartWrapper, String> col_length_l_it;
    public TableColumn<PartWrapper, String> col_diameter_zt;
    public TableColumn<PartWrapper, String> col_length_l_zt;
    public TableColumn<PartWrapper, String> col_cr_steg;
    public TableColumn<PartWrapper, String> col_cr_niere;
    public TableColumn<PartWrapper, String> col_ca;
    public TableColumn<PartWrapper, String> col_ct;
    public TableColumn<PartWrapper, String> col_ck;
    public TableColumn<PartWrapper, String> col_customer;
    public TableColumn<PartWrapper, ImageView> col_drawing;

    public TableColumn<PartWrapper, String> col_diameter_at_tol;
    public TableColumn<PartWrapper, String> col_length_l_at_tol;
    public TableColumn<PartWrapper, String> col_diameter_it_tol;
    public TableColumn<PartWrapper, String> col_length_l_it_tol;
    public TableColumn<PartWrapper, String> col_diameter_zt_tol;
    public TableColumn<PartWrapper, String> col_length_l_zt_tol;

    public Button backButton;
    public Button confirmButton;
    public Button orderByRatingButton;
    public Button orderByPriorityButton;

    private boolean orderByPriorityEnabled;
    private boolean orderByRatingChecked;
    private boolean orderByPriorityChecked;
    private List<Part> resultOfFirstSearch;
    private ObservableList<PartWrapper> parts;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resultOfFirstSearch = FirstSearchManager.search(SESSION.getCriteria());

        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/backImage.png")).toExternalForm());
        backButton.setGraphic(imageView);

        ImageView imageView2 = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/saveImage.png")).toExternalForm());
        confirmButton.setGraphic(imageView2);

        initializeAlignment();
        initializeController();

        if(!SESSION.getCriteria().hasPriorities()){
            orderByPriorityEnabled = false;
            orderByPriorityButton.setDisable(true);
        }
        else{
            orderByPriorityEnabled = true;
            orderByPriorityChecked = true;
            orderByPriorityButton.setStyle("-fx-background-color: #8a8f96");
        }
        orderByRatingChecked = false;
    }

    private void initializeController() {

        col_check_box.setCellValueFactory(new PropertyValueFactory<>("checkBox"));
        col_button_add_cat.setCellValueFactory(new PropertyValueFactory<>("button"));
        col_part_number.setCellValueFactory(new PropertyValueFactory<>("partNumber"));
        col_part_name.setCellValueFactory(new PropertyValueFactory<>("partName"));
        col_category.setCellValueFactory(new PropertyValueFactory<>("categoryName"));
        col_rubber.setCellValueFactory(new PropertyValueFactory<>("rubberValue"));
        col_diameter_at.setCellValueFactory(new PropertyValueFactory<>("diameterAT"));
        col_length_l_at.setCellValueFactory(new PropertyValueFactory<>("lengthLAT"));
        col_diameter_it.setCellValueFactory(new PropertyValueFactory<>("diameterIT"));
        col_length_l_it.setCellValueFactory(new PropertyValueFactory<>("lengthLIT"));
        col_diameter_zt.setCellValueFactory(new PropertyValueFactory<>("diameterZT"));
        col_length_l_zt.setCellValueFactory(new PropertyValueFactory<>("lengthLZT"));
        col_cr_steg.setCellValueFactory(new PropertyValueFactory<>("crSteg"));
        col_cr_niere.setCellValueFactory(new PropertyValueFactory<>("crNiere"));
        col_ca.setCellValueFactory(new PropertyValueFactory<>("caValue"));
        col_ct.setCellValueFactory(new PropertyValueFactory<>("ctValue"));
        col_ck.setCellValueFactory(new PropertyValueFactory<>("ckValue"));
        col_customer.setCellValueFactory(new PropertyValueFactory<>("customer"));
        col_drawing.setCellValueFactory(new PropertyValueFactory<>("image"));
        col_diameter_at_tol.setCellValueFactory(new PropertyValueFactory<>("diameterATTOL"));
        col_diameter_it_tol.setCellValueFactory(new PropertyValueFactory<>("diameterITTOL"));
        col_diameter_zt_tol.setCellValueFactory(new PropertyValueFactory<>("diameterZTTOL"));
        col_length_l_at_tol.setCellValueFactory(new PropertyValueFactory<>("lengthLATTOL"));
        col_length_l_it_tol.setCellValueFactory(new PropertyValueFactory<>("lengthLITTOL"));
        col_length_l_zt_tol.setCellValueFactory(new PropertyValueFactory<>("lengthLZTTOL"));

        parts = FXCollections.observableArrayList();

        if (resultOfFirstSearch != null) {

            for (Part p : resultOfFirstSearch) {
                parts.add(new PartWrapper(p, this));
            }
        }
        table_parts.setItems(parts);
    }

    public void initializeAlignment() {

        col_check_box.setStyle("-fx-alignment: CENTER");
        col_button_add_cat.setStyle("-fx-alignment: CENTER");
        col_part_number.setStyle("-fx-alignment: CENTER");
        col_customer.setStyle("-fx-alignment: CENTER");
        col_part_name.setStyle("-fx-alignment: CENTER");
        col_category.setStyle("-fx-alignment: CENTER");
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

    public List<Part> selectedParts() {
        ArrayList<Part> selectedPartsList = new ArrayList<>();

        for (PartWrapper partWrapper : parts) {
            if (partWrapper.isChecked()) {
                selectedPartsList.add(partWrapper.getPart());
            }
        }
        return selectedPartsList;
    }

    @FXML
    public void onClickConfirmButton() {

        SecondSearchConfirmController secondSearchConfirm = MainController.setNewStage("/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchConfirm.fxml", Constants.WINDOW_TITLE_CONFIRM_SEARCH);
        assert secondSearchConfirm != null;
        secondSearchConfirm.setSelectedParts(selectedParts());
    }

    @FXML
    public void onClickBackButton() {
        MainController.switchTab("/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", SESSION.getSearchTab());
    }


    public void setVisiblePartNumber() {
        col_part_number.setVisible(check_part_number.isSelected());
    }

    public void setVisibleCustomer() {
        col_customer.setVisible(check_customer.isSelected());
    }

    public void setVisiblePartName() {
        col_part_name.setVisible(check_part_name.isSelected());
    }

    public void setVisibleCategory() {
        col_category.setVisible(check_category.isSelected());
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

    @FXML
    public void onClickOrderByPriority() {
        if(!orderByPriorityEnabled || orderByPriorityChecked)
            return;

        SecondSearchManager.sortByPriority(resultOfFirstSearch, SESSION.getCriteria());

        orderByPriorityChecked = true;
        orderByRatingChecked = false;
        orderByPriorityButton.setStyle("-fx-background-color: #8a8f96");
        orderByRatingButton.setStyle("-fx-base: #f2f2f2");
        refreshTable();
    }

    @FXML
    public void onClickOrderByRating() {
        if(orderByPriorityEnabled && orderByRatingChecked)
            return;
        if(orderByRatingChecked){
            resultOfFirstSearch.sort((o1, o2) -> {
                Double val1 = o1.getDiameter_AT();
                Double val2 = o2.getDiameter_AT();
                if(Objects.equals(val1, val2))
                    return 0;
                if(val2 != null && val1 == null)
                    return 1;
                else if(val2 == null)
                    return -1;
                else {
                    return Double.compare(o2.getDiameter_AT(), o1.getDiameter_AT());
                }
            });
            orderByRatingChecked = false;
            orderByRatingButton.setStyle("-fx-base: #f2f2f2");
            refreshTable();
            return;
        }

        SecondSearchManager.sortByRating(resultOfFirstSearch);

        orderByRatingButton.setStyle("-fx-background-color: #8a8f96");
        if(orderByPriorityEnabled){
            orderByPriorityChecked = false;
            orderByPriorityButton.setStyle("-fx-base: #f2f2f2");
        }
        orderByRatingChecked = true;

        refreshTable();
    }

    public void updateTable() {
        parts.retainAll();

        refreshTable();
    }


    public void refreshTable() {

        parts.retainAll();

        for (Part p : resultOfFirstSearch) {
            parts.add(new PartWrapper(p, this));
        }

        table_parts.setItems(parts);
    }
}
