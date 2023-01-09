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

    public static void clear() {
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


    public static Tab getSearchTab() {
        if (searchTab == null) {
            throw new IllegalStateException("searchTab == null");
        }
        return searchTab;
    }

    public static void setSearchTab(Tab searchTab) {
        SESSION.searchTab = searchTab;
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
        MainController.replaceStageByEvent("./src/frontend/FXML/loginScreen.fxml", Constants.WINDOW_TITLE_LOGIN, event);
        clear();
    }

}