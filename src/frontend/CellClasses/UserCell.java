package frontend.CellClasses;

import backend.Entities.User;
import backend.Managers.UserManager;
import frontend.Controllers.UserManagementControllers.UserMainController;
import javafx.scene.control.*;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Priority;

import java.util.Objects;

/**
 * Class used as Cell in ListView consists of  username and button to suspend or enable user
 */
public class UserCell extends ListCell<User> {
    HBox hbox = new HBox();
    Button button = new Button();
    Label label = new Label();
    Pane pane = new Pane();

    UserMainController userMainController;

    /**
     * constructor
     */
    public UserCell(UserMainController userMainController) {
        super();

        hbox.getChildren().addAll(label, pane, button);
        HBox.setHgrow(pane, Priority.ALWAYS);
        this.userMainController = userMainController;
    }

    /**
     * This method is override and called on update of cell.
     *
     * @param user  - list item.
     * @param empty - empty.
     */
    @Override
    public void updateItem(User user, boolean empty) {
        super.updateItem(user, empty);
        setText(null);
        setGraphic(null);

        if (user != null && !empty) {
            label.setText(user.getUser_name());
            setGraphic(hbox);
            if (Objects.equals(user.getUser_name(), "admin")) {
                button.setVisible(false);
                return;
            }
            if (user.getSuspended()) {

                ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/confirmImage.png")).toExternalForm());
                button.setGraphic(imageView);
                button.setTooltip(new Tooltip("Enable user"));

                button.setOnAction(x -> {
                    if (!UserManager.enableAccount(user)) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Failure");
                        errorAlert.setContentText("Enabling user has failed");
                        errorAlert.showAndWait();
                        return;
                    }
                    userMainController.updateList();
                });
            } else {

                ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/blockImage.png")).toExternalForm());
                button.setGraphic(imageView);
                button.setTooltip(new Tooltip("Suspend user"));

                button.setOnAction(x -> {
                    if (!UserManager.suspendAccount(user)) {
                        Alert errorAlert = new Alert(Alert.AlertType.ERROR);
                        errorAlert.setHeaderText("Failure");
                        errorAlert.setContentText("Suspending user has failed");
                        errorAlert.showAndWait();
                        return;
                    }
                    userMainController.updateList();
                });
            }
        }
    }
}
