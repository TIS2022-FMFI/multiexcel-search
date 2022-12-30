package backend.Sessions;

import backend.Entities.Category;
import backend.Entities.Query;
import backend.Entities.User;
import javafx.util.Pair;

import java.math.BigInteger;
import java.sql.Date;
import java.util.List;
import java.util.Map;

public class HistorySession {

    public static HistorySession instance;

    private List<User> usersFilter;
    private List<Category> categoriesFilter;
    private Pair<Date, Date> dateFromToFilter;
    private Map<Integer, String> userIdToName;
    private Map<BigInteger, String> categoryIdToName;
    private Query selectedQuery;
    private Integer currentPageIndex;



    public static HistorySession getInstance(){
        if (instance == null){
            instance = new HistorySession();
        }
        return instance;
    }

    private HistorySession(){
    }

    public void removeSessionData(){
        usersFilter = null;
        categoriesFilter = null;
        dateFromToFilter = null;
        userIdToName = null;
        selectedQuery = null;
        currentPageIndex = null;
        categoryIdToName = null;
    }

    public List<User> getUsersFilter() {
        return usersFilter;
    }

    public void setUsersFilter(List<User> usersFilter) {
        this.usersFilter = usersFilter;
    }

    public List<Category> getCategoriesFilter() {
        return categoriesFilter;
    }

    public void setCategoriesFilter(List<Category> categoriesFilter) {
        this.categoriesFilter = categoriesFilter;
    }

    public Pair<Date, Date> getDateFromToFilter() {
        return dateFromToFilter;
    }

    public void setDateFromToFilter(Pair<Date, Date> dateFromToFilter) {
        this.dateFromToFilter = dateFromToFilter;
    }

    public Map<Integer, String> getUserIdToName() {
        return userIdToName;
    }

    public void setUserIdToName(Map<Integer, String> userIdToName) {
        this.userIdToName = userIdToName;
    }

    public Query getSelectedQuery() {
        return selectedQuery;
    }

    public void setSelectedQuery(Query selectedQuery) {
        this.selectedQuery = selectedQuery;
    }

    public Integer getCurrentPageIndex(){
        return currentPageIndex;
    }
    public void setCurrentPageIndex(int currentPageIndex){
        this.currentPageIndex = currentPageIndex;
    }

    public void setCategoryIdToName(Map<BigInteger, String> categoryIdToName)
    {
        this.categoryIdToName = categoryIdToName;
    }

    public Map<BigInteger, String> getCategoryIdToName(){
        return categoryIdToName;
    }

}
