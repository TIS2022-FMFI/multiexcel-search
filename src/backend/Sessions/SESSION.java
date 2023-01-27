package backend.Sessions;

import backend.Entities.User;
import backend.Models.Constants;
import backend.Models.Criteria;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.event.ActionEvent;
import javafx.scene.control.Tab;

public class SESSION {

    private static User user;

    private static Criteria criteria;

    private static Tab historyTab;

    private static Tab searchTab;

    private static Tab categoryTab;

    //add other Tabs also here

    public static User getSession() {
        if (user == null) {
            throw new IllegalStateException("User must be set before calling this method");
        }
        return user;
    }

    public static void setSession(User user) {
        if (user == null) {
            throw new NullPointerException("User cannot be null");
        }
        SESSION.user = user;
    }

    public static void clearUser() {
        user = null;
    }

    public static Tab getHistoryTab() {
        if (historyTab == null) {
            throw new IllegalStateException("historyTab == null");
        }
        return historyTab;
    }

    public static void setHistoryTab(Tab historyTab) {
        SESSION.historyTab = historyTab;
    }

    public static boolean isAdmin() {
        return user.getUser_name().equals("admin");
    }

    public static Tab getSearchTab() {
        if (searchTab == null) {
            throw new IllegalStateException("searchTab == null");
        }
        return searchTab;
    }

    public static void setSearchTab(Tab searchTab) {
        SESSION.searchTab = searchTab;
    }

    public static Tab getCategoryTab() {
        if (categoryTab == null) {
            throw new IllegalStateException("categoryTab == null");
        }
        return categoryTab;
    }

    public static void setCategoryTab(Tab categoryTab) {
        SESSION.categoryTab = categoryTab;
    }

    public static Criteria getCriteria() {
        if (criteria == null)
            criteria = new Criteria();
        return criteria;
    }

    public static void clearCriteria() {
        criteria = null;
    }

    public static void logout(ActionEvent event) {
        MainController.replaceStageByEvent("/frontend/FXML/LoginScreen.fxml", Constants.WINDOW_TITLE_LOGIN, event, true);
        clearUser();
        clearCriteria();
    }

}