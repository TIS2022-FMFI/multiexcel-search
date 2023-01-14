package frontend.Controllers.UserManagementControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import backend.Models.Constants;
import frontend.CellClasses.UserCell;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ListView;

import java.net.URL;
import java.util.ResourceBundle;

public class UserMainController implements Initializable {

    @FXML
    public ListView<User> enabledUserList;
    public ListView<User> suspendedUserList;

    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        updateList();
    }

    /**
     * Updates users ListView from database
     */
    public void updateList() {
        ObservableList<User> enabledUsers = UserManager.getUsers(true);
        ObservableList<User> suspendedUsers = UserManager.getSuspendedUsers();

        if (enabledUsers != null) {
            enabledUserList.setItems(enabledUsers);
            enabledUserList.setCellFactory(x -> new UserCell(this));
        }
        if (suspendedUsers != null) {
            suspendedUserList.setItems(suspendedUsers);
            suspendedUserList.setCellFactory(x -> new UserCell(this));
        }
    }

    /**
     * Opens new window for adding new user
     */
    @FXML
    public void addUser() {
        String fxmlDocPath = "/frontend/FXML/UserManagementFXML/UserAdd.fxml";
        UserAddController controller = MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_USER_MANAGMENT_ADD);
        controller.init(UserMainController.this);

    }
}
