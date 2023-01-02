package frontend.BasicControllers.AbstractControllers;

import backend.Models.Filterable;
import javafx.scene.control.Button;

import java.util.List;
import java.util.stream.Collectors;

public interface FilterMasterController {

    void setParameters(List<? extends Filterable> parameters, Class<?> type);

    List<? extends Filterable> getParameters(Class<?> type);

    default void setStyleBasedOnParameters(List<?> parameters, Button button){
        if (parameters.isEmpty())
            button.setStyle("");
        else
            button.setStyle("-fx-background-color: #8a8f96");
    }
}
