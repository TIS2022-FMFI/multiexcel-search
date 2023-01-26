package frontend.Controllers.AbstractControllers;

import backend.Models.Filterable;
import javafx.scene.control.Button;

import java.util.List;
import java.util.stream.Collectors;

public interface FilterMasterController {

    void setParameters(List<? extends Filterable> parameters, Class<?> type);

    void updateTable();

    List<? extends Filterable> getParameters(Class<?> type);

    default void setStyleBasedOnParameters(List<?> parameters, Button button) {
        if (parameters == null || parameters.isEmpty())
            button.setStyle("");
        else
            button.setStyle("-fx-background-color: #8a8f96");
    }

    default <T extends Filterable> List<T> getConcreteParametersAndSetStyle(List<? extends Filterable> parameters, Button button) {
        setStyleBasedOnParameters(parameters, button);
        if (parameters == null)
            return null;
        return parameters.stream().map(x -> (T) x).collect(Collectors.toList());
    }
}
