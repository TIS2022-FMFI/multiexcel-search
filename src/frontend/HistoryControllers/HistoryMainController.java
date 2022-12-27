package frontend.HistoryControllers;

import backend.Entities.Query;

import backend.Entities.User;
import backend.Managers.HistoryManager;
import javafx.collections.ObservableList;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.input.MouseEvent;
import javafx.util.Pair;

import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class HistoryMainController implements Initializable {

    @FXML
    public TableView<Query> table_queries;
    @FXML
    public TableColumn<Query, Integer> col_query_id;
    @FXML
    public TableColumn<Query, Integer> col_user_id;
    @FXML
    public TableColumn<Query, Integer> col_rubber;
    @FXML
    public TableColumn<Query, Double> col_diameter_at;
    @FXML
    public TableColumn<Query, Double> col_length_l_at;
    @FXML
    public TableColumn<Query, Double> col_diameter_it;
    @FXML
    public TableColumn<Query, Double> col_length_l_it;
    @FXML
    public TableColumn<Query, Double> col_diameter_zt;
    @FXML
    public TableColumn<Query, Double> col_length_l_zt;
    @FXML
    public TableColumn<Query, Integer> col_cr_steg;
    @FXML
    public TableColumn<Query, Integer> col_cr_niere;
    @FXML
    public TableColumn<Query, Integer> col_ca;
    @FXML
    public TableColumn<Query, Double> col_ct;
    @FXML
    public TableColumn<Query, Double> col_ck;
    @FXML
    public TableColumn<Query, Date> col_date;

    @FXML
    public Button button_previous_page;
    @FXML
    public Button button_next_page;
    @FXML
    public Label label_page_index;
    @FXML
    public DatePicker date_picker_to;
    @FXML
    public DatePicker date_picker_from;
    @FXML
    public Button button_filter;
    @FXML
    public Button button_date_from_clear;
    @FXML
    public Button button_date_to_clear;


    private ObservableList<Query> queries;
    private int currentPageIndex = 0;
    private int itemsPerPage = 10;
    private int currentSelectedIndex = -1;
    private int totalItemCount;
    private int maxPagesIndex;
    private List<User> users;
    private Pair<Date, Date> dateFromTo;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        initializeController();
    }

    private void initializeController(){
        col_query_id.setCellValueFactory(new PropertyValueFactory<>("query_id"));
        col_user_id.setCellValueFactory(new PropertyValueFactory<>("user_id"));

        /*col_rubber.setCellValueFactory(new PropertyValueFactory<>("rubber"));
        col_diameter_at.setCellValueFactory(new PropertyValueFactory<>("diameter_at"));
        col_length_l_at.setCellValueFactory(new PropertyValueFactory<>("length_l_at"));
        col_diameter_it.setCellValueFactory(new PropertyValueFactory<>("diameter_it"));
        col_length_l_it.setCellValueFactory(new PropertyValueFactory<>("length_l_it"));
        col_diameter_zt.setCellValueFactory(new PropertyValueFactory<>("diameter_zt"));
        col_length_l_zt.setCellValueFactory(new PropertyValueFactory<>("length_l_zt"));
        col_cr_steg.setCellValueFactory(new PropertyValueFactory<>("cr_steg"));
        col_cr_niere.setCellValueFactory(new PropertyValueFactory<>("cr_niere"));
        col_ca.setCellValueFactory(new PropertyValueFactory<>("ca"));
        col_ct.setCellValueFactory(new PropertyValueFactory<>("ct"));
        col_ck.setCellValueFactory(new PropertyValueFactory<>("ck"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));*/

        col_rubber.setCellValueFactory(new PropertyValueFactory<>("rubber_from"));
        col_diameter_at.setCellValueFactory(new PropertyValueFactory<>("diameter_AT_from"));
        col_length_l_at.setCellValueFactory(new PropertyValueFactory<>("length_L_AT_from"));
        col_diameter_it.setCellValueFactory(new PropertyValueFactory<>("diameter_IT_from"));
        col_length_l_it.setCellValueFactory(new PropertyValueFactory<>("length_L_IT_from"));
        col_diameter_zt.setCellValueFactory(new PropertyValueFactory<>("diameter_ZT_from"));
        col_length_l_zt.setCellValueFactory(new PropertyValueFactory<>("length_L_ZT_from"));
        col_cr_steg.setCellValueFactory(new PropertyValueFactory<>("cr_steg_from"));
        col_cr_niere.setCellValueFactory(new PropertyValueFactory<>("cr_niere_from"));
        col_ca.setCellValueFactory(new PropertyValueFactory<>("ca_from"));
        col_ct.setCellValueFactory(new PropertyValueFactory<>("ct_from"));
        col_ck.setCellValueFactory(new PropertyValueFactory<>("ck_from"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        resetFilters();
        updateTableContent();
        calculatePageIndexes();

        currentSelectedIndex = -1;
        updatePageButtonsDisabledStatus();

        updatePageTextLabel();

        System.out.println("Current page index: " + currentPageIndex + ". Max page index: " + maxPagesIndex);

        date_picker_from.setOnAction(event -> {
            if(date_picker_from.getValue() != null){
                System.out.printf("Selected from date: %s%n",Date.valueOf(date_picker_from.getValue()));
            }
        });

        date_picker_to.setOnAction(event -> {
            if(date_picker_to.getValue() != null){
                System.out.printf("Selected TO date: %s%n",Date.valueOf(date_picker_to.getValue()));
            }
        });

        button_filter.setOnAction(event -> {
            currentPageIndex = 0;
            updateQueryFilter();
            updateTableContent();
            calculatePageIndexes();
            updatePageButtonsDisabledStatus();
            System.out.println("Updated filters, page buttons and table");
        });
    }

    private void calculatePageIndexes(){
        totalItemCount = HistoryManager.getTotalNumberOfQueriesWithFilters(dateFromTo, users);
        maxPagesIndex = (int) Math.ceil((double)totalItemCount/itemsPerPage);
        if(maxPagesIndex == 0){
            currentPageIndex = -1;
        }
        updatePageTextLabel();
    }
    private void updatePageTextLabel(){
        label_page_index.setText(pageTextInfo());
    }

    private String pageTextInfo(){
        return String.format("Page %d / %d", currentPageIndex+1, maxPagesIndex);
    }

    private void updateTableContent(){
        currentSelectedIndex = -1;
        queries = HistoryManager.getQueriesWithFilters(dateFromTo, users, itemsPerPage, currentPageIndex);
        table_queries.setItems(queries);
        updatePageTextLabel();

        System.out.printf("Updated table content. Total rows: %d. Current Page: %d. Max page index: %d.%n", totalItemCount, currentPageIndex, maxPagesIndex);
    }

    private boolean isNextValidIndexForward(){
        return (currentPageIndex + 1 < maxPagesIndex);
    }


    private boolean isNextValidIndexBackward(){
        return (currentPageIndex - 1 >= 0);
    }

    private void resetFilters(){
        users = new ArrayList<>();
        dateFromTo = new Pair<>(new Date(0), new Date(System.currentTimeMillis()));
    }

    @FXML
    public void updateQueryFilter(){
        if(date_picker_to.getValue() != null && date_picker_from.getValue() != null){
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        }else if(date_picker_to.getValue() == null && date_picker_from.getValue() != null){
            date_picker_to.setValue(LocalDate.now());
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        }else if(date_picker_to.getValue() != null && date_picker_from.getValue() == null){
            date_picker_from.setValue(LocalDate.of(1970,1,1));
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        }else{
            dateFromTo = null;
            date_picker_from.setValue(null);
            date_picker_to.setValue(null);
        }
        //todo update user filter
    }

    private void updatePageButtonsDisabledStatus(){
        if(!isNextValidIndexForward()){
            button_next_page.setDisable(true);
        }else{
            if(button_next_page.isDisabled()){
                button_next_page.setDisable(false);
            }
        }

        if(!isNextValidIndexBackward()){
            button_previous_page.setDisable(true);
        }else{
            if(button_previous_page.isDisabled()){
                button_previous_page.setDisable(false);
            }
        }
    }

    @FXML
    public void getSelected(MouseEvent event){
        int selectedIndex = table_queries.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0 || selectedIndex >= queries.size()){
            currentSelectedIndex = -1;
            return;
        }
        currentSelectedIndex = selectedIndex;
        System.out.printf("Selceted index: %d. Selected Query id: %d%n", currentSelectedIndex, queries.get(currentSelectedIndex).getQuery_id());
    }

    @FXML
    public void onNextPageButtonClick(MouseEvent event){
        if(isNextValidIndexForward()){
            currentPageIndex++;
            System.out.printf("Moved from page %d to page %d.%n", currentPageIndex-1 ,currentPageIndex);
            updateTableContent();
            updatePageTextLabel();
        }else{
            System.out.println("No next page availible!");
        }
        updatePageButtonsDisabledStatus();
    }

    @FXML
    public void onPreviousPageButtonClick(MouseEvent event){
        if(isNextValidIndexBackward()){
            currentPageIndex--;
            System.out.printf("Moved from page %d to page %d.%n", currentPageIndex+1 ,currentPageIndex);
            updateTableContent();
            updatePageTextLabel();
        }else{
            System.out.println("No previous page availible!");
        }
        updatePageButtonsDisabledStatus();
    }

    @FXML
    public void onClearDateFromClick(MouseEvent event){
        dateFromTo = null;
        date_picker_from.setValue(null);
    }

    @FXML
    public void onClearDateToClick(MouseEvent event){
        dateFromTo = null;
        date_picker_to.setValue(null);
    }
}
