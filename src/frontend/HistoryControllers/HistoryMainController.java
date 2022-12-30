package frontend.HistoryControllers;

import backend.Entities.*;

import backend.Managers.CategoryManager;
import backend.Managers.HistoryManager;
import backend.Managers.PartManager;
import backend.Managers.UserManager;
import backend.Sessions.HistorySession;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.fxml.FXML;
import javafx.scene.control.cell.PropertyValueFactory;

import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.util.Pair;

import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

public class HistoryMainController implements Initializable {

    @FXML
    public TableView<Query> table_queries;
    @FXML
    public TableColumn<Query, String> col_username;
    @FXML
    public TableColumn<Query, String> col_categories;
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
    public Button button_date_from_clear;
    @FXML
    public Button button_date_to_clear;
    @FXML
    public Button button_open_selected;
    @FXML
    public Button button_delte_selected;


    private ObservableList<Query> queries;
    private int currentPageIndex = 0;
    private int itemsPerPage = 10;
    private int currentSelectedIndex = -1;
    private List<Integer> currentSelectedIndexes = new ArrayList<>();
    private int totalItemCount;
    private int maxPagesIndex;
    private List<User> users;
    private List<Category> categories;
    private Pair<Date, Date> dateFromTo;
    private Map<Integer, String> userIdToName;
    private Map<BigInteger, String> categoryIdToName;

    private HistorySession historySession;
    private boolean ignorePageCalculation = false;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        historySession = HistorySession.getInstance();
        initializeController();
    }

    private void initializeController(){
        userIdToName = new HashMap<>();
        categoryIdToName = new HashMap<>();

        col_username.setCellValueFactory(feature -> createUsernameWrapperFromUserID(feature.getValue().getUser_id()));
        col_categories.setCellValueFactory(feature -> createCategoriesWrapper(feature.getValue().getQuery_id()));
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
        table_queries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        queries = FXCollections.observableArrayList();
        resetFilters();

        loadFiltersAndSelectedQuery();

        refreshTable();

        System.out.println("Current page index: " + currentPageIndex + ". Max page index: " + maxPagesIndex);

        date_picker_from.setOnAction(event -> {
            if(date_picker_from.getValue() != null){
                System.out.printf("Selected from date: %s%n",Date.valueOf(date_picker_from.getValue()));
                filter();
            }
        });

        date_picker_to.setOnAction(event -> {
            if(date_picker_to.getValue() != null){
                System.out.printf("Selected TO date: %s%n",Date.valueOf(date_picker_to.getValue()));
                filter();
            }
        });

    }

    private ReadOnlyStringWrapper createCategoriesWrapper(int queryId){
        List<Part> parts = PartManager.GetPartsByQueryId(queryId);

        if(parts == null){
            return new ReadOnlyStringWrapper("<none>");
        }

        /*if(parts.size() == 0){
            return new ReadOnlyStringWrapper("<none>");
        }*/

        HashSet<BigInteger> categoryIds = new HashSet<>();
        for (Part p : parts) {
            categoryIds.add(p.getCategory_id());
        }
        List<String> displayedCategories = new ArrayList<>();

        for (BigInteger b: categoryIds) {
            if(!categoryIdToName.containsKey(b)){
                Category category = CategoryManager.getCategory(b);
                if(category == null){
                    continue;
                }
                categoryIdToName.put(b, category.getCategory_name());
            }
            displayedCategories.add(categoryIdToName.get(b));
        }
        return new ReadOnlyStringWrapper(String.join(", ", displayedCategories));
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
        currentSelectedIndexes.clear();
        updateDeleteButton();
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
        users = null;
        categories = null;
        dateFromTo = null;
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
        historySession.setCurrentPageIndex(currentPageIndex);
        historySession.setCategoryIdToName(categoryIdToName);
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
            date_picker_from.setValue(dateFromTo.getKey().toLocalDate());
            date_picker_to.setValue(dateFromTo.getValue().toLocalDate());
        }
        if(historySession.getUserIdToName() != null){
            userIdToName = historySession.getUserIdToName();
        }
        if(historySession.getCurrentPageIndex() != null){
            currentPageIndex = historySession.getCurrentPageIndex();
            ignorePageCalculation = true;
        }
        if(historySession.getCategoryIdToName() != null){
            categoryIdToName = historySession.getCategoryIdToName();
        }
        System.out.println("Filter data loaded from HistorySession");
        historySession.removeSessionData();
    }
    
    private void filter(){
        currentPageIndex = 0;
        updateDateFilter();
        updateTableContent();
        calculatePageIndexesAndUpdate();
        updatePageButtonsDisabledStatus();
        System.out.println("Updated filters, page buttons and table");
    }

    @FXML
    public void updateDateFilter(){
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
    private void openHistoryDetailFXML(ActionEvent actionEvent){
        try{
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/HistoryDetails.fxml";
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

    private void updateDeleteButton(){
        if(currentSelectedIndexes.size() > 0){
            button_delte_selected.setDisable(false);
        }else{
            button_delte_selected.setDisable(true);
        }
    }

    @FXML
    public void getSelected(){
        currentSelectedIndexes.clear();
        ObservableList<Integer> selectedIndexes = table_queries.getSelectionModel().getSelectedIndices();
        if(selectedIndexes.size() == 1){
            currentSelectedIndex = selectedIndexes.get(0);
            updateSelectButton();
            System.out.printf("Selected index: %d. Selected Query id: %d%n", currentSelectedIndex, queries.get(currentSelectedIndex).getQuery_id());
        }else{
            currentSelectedIndex = -1;
            updateSelectButton();
        }
        currentSelectedIndexes.addAll(selectedIndexes);
        updateDeleteButton();
        System.out.printf("Selected indexes size %d. Selected indexes: %s%n",selectedIndexes.size(),selectedIndexes);
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
        refreshTable();
    }

    @FXML
    public void onClearDateToClick(){
        dateFromTo = null;
        date_picker_to.setValue(null);
        refreshTable();
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
            stage.setTitle("Filter by category");
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
        if(!ignorePageCalculation){
            currentPageIndex = 0;
        }else{
            ignorePageCalculation = false;
        }
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
    public void openSelectedQuery(ActionEvent actionEvent){
        System.out.printf("Opening query id: %d.%n", queries.get(currentSelectedIndex).getQuery_id());
        saveCurrentFiltersAndSelectedQuery();
        openHistoryDetailFXML(actionEvent);
    }

    @FXML
    public void deleteSelectedQueries(){
        try{
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/BasicFXML/HistoryDelete.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            HistoryDeleteController historyDeleteController = loader.getController();
            historyDeleteController.setHistoryMainController(this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.setTitle("Delete selection");
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteSelectionConfirmed(){
        try{
            for (int i = 0; i < currentSelectedIndexes.size(); i++) {
                queries.get(i).delete();
            }
        }catch (SQLException sqlException){
            System.err.printf("Error occured during selection removeal in HistoryMainController::deleteSelectionConfirmed()%n.Tried deleting items from: %s", queries);
        }
    }

    public void setUserFilter(List<User> users){
        if(this.users == null){
            this.users = new ArrayList<>();
        }
        this.users.clear();
        this.users.addAll(users);
    }

    public List<User> getUserFilter(){
        if(users == null){
            return null;
        }
        return Collections.unmodifiableList(users);
    }

    public void setCategoryFilter(List<Category> categories){
        if(this.categories == null){
            this.categories = new ArrayList<>();
        }
        this.categories.clear();
        this.categories.addAll(categories);
    }

    public List<Category> getCategoryFilter(){
        if(categories == null){
            return null;
        }
        return Collections.unmodifiableList(categories);
    }
}
