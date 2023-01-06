package frontend.Controllers.AbstractControllers;

import backend.Models.Filterable;
import javafx.scene.control.Button;

import java.util.List;

public interface FilterMasterController {

    void setParameters(List<? extends Filterable> parameters, Class<?> type);

    List<? extends Filterable> getParameters(Class<?> type);

    default void setStyleBasedOnParameters(List<?> parameters, Button button) {
        if (parameters == null || parameters.isEmpty())
            button.setStyle("");
        else
            button.setStyle("-fx-background-color: #8a8f96");
    }
}
