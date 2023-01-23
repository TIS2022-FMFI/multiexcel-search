package frontend.Controllers.AbstractControllers;

import backend.Models.Constants;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Tab;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class MainController {

    public static void setTab(String path, Tab tab) throws IOException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(Class.class.getResource(path)));
        tab.setContent(root);
    }

    public static <T> T setNewStage(String fxmlDocPath, String stageTitle) {
        try {
            FXMLLoader loader = new FXMLLoader(Objects.requireNonNull(Class.class.getResource(fxmlDocPath)));
            AnchorPane root = loader.load();

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setTitle(stageTitle);
            stage.setScene(scene);
            stage.show();
            return loader.getController();
        } catch (IOException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
            return null;
        }
    }

    public static void switchTab(String fxmlDocPath, Tab tab) {
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(Class.class.getResource(fxmlDocPath)));

            tab.setContent(root);

        } catch (IOException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
        }
    }

    public static void showAlert(Alert.AlertType type, String title, String text) {
        Alert alert = new Alert(type);
        alert.setTitle(title);
        alert.setContentText(text);
        alert.showAndWait();
    }

    public static void replaceStageByEvent(String fxmlDocPath, String stageTitle, ActionEvent event) {
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(Class.class.getResource(fxmlDocPath)));

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (!stageTitle.equals("")) {
                stage.setTitle(stageTitle);
            }
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
        }
    }

    public static void replaceStageByEvent(String fxmlDocPath, String stageTitle, ActionEvent event, boolean login) {
        try {
            AnchorPane root = FXMLLoader.load(Objects.requireNonNull(Class.class.getResource(fxmlDocPath)));

            Scene scene = new Scene(root);
            Stage stage = (Stage) ((Node) event.getSource()).getScene().getWindow();
            if (!stageTitle.equals("")) {
                stage.setTitle(stageTitle);
            }
            stage.setResizable(!login);
            stage.setScene(scene);
            stage.show();
            if(login){
                clearMinMaxWH(stage);
                stage.setMaximized(false);
                setMaxMinCurWH(stage, Constants.WINDOW_LOGIN_WIDTH, Constants.WINDOW_LOGIN_HEIGHT);
            }else{
                clearMinMaxWH(stage);
                stage.setMaximized(true);
                setMaxMinCurWH(stage, stage.getWidth(), stage.getHeight());
            }
            stage.centerOnScreen();
            //System.err.printf("WxH = %f, %f %n",stage.getWidth(), stage.getHeight());
        } catch (IOException e) {
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
        }
    }

    private static void clearMinMaxWH(Stage s){
        s.setMinWidth(0);
        s.setMinHeight(0);
        s.setMaxWidth(Double.MAX_VALUE);
        s.setMaxHeight(Double.MAX_VALUE);
    }
    private static void setMaxMinCurWH(Stage s, Double w, Double h){
        s.setMinWidth(w);
        s.setMinHeight(h);
        s.setWidth(w);
        s.setHeight(h);
        s.setMaxWidth(w);
        s.setMaxHeight(h);
    }

    public static File saveFile(String description, String extension) {
        FileChooser fc = new FileChooser();
        fc.setTitle("Save");
        fc.getExtensionFilters().addAll(new FileChooser.ExtensionFilter(description, extension));
        fc.setInitialDirectory(new File("."));
        return fc.showSaveDialog(null);
    }
}
