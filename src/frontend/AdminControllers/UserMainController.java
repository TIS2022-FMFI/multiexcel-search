package frontend.AdminControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class UserMainController implements Initializable {

    @FXML
    public ListView<User> userList;

    /**
     * Inner class used as Cell in ListView consists of  username and button to suspend or enable user
     */
    class Cell extends ListCell<User> {
        HBox hbox = new HBox();
        Button button = new Button();
        Label label = new Label();
        Pane pane = new Pane();

        /**
         * constructor
         */
        public Cell(){
            super();

            hbox.getChildren().addAll(label, pane, button);
            HBox.setHgrow(pane, Priority.ALWAYS);
        }

        /**
         * This method is override and called on update of cell.
         * @param user - list item.
         * @param empty - empty.
         */
        @Override
        public void updateItem(User user, boolean empty){
            super.updateItem(user, empty);
            setText(null);
            setGraphic(null);

            if(user != null && !empty){
                label.setText(user.getUser_name());
                setGraphic(hbox);
                if(Objects.equals(user.getUser_name(), "admin")){
                    button.setVisible(false);
                    return;
                }
                if(user.getSuspended()){
                    button.setText("Enable");
                    button.setOnAction(x -> {
                        if(!UserManager.enableAccount(user)){
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText("Failure");
                            errorAlert.setContentText("Enabling user has failed");
                            errorAlert.showAndWait();
                            return;
                        }
                        updateList();
                    });
                }
                else{
                    button.setText("Suspend");
                    button.setOnAction(x -> {
                        if(!UserManager.suspendAccount(user)){
                            Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                            errorAlert.setHeaderText("Failure");
                            errorAlert.setContentText("Suspending user has failed");
                            errorAlert.showAndWait();
                            return;
                        }
                        updateList();
                    });
                }
            }
        }
    }

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
    public void updateList(){
        ObservableList<User> users = UserManager.getUsers(false);
        if(users == null)
            return;
        userList.setItems(users);
        userList.setCellFactory(x -> new Cell());
    }

    /**
     * Opens new window for adding new user
     */
    @FXML
    public void addUser() {
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/AdminFXML/UserAdd.fxml";
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
