package frontend.BasicControllers;

import javafx.fxml.Initializable;
import javafx.scene.control.TextField;

import java.net.URL;
import java.util.ResourceBundle;

public class SearchController implements Initializable {
    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    private static void numericOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("[\\d\\.]")) {
                field.setText(newValue.replaceAll("[^\\d\\.]", ""));
            }
        });
    }
}

