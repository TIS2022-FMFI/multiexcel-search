package frontend.Controllers.HistoryControllers;

import backend.Entities.Category;
import backend.Entities.Query;
import backend.Entities.User;
import backend.Managers.HistoryManager;
import backend.Models.Constants;
import backend.Models.Filterable;
import backend.Sessions.HistorySession;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.FilterController;
import frontend.Controllers.AbstractControllers.FilterMasterController;
import frontend.Controllers.AbstractControllers.MainController;
import frontend.Wrappers.QueryWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.util.Pair;

import java.math.BigInteger;
import java.net.URL;
import java.sql.Date;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.*;

public class HistoryMainController implements Initializable, FilterMasterController {

    private final int itemsPerPage = 10;
    private final List<Integer> currentSelectedIndexes = new ArrayList<>();
    @FXML
    public TableView<QueryWrapper> table_queries;
    @FXML
    public TableColumn<QueryWrapper, String> col_username;
    @FXML
    public TableColumn<QueryWrapper, String> col_categories;
    @FXML
    public TableColumn<QueryWrapper, String> col_rubber;
    @FXML
    public TableColumn<QueryWrapper, String> col_diameter_at;
    @FXML
    public TableColumn<QueryWrapper, String> col_length_l_at;
    @FXML
    public TableColumn<QueryWrapper, String> col_diameter_it;
    @FXML
    public TableColumn<QueryWrapper, String> col_length_l_it;
    @FXML
    public TableColumn<QueryWrapper, String> col_diameter_zt;
    @FXML
    public TableColumn<QueryWrapper, String> col_length_l_zt;
    @FXML
    public TableColumn<QueryWrapper, String> col_cr_steg;
    @FXML
    public TableColumn<QueryWrapper, String> col_cr_niere;
    @FXML
    public TableColumn<QueryWrapper, String> col_ca;
    @FXML
    public TableColumn<QueryWrapper, String> col_ct;
    @FXML
    public TableColumn<QueryWrapper, String> col_ck;
    @FXML
    public TableColumn<QueryWrapper, String> col_date;
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
    @FXML
    public Button button_user_filter;
    public Button button_category_filter;
    private boolean isAdmin;
    private ObservableList<QueryWrapper> queries;
    private int currentPageIndex = 0;
    private int currentSelectedIndex = -1;
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
        isAdmin = SESSION.getSession().getUser_name().equals("admin");

        if (!isAdmin) {
            col_username.setVisible(false);
            button_user_filter.setVisible(false);
        }
        historySession = HistorySession.getInstance();
        initializeController();
        setFilterStyle();
    }

    private void initializeController() {
        userIdToName = new HashMap<>();
        categoryIdToName = new HashMap<>();

        col_username.setCellValueFactory(new PropertyValueFactory<>("username"));
        col_categories.setCellValueFactory(new PropertyValueFactory<>("categories"));
        col_rubber.setCellValueFactory(new PropertyValueFactory<>("rubber"));
        col_diameter_at.setCellValueFactory(new PropertyValueFactory<>("diameter_AT"));
        col_length_l_at.setCellValueFactory(new PropertyValueFactory<>("length_L_AT"));
        col_diameter_it.setCellValueFactory(new PropertyValueFactory<>("diameter_IT"));
        col_length_l_it.setCellValueFactory(new PropertyValueFactory<>("length_L_IT"));
        col_diameter_zt.setCellValueFactory(new PropertyValueFactory<>("diameter_ZT"));
        col_length_l_zt.setCellValueFactory(new PropertyValueFactory<>("length_L_ZT"));
        col_cr_steg.setCellValueFactory(new PropertyValueFactory<>("cr_steg"));
        col_cr_niere.setCellValueFactory(new PropertyValueFactory<>("cr_niere"));
        col_ca.setCellValueFactory(new PropertyValueFactory<>("ca"));
        col_ct.setCellValueFactory(new PropertyValueFactory<>("ct"));
        col_ck.setCellValueFactory(new PropertyValueFactory<>("ck"));
        col_date.setCellValueFactory(new PropertyValueFactory<>("date"));
        table_queries.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
        queries = FXCollections.observableArrayList();
        resetFilters();

        loadFiltersAndSelectedQuery();

        refreshTable();

        System.out.println("Current page index: " + currentPageIndex + ". Max page index: " + maxPagesIndex);

        date_picker_from.setOnAction(event -> {
            if (date_picker_from.getValue() != null) {
                System.out.printf("Selected from date: %s%n", Date.valueOf(date_picker_from.getValue()));
                filter();
            }
        });

        date_picker_to.setOnAction(event -> {
            if (date_picker_to.getValue() != null) {
                System.out.printf("Selected TO date: %s%n", Date.valueOf(date_picker_to.getValue()));
                filter();
            }
        });

    }

    private void calculatePageIndexesAndUpdate() {
        totalItemCount = HistoryManager.getTotalNumberOfQueriesWithFilters(categories, dateFromTo, users);
        maxPagesIndex = (int) Math.ceil((double) totalItemCount / itemsPerPage);
        if (maxPagesIndex == 0) {
            currentPageIndex = -1;
        }
        updatePageTextLabel();
    }

    private void updatePageTextLabel() {
        label_page_index.setText(pageTextInfo());
    }

    private String pageTextInfo() {
        return String.format("Page %d / %d", currentPageIndex + 1, maxPagesIndex);
    }

    private void updateTableContent() {
        currentSelectedIndex = -1;
        currentSelectedIndexes.clear();
        updateDeleteButton();
        updateSelectButton();
        queries.clear();

        ObservableList<Query> result = HistoryManager.getQueriesWithFilters(categories, dateFromTo, users, itemsPerPage, currentPageIndex);
        if (result != null) {
            for (Query q : result) {
                queries.add(new QueryWrapper(q, userIdToName, categoryIdToName));
            }
        }

        table_queries.setItems(queries);

        System.out.printf("Updated table content. Total rows: %d. Current Page: %d. Max page index: %d.%n", totalItemCount, currentPageIndex, maxPagesIndex);
    }

    private boolean isNextValidIndexForward() {
        return (currentPageIndex + 1 < maxPagesIndex);
    }


    private boolean isNextValidIndexBackward() {
        return (currentPageIndex - 1 >= 0);
    }

    private void resetFilters() {
        categories = null;
        dateFromTo = null;

        if (!isAdmin) {
            if (users == null) {
                users = new ArrayList<>();
            }
            if (users.size() == 0) {
                users.add(SESSION.getSession());
            }
        } else {
            users = null;
        }
        setFilterStyle();
    }

    private void updateSelectButton() {
        button_open_selected.setDisable(!(currentSelectedIndex >= 0 && currentSelectedIndex < queries.size()));
    }

    private void saveCurrentFiltersAndSelectedQuery() {
        historySession.setSelectedQuery(queries.get(currentSelectedIndex).getQuery());
        historySession.setUsersFilter(users);
        historySession.setCategoriesFilter(categories);
        historySession.setDateFromToFilter(dateFromTo);
        historySession.setUserIdToName(userIdToName);
        historySession.setCurrentPageIndex(currentPageIndex);
        historySession.setCategoryIdToName(categoryIdToName);
        System.out.println("Filter data saved to HistorySession");
    }

    private void loadFiltersAndSelectedQuery() {
        if (historySession.getUsersFilter() != null) {
            users = historySession.getUsersFilter();
        }
        if (historySession.getCategoriesFilter() != null) {
            categories = historySession.getCategoriesFilter();
        }
        if (historySession.getDateFromToFilter() != null) {
            dateFromTo = historySession.getDateFromToFilter();
            date_picker_from.setValue(dateFromTo.getKey().toLocalDate());
            date_picker_to.setValue(dateFromTo.getValue().toLocalDate());
        }
        if (historySession.getUserIdToName() != null) {
            userIdToName = historySession.getUserIdToName();
        }
        if (historySession.getCurrentPageIndex() != null) {
            currentPageIndex = historySession.getCurrentPageIndex();
            ignorePageCalculation = true;
        }
        if (historySession.getCategoryIdToName() != null) {
            categoryIdToName = historySession.getCategoryIdToName();
        }
        System.out.println("Filter data loaded from HistorySession");
        historySession.removeSessionData();
    }

    private void filter() {
        currentPageIndex = 0;
        updateDateFilter();
        updateTableContent();
        calculatePageIndexesAndUpdate();
        updatePageButtonsDisabledStatus();
        System.out.println("Updated filters, page buttons and table");
    }

    @FXML
    public void updateDateFilter() {
        if (date_picker_to.getValue() != null && date_picker_from.getValue() != null) {
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        } else if (date_picker_to.getValue() == null && date_picker_from.getValue() != null) {
            date_picker_to.setValue(LocalDate.now());
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        } else if (date_picker_to.getValue() != null && date_picker_from.getValue() == null) {
            date_picker_from.setValue(LocalDate.of(2022, 1, 1));
            dateFromTo = new Pair<>(Date.valueOf(date_picker_from.getValue()), Date.valueOf(date_picker_to.getValue()));
        } else {
            dateFromTo = null;
            date_picker_from.setValue(null);
            date_picker_to.setValue(null);
        }
    }

    private void updatePageButtonsDisabledStatus() {
        if (!isNextValidIndexForward()) {
            button_next_page.setDisable(true);
        } else {
            if (button_next_page.isDisabled()) {
                button_next_page.setDisable(false);
            }
        }

        if (!isNextValidIndexBackward()) {
            button_previous_page.setDisable(true);
        } else {
            if (button_previous_page.isDisabled()) {
                button_previous_page.setDisable(false);
            }
        }
    }

    private void openHistoryDetailFXML() {
        MainController.switchTab("/frontend/FXML/HistoryFXML/HistoryDetails.fxml", SESSION.getHistoryTab());
    }

    private void updateDeleteButton() {
        button_delte_selected.setDisable(currentSelectedIndexes.size() <= 0);
    }

    @FXML
    public void getSelected() {
        currentSelectedIndexes.clear();
        ObservableList<Integer> selectedIndexes = table_queries.getSelectionModel().getSelectedIndices();
        if (selectedIndexes.size() == 1) {
            currentSelectedIndex = selectedIndexes.get(0);
            updateSelectButton();
            System.out.printf("Selected index: %d. Selected Query id: %d%n", currentSelectedIndex, queries.get(currentSelectedIndex).getQuery().getQuery_id());
        } else {
            currentSelectedIndex = -1;
            updateSelectButton();
        }
        currentSelectedIndexes.addAll(selectedIndexes);
        updateDeleteButton();
        System.out.printf("Selected indexes size %d. Selected indexes: %s%n", selectedIndexes.size(), selectedIndexes);
    }

    @FXML
    public void onNextPageButtonClick() {
        if (isNextValidIndexForward()) {
            currentPageIndex++;
            System.out.printf("Moved from page %d to page %d.%n", currentPageIndex - 1, currentPageIndex);
            updateTableContent();
            updatePageTextLabel();
        } else {
            System.out.println("No next page availible!");
        }
        updatePageButtonsDisabledStatus();
    }

    @FXML
    public void onPreviousPageButtonClick() {
        if (isNextValidIndexBackward()) {
            currentPageIndex--;
            System.out.printf("Moved from page %d to page %d.%n", currentPageIndex + 1, currentPageIndex);
            updateTableContent();
            updatePageTextLabel();
        } else {
            System.out.println("No previous page availible!");
        }
        updatePageButtonsDisabledStatus();
    }

    @FXML
    public void onClearDateFromClick() {
        dateFromTo = null;
        date_picker_from.setValue(null);
        refreshTable();
    }

    @FXML
    public void onClearDateToClick() {
        dateFromTo = null;
        date_picker_to.setValue(null);
        refreshTable();
    }

    @FXML
    public void onClickCategoryFilterButton() {
        FilterController.onClickFilterButton("/frontend/FXML/HistoryFXML/HistoryCategoryFilter.fxml",
                this,
                Constants.WINDOW_TITLE_HISTORY_CATEGORY_FILTER);
    }

    @FXML
    public void onClickUserFilterButton() {
        FilterController.onClickFilterButton("/frontend/FXML/HistoryFXML/HistoryUserFilter.fxml",
                this,
                Constants.WINDOW_TITLE_HISTORY_USER_FILTER);
    }

    @FXML
    public void refreshTable() {
        if (!ignorePageCalculation) {
            currentPageIndex = 0;
        } else {
            ignorePageCalculation = false;
        }
        updateTableContent();
        calculatePageIndexesAndUpdate();
        updatePageButtonsDisabledStatus();
        System.out.println("Performed table refresh!");
    }

    @FXML
    public void clearAllFiltersAndRefreshTable() {
        resetFilters();
        date_picker_from.setValue(null);
        date_picker_to.setValue(null);
        System.out.println("Filters cleared!");
        refreshTable();
    }

    @FXML
    public void openSelectedQuery() {
        System.out.printf("Opening query id: %d.%n", queries.get(currentSelectedIndex).getQuery().getQuery_id());
        saveCurrentFiltersAndSelectedQuery();
        openHistoryDetailFXML();
    }

    @FXML
    public void deleteSelectedQueries() {
        HistoryDeleteController historyDeleteController = MainController.setNewStage("/frontend/FXML/HistoryFXML/HistoryDelete.fxml", Constants.WINDOW_TITLE_HISTORY_DELETE);
        historyDeleteController.setHistoryMainController(this);
    }

    public void deleteSelectionConfirmed() {
        try {
            for (int i = 0; i < currentSelectedIndexes.size(); i++) {
                queries.get(i).getQuery().delete();
            }
        } catch (SQLException sqlException) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", sqlException.toString());
            System.err.printf("Error occured during selection removeal in HistoryMainController::deleteSelectionConfirmed()%n.Tried deleting items from: %s", queries);
        }
    }

    private void setFilterStyle() {
        setStyleBasedOnParameters(users, button_user_filter);
        setStyleBasedOnParameters(categories, button_category_filter);
    }

    @Override
    public void setParameters(List<? extends Filterable> parameters, Class<?> type) {
        if (type.equals(User.class))
            users = getConcreteParametersAndSetStyle(parameters, button_user_filter);
         else if (type.equals(Category.class))
            categories = getConcreteParametersAndSetStyle(parameters, button_category_filter);

        refreshTable();
    }

    @Override
    public void updateTable() {
    }

    @Override
    public List<? extends Filterable> getParameters(Class<?> type) {
        if (type.equals(User.class))
            return users;
        if (type.equals(Category.class))
            return categories;
        return null;
    }
}
