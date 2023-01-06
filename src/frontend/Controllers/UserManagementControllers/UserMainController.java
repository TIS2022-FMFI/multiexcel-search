package frontend.Controllers.UserManagementControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import frontend.CellClasses.UserCell;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
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
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/FXML/UserManagementFXML/UserAdd.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            UserAddController controller = loader.getController();
            controller.init(UserMainController.this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
