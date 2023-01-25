package frontend.Controllers.AbstractControllers;

import backend.Models.Filterable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import java.net.URL;
import java.util.*;
import java.util.stream.Collectors;

public abstract class FilterController<T extends Filterable> implements Initializable {
    @FXML
    public VBox scrollParameterFilter;
    Class<?> type;
    private Map<String, T> nameToParameter;
    private List<CheckBox> checkBoxes;
    private FilterMasterController masterController;

    public static void onClickFilterButton(String docPath, FilterMasterController masterController, String windowName) {
        FilterController<?> filterController = MainController.setNewStage(docPath, windowName);
        filterController.setMasterController(masterController);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        checkBoxes = new ArrayList<>();
        nameToParameter = new HashMap<>();
        List<T> parameters = getParameters();
        if (parameters != null) {
            for (T p : parameters) {
                CheckBox partNameCheckBox = new CheckBox();
                partNameCheckBox.setText(p.getName());
                nameToParameter.put(p.getName(), p);
                checkBoxes.add(partNameCheckBox);
                scrollParameterFilter.getChildren().add(partNameCheckBox);
            }
        }
    }

    @FXML
    public void onSelectAllAction() {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setSelected(true);
        }
    }

    @FXML
    public void onClearSelectionAction() {
        for (CheckBox checkBox : checkBoxes) {
            checkBox.setSelected(false);
        }
    }

    @FXML
    public void onCancelAction(ActionEvent event) {
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    @FXML
    void onConfirmAction(ActionEvent event) {
        if (masterController != null) {
            masterController.setParameters(collectCheckedParameters(), type);
        } else {
            System.err.println("historyMainController == null");
        }
        ((Stage) (((Button) event.getSource()).getScene().getWindow())).close();
    }

    protected void preSelect() {
        List<T> parameters = (List<T>) masterController.getParameters(type);
        if (parameters == null || parameters.isEmpty()) {
            return;
        }

        for (T p : parameters) {
            for (CheckBox checkBox : checkBoxes) {
                if (checkBox.getText().equals(p.getName())) {
                    checkBox.setSelected(true);
                }
            }
        }
    }

    private List<T> collectCheckedParameters() {
        if (checkBoxes.stream().noneMatch(CheckBox::isSelected))
            return null;
        List<T> parameters = new ArrayList<>();
        for (CheckBox checkBox : checkBoxes) {
            if (checkBox.isSelected()) {
                if (nameToParameter.containsKey(checkBox.getText())) {
                    parameters.add(nameToParameter.get(checkBox.getText()));
                }
            }
        }
        return parameters;
    }

    abstract protected List<T> getParameters();

    protected void setType(Class<?> type) {
        this.type = type;
    }

    public void setMasterController(FilterMasterController masterController) {
        this.masterController = masterController;
        preSelect();
    }
}
