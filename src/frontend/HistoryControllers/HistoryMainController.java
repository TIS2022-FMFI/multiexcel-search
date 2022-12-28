package frontend.HistoryControllers;

import backend.Entities.Category;
import backend.Entities.HistorySession;
import backend.Entities.Query;

import backend.Entities.User;
import backend.Managers.HistoryManager;
import backend.Managers.UserManager;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.sql.Date;
import java.time.LocalDate;
import java.util.*;

//DONE: add user filter button/window
//DONE: SELECT (pre category filter)
//DONE: Add category filter button/window

//DONE: add refresh button
//DONE: Add clear all filters button
//todo: resize columns
//DONE: polozky od do napis v jednom stlpci
//DONE: pridaj tam username a nezobrazuj query id ani user id (to su len interne)
public class HistoryMainController implements Initializable {

    @FXML
    public TableView<Query> table_queries;
    //@FXML
    //public TableColumn<Query, Integer> col_query_id;
    @FXML
    public TableColumn<Query, String> col_username;
    @FXML
    public TableColumn<Query, String> col_rubber;
    @FXML
    public TableColumn<Query, String> col_diameter_at;
    @FXML
    public TableColumn<Query, String> col_length_l_at;
    @FXML
    public TableColumn<Query, String> col_diameter_it;
    @FXML
    public TableColumn<Query, String> col_length_l_it;
    @FXML
    public TableColumn<Query, String> col_diameter_zt;
    @FXML
    public TableColumn<Query, String> col_length_l_zt;
    @FXML
    public TableColumn<Query, String> col_cr_steg;
    @FXML
    public TableColumn<Query, String> col_cr_niere;
    @FXML
    public TableColumn<Query, String> col_ca;
    @FXML
    public TableColumn<Query, String> col_ct;
    @FXML
    public TableColumn<Query, String> col_ck;
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
    @FXML
    public Button button_open_selected;


    private ObservableList<Query> queries;
    private int currentPageIndex = 0;
    private int itemsPerPage = 10;
    private int currentSelectedIndex = -1;
    private int totalItemCount;
    private int maxPagesIndex;
    private List<User> users;
    private List<Category> categories;
    private Pair<Date, Date> dateFromTo;
    private Map<Integer, String> userIdToName;

    private HistorySession historySession;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historySession = HistorySession.getInstance();
        initializeController();
    }

    private void initializeController(){
        userIdToName = new HashMap<>();

        //col_query_id.setCellValueFactory(new PropertyValueFactory<>("query_id"));
        col_username.setCellValueFactory(feature -> createUsernameWrapperFromUserID(feature.getValue().getUser_id()));
        col_rubber.setCellValueFactory( featrue -> createFromToWrapper(featrue.getValue().getRubber_from(), featrue.getValue().getRubber_to()));
        col_diameter_at.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getDiameter_AT_from(), featrue.getValue().getDiameter_AT_to()));
        col_length_l_at.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getLength_L_AT_from(), featrue.getValue().getLength_L_AT_to()));
        col_diameter_it.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getDiameter_IT_from(), featrue.getValue().getDiameter_IT_to()));
        col_length_l_it.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getLength_L_IT_from(), featrue.getValue().getLength_L_IT_to()));
        col_diameter_zt.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getDiameter_ZT_from(), featrue.getValue().getDiameter_ZT_to()));
        col_length_l_zt.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getLength_L_ZT_from(), featrue.getValue().getLength_L_ZT_to()));
        col_cr_steg.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getCr_steg_from(), featrue.getValue().getCr_steg_to()));
        col_cr_niere.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getCr_niere_from(), featrue.getValue().getCr_niere_to()));
        col_ca.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getCa_from(), featrue.getValue().getCa_to()));
        col_ct.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getCt_from(), featrue.getValue().getCt_to()));
        col_ck.setCellValueFactory(featrue -> createFromToWrapper(featrue.getValue().getCk_from(), featrue.getValue().getCk_to()));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));

        queries = FXCollections.observableArrayList();
        resetFilters();
        
        refreshTable();

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
            calculatePageIndexesAndUpdate();
            updatePageButtonsDisabledStatus();
            System.out.println("Updated filters, page buttons and table");
        });
    }

    private ReadOnlyStringWrapper createUsernameWrapperFromUserID(int userId){
        if(!userIdToName.containsKey(userId)){
            User user = UserManager.getUserById(userId);
            if(user == null){
                return new ReadOnlyStringWrapper("not existing user");
            }
            userIdToName.put(userId, user.getUser_name());
        }

        return new ReadOnlyStringWrapper(userIdToName.get(userId));
    }

    private <K, V>ReadOnlyStringWrapper createFromToWrapper(K first, V second){
        if(first == null || second == null){
            return new ReadOnlyStringWrapper("null in arguments");
        }
        return new ReadOnlyStringWrapper(String.format("%s - %s", first, second));
    }

    private void calculatePageIndexesAndUpdate(){
        totalItemCount = HistoryManager.getTotalNumberOfQueriesWithFilters(categories, dateFromTo, users);
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
        updateSelectButton();
        queries.clear();

        ObservableList<Query> result = HistoryManager.getQueriesWithFilters(categories, dateFromTo, users, itemsPerPage, currentPageIndex);
        if(result != null){
            queries.addAll(result);
        }

        table_queries.setItems(queries);

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
        categories = new ArrayList<>();
        dateFromTo = new Pair<>(new Date(0), new Date(System.currentTimeMillis()));
    }

    private void updateSelectButton(){
        button_open_selected.setDisable(!(currentSelectedIndex >= 0 && currentSelectedIndex < queries.size()));
    }

    private void saveCurrentFiltersAndSelectedQuery(){
        historySession.setSelectedQuery(queries.get(currentSelectedIndex));
        historySession.setUsersFilter(users);
        historySession.setCategoriesFilter(categories);
        historySession.setDateFromToFilter(dateFromTo);
        historySession.setUserIdToName(userIdToName);
        System.out.println("Filter data saved to HistorySession");
    }

    private void loadFiltersAndSelectedQuery(){
        if(historySession.getUsersFilter() != null){
            users = historySession.getUsersFilter();
        }
        if(historySession.getCategoriesFilter() != null){
            categories = historySession.getCategoriesFilter();
        }
        if(historySession.getDateFromToFilter() != null){
            dateFromTo = historySession.getDateFromToFilter();
        }
        if(historySession.getUserIdToName() != null){
            userIdToName = historySession.getUserIdToName();
        }
        System.out.println("Filter data loaded from HistorySession");
    }

    @FXML
    public void updateQueryFilter(){
        if(date_picker_to.getValue() != null && date_picker_from.getValue() != null){
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        }else if(date_picker_to.getValue() == null && date_picker_from.getValue() != null){
            date_picker_to.setValue(LocalDate.now());
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        }else if(date_picker_to.getValue() != null && date_picker_from.getValue() == null){
            date_picker_from.setValue(LocalDate.of(2022,1,1));
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        }else{
            dateFromTo = null;
            date_picker_from.setValue(null);
            date_picker_to.setValue(null);
        }
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
    public void getSelected(){
        int selectedIndex = table_queries.getSelectionModel().getSelectedIndex();
        if(selectedIndex < 0 || selectedIndex >= queries.size()){
            currentSelectedIndex = -1;
            return;
        }
        currentSelectedIndex = selectedIndex;
        updateSelectButton();
        System.out.printf("Selceted index: %d. Selected Query id: %d%n", currentSelectedIndex, queries.get(currentSelectedIndex).getQuery_id());
    }

    @FXML
    public void onNextPageButtonClick(){
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
    public void onPreviousPageButtonClick(){
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
    public void onClearDateFromClick(){
        dateFromTo = null;
        date_picker_from.setValue(null);
    }

    @FXML
    public void onClearDateToClick(){
        dateFromTo = null;
        date_picker_to.setValue(null);
    }

    @FXML
    public void onClickCategoryFilterButton(){
        try{
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/CategoryFilter.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            CategoryFilterController categoryFilterController = loader.getController();
            categoryFilterController.setHistoryMainController(this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Filter by users");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @FXML
    public void onClickUserFilterButton(){
        try{
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/UserFilter.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            UserFilterController userFilterController = loader.getController();
            userFilterController.setHistoryMainController(this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Filter by users");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    
    @FXML
    public void refreshTable(){
        currentPageIndex = 0;
        updateTableContent();
        calculatePageIndexesAndUpdate();
        updatePageButtonsDisabledStatus();
        System.out.println("Performed table refresh!");
    }

    @FXML
    public void clearAllFiltersAndRefreshTable(){
        resetFilters();
        date_picker_from.setValue(null);
        date_picker_to.setValue(null);
        System.out.println("Filters cleared!");
        refreshTable();
    }

    @FXML
    public void openSelectedQuery(){
        System.out.printf("Opening query id: %d.%n", queries.get(currentSelectedIndex).getQuery_id());
        saveCurrentFiltersAndSelectedQuery();
    }

    public void setUserFilter(List<User> users){
        this.users.clear();
        this.users.addAll(users);
    }

    public List<User> getUserFilter(){
        return Collections.unmodifiableList(users);
    }

    public void setCategoryFilter(List<Category> categories){
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public List<Category> getCategoryFilter(){
        return Collections.unmodifiableList(categories);
    }
}
