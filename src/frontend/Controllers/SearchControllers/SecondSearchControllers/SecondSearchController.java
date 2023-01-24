package frontend.Controllers.SearchControllers.SecondSearchControllers;

import backend.Entities.Category;
import backend.Entities.Part;
import backend.Managers.FirstSearchManager;
import backend.Managers.SecondSearchManager;
import backend.Models.Constants;
import backend.Models.Filterable;
import backend.Sessions.SESSION;
import backend.Wrappers.PartWrapper;

import frontend.Controllers.AbstractControllers.FilterController;
import frontend.Controllers.AbstractControllers.FilterMasterController;
import frontend.Controllers.AbstractControllers.MainController;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static backend.Managers.CategoryManager.getAllCategories;

public class SecondSearchController implements Initializable, FilterMasterController {

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
    //TODO: pridanie zobrazenia obrazkov
    public TableColumn<PartWrapper, String> col_drawing;

    public TableColumn<PartWrapper, String> col_diameter_at_tol;
    public TableColumn<PartWrapper, String> col_length_l_at_tol;
    public TableColumn<PartWrapper, String> col_diameter_it_tol;
    public TableColumn<PartWrapper, String> col_length_l_it_tol;
    public TableColumn<PartWrapper, String> col_diameter_zt_tol;
    public TableColumn<PartWrapper, String> col_length_l_zt_tol;


    private List<Part> resultOfFirstSearch;
    private List<Category> categories;

    private List<Part> partsAfterFilter;
    private ObservableList<PartWrapper> parts;


    @Override
    public void initialize(URL location, ResourceBundle resources) {

        resultOfFirstSearch = FirstSearchManager.search(SESSION.getCriteria());
        categories = getAllCategories();

        initializeController();
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

        //col_drawing.setCellValueFactory(new PropertyValueFactory<>());

        col_diameter_at_tol.setCellValueFactory(new PropertyValueFactory<>("diameterATTOL"));
        col_diameter_it_tol.setCellValueFactory(new PropertyValueFactory<>("diameterITTOL"));
        col_diameter_zt_tol.setCellValueFactory(new PropertyValueFactory<>("diameterZTTOL"));
        col_length_l_at_tol.setCellValueFactory(new PropertyValueFactory<>("lengthLATTOL"));
        col_length_l_it_tol.setCellValueFactory(new PropertyValueFactory<>("lengthLITTOL"));
        col_length_l_zt_tol.setCellValueFactory(new PropertyValueFactory<>("lengthLZTTOL"));

        parts = FXCollections.observableArrayList();

        if (resultOfFirstSearch != null) {

            for (Part p: resultOfFirstSearch) {
                parts.add(new PartWrapper(p, this));
            }
        }
        table_parts.setItems(parts);
    }

    public List<Part> selectedParts() {
        ArrayList<Part> selectedPartsList = new ArrayList<>();

        for (PartWrapper partWrapper: parts) {
            if (partWrapper.isChecked()) {
                selectedPartsList.add(partWrapper.getPart());
            }
        }
        return selectedPartsList;
    }

    @FXML
    public void onClickConfirmButton() {

        SecondSearchConfirm secondSearchConfirm = MainController.setNewStage("/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchConfirm.fxml", Constants.WINDOW_TITLE_CONFIRM_SEARCH);
        assert secondSearchConfirm != null;
        secondSearchConfirm.setSelectedParts(selectedParts());
        secondSearchConfirm.setCategories(getCategories());
    }

    @FXML
    public void onClickBackButton() {
        MainController.switchTab("/frontend/FXML/SearchFXML/FirstSearchFXML/FirstSearch.fxml", SESSION.getSearchTab());
    }

    public List<Category> getCategories() {return categories;}

    public void setVisiblePartNumber() {
        col_part_number.setVisible(!check_part_number.isSelected());
    }

    public void setVisibleCustomer() {
        col_customer.setVisible(!check_customer.isSelected());
    }

    public void setVisiblePartName() {
        col_part_name.setVisible(!check_part_name.isSelected());
    }

    public void setVisibleCategory() {col_category.setVisible(!check_category.isSelected()); }

    public void setVisibleDrawing() {
        col_drawing.setVisible(!check_drawing.isSelected());
    }

    public void setVisibleRubber() {
        col_rubber.setVisible(!check_rubber.isSelected());
    }

    public void setVisibleDiameterAT() {
        col_diameter_at.setVisible(!check_diameter_at.isSelected());
    }

    public void setVisibleDiameterATTOL() {
        col_diameter_at_tol.setVisible(!check_diameter_at_tol.isSelected());
    }

    public void setVisibleLengthLAT() {
        col_length_l_at.setVisible(!check_length_l_at.isSelected());
    }

    public void setVisibleLengthLATTOL() {col_length_l_at_tol.setVisible(!check_length_l_at_tol.isSelected()); }

    public void setVisibleDiameterIT() {
        col_diameter_it.setVisible(!check_diameter_it.isSelected());
    }

    public void setVisibleDiameterITTOL() {
        col_diameter_it_tol.setVisible(!check_diameter_it_tol.isSelected());
    }

    public void setVisibleLengthLIT() {
        col_length_l_it.setVisible(!check_length_l_it.isSelected());
    }

    public void setVisibleLengthLITTOL() {
        col_length_l_it_tol.setVisible(!check_length_l_it_tol.isSelected());
    }

    public void setVisibleDiameterZT() {
        col_diameter_zt.setVisible(!check_diameter_zt.isSelected());
    }

    public void setVisibleDiameterZTTOL() {col_diameter_zt_tol.setVisible(!check_diameter_zt_tol.isSelected()); }

    public void setVisibleLengthLZT() {
        col_length_l_zt.setVisible(!check_length_l_zt.isSelected());
    }

    public void setVisibleLengthLZTTOL() {
        col_length_l_zt_tol.setVisible(!check_length_l_zt_tol.isSelected());
    }

    public void setVisibleCrSteg() {
        col_cr_steg.setVisible(!check_cr_steg.isSelected());
    }

    public void setVisibleCrNiere() {
        col_cr_niere.setVisible(!check_cr_niere.isSelected());
    }

    public void setVisibleCa() {
        col_ca.setVisible(!check_ca.isSelected());
    }

    public void setVisibleCt() {
        col_ct.setVisible(!check_ct.isSelected());
    }

    public void setVisibleCk() {
        col_ck.setVisible(!check_ck.isSelected());
    }

    @FXML
    public void onClickFilterByCategory() {
        FilterController.onClickFilterButton("/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchCategoryFilter.fxml",
                this,
                Constants.WINDOW_TITLE_HISTORY_CATEGORY_FILTER);
    }

    @FXML
    public void onClickFilterByPriority() {

        if (partsAfterFilter == null || partsAfterFilter.isEmpty()) {
            partsAfterFilter = SecondSearchManager.sortByPriority(resultOfFirstSearch, categories, SESSION.getCriteria());
        } else {
            partsAfterFilter = SecondSearchManager.sortByPriority(partsAfterFilter, categories, SESSION.getCriteria());
        }

        if (partsAfterFilter == null) {
            System.out.println("NULL");
            return;
        }

        refreshTable();
    }

    @FXML
    public void onClickFilterByRating() {
        if (partsAfterFilter == null || partsAfterFilter.isEmpty()) {
            partsAfterFilter = SecondSearchManager.sortByRating(resultOfFirstSearch, categories);
        } else {
            partsAfterFilter = SecondSearchManager.sortByRating(partsAfterFilter, categories);
        }

        refreshTable();
    }

    @Override
    public void setParameters(List<? extends Filterable> parameters, Class<?> type) {

        if (type.equals(Category.class)) {
            categories = parameters.stream().map(x -> (Category) x).collect(Collectors.toList());

            partsAfterFilter = SecondSearchManager.filterByCategories(resultOfFirstSearch, categories);
            refreshTable();
        }
    }

    @Override
    public void updateTable() {
        parts.retainAll();

        if (partsAfterFilter == null || partsAfterFilter.isEmpty()) {
            for (Part p: resultOfFirstSearch) {
                parts.add(new PartWrapper(p, this));
            }
            table_parts.setItems(parts);
        } else {
            refreshTable();
        }
    }

    @Override
    public List<? extends Filterable> getParameters(Class<?> type) {
        return null;
    }


    public void refreshTable(){

        parts.retainAll();

        for (Part p: partsAfterFilter) {
            parts.add(new PartWrapper(p, this));
        }

        table_parts.setItems(parts);
    }
}
