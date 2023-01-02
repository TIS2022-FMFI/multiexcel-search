package frontend.BasicControllers;

import backend.Entities.Customer;
import backend.Entities.Part_name;
import backend.Models.Criteria;
import backend.Models.Filterable;
import backend.Models.Triple;
import backend.Sessions.SESSION;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Node;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;

import java.net.URL;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.ResourceBundle;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class FirstSearchController implements Initializable, FilterMasterController {
    @FXML
    public AnchorPane mainPane;
    @FXML
    public Text errorMessage;
    List<Part_name> partNames = new ArrayList<>();
    List<Customer> customers = new ArrayList<>();

    @FXML
    private TextField diameterATFrom;

    @FXML
    private TextField diameterATTo;

    @FXML
    private ChoiceBox<String> diameterATPriority;

    @FXML
    private TextField lengthLATFrom;

    @FXML
    private TextField lengthLATTo;

    @FXML
    private ChoiceBox<String> lengthLATPriority;

    @FXML
    private TextField rubberFrom;

    @FXML
    private TextField rubberTo;

    @FXML
    private ChoiceBox<String> rubberPriority;

    @FXML
    private TextField diameterITFrom;

    @FXML
    private TextField diameterITTo;

    @FXML
    private ChoiceBox<String> diameterITPriority;

    @FXML
    private TextField ckFrom;

    @FXML
    private TextField ckTo;

    @FXML
    private ChoiceBox<String> ckPriority;

    @FXML
    private TextField ctFrom;

    @FXML
    private TextField ctTo;

    @FXML
    private ChoiceBox<String> ctPriority;

    @FXML
    private TextField crStegFrom;

    @FXML
    private TextField crStegTo;

    @FXML
    private ChoiceBox<String> crStegPriority;

    @FXML
    private TextField caFrom;

    @FXML
    private TextField caTo;

    @FXML
    private ChoiceBox<String> caPriority;

    @FXML
    private TextField crNiereFrom;

    @FXML
    private TextField crNiereTo;

    @FXML
    private ChoiceBox<String> crNierePriority;

    @FXML
    private TextField lengthLITFrom;

    @FXML
    private TextField lengthLITTo;

    @FXML
    private ChoiceBox<String> lengthLITPriority;

    @FXML
    private TextField diameterZTFrom;

    @FXML
    private TextField diameterZTTo;

    @FXML
    private ChoiceBox<String> diameterZTPriority;

    @FXML
    private TextField lengthLZTFrom;

    @FXML
    private TextField lengthLZTTo;

    @FXML
    private ChoiceBox<String> lengthLZTPriority;

    private static void doubleOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (newValue.chars().filter(ch -> ch == '.').count() > 1)
                field.setText(oldValue);
            else if (!newValue.matches("^[0-9]+([.][0-9]+)?$")) {
                field.setText(newValue.replaceAll("[^\\d.]", ""));
            }
        });
    }

    private static void integerOnly(final TextField field) {
        field.textProperty().addListener((observable, oldValue, newValue) -> {
            if (!newValue.matches("\\d*")) {
                field.setText(newValue.replaceAll("\\D", ""));
            }
        });
    }

    private Stream<Node> getUndividableChildren(Pane parent) {
        return parent.getChildren().stream().flatMap(child -> {
            if (child instanceof Pane)
                return getUndividableChildren((Pane) child);
            return Stream.of(child);
        });
    }

    private void setupChoiceBox(ChoiceBox<String> choiceBox) {
        choiceBox.getItems().add("");
        for (int i = 1; i < 13; i++) {
            choiceBox.getItems().add(String.valueOf(i));
        }
        choiceBox.getSelectionModel().selectFirst();
        choiceBox.getSelectionModel().selectedItemProperty().addListener((observableValue, oldVal, newVal) -> {
            Stream<ChoiceBox<String>> choiceBoxes = getUndividableChildren(mainPane)
                    .filter(x -> x instanceof ChoiceBox<?>).map(x -> (ChoiceBox<String>) x);
            choiceBoxes.forEach(chB -> {
                if (chB != choiceBox) {
                    if (!oldVal.equals("") && !chB.getItems().contains(oldVal))
                        chB.getItems().add(oldVal);
                    if (!newVal.equals(""))
                        chB.getItems().remove(newVal);
                    chB.getItems().sort(Comparator.comparing(x -> {
                        if (x.equals(""))
                            return 0;
                        return Integer.valueOf(x);
                    }));
                }
            });
        });
    }

    private void setupHandlers() {
        getUndividableChildren(mainPane).forEach(child -> {
            if (child instanceof TextField) {
                doubleOnly((TextField) child);
            }
            if (child instanceof ChoiceBox<?>) {
                setupChoiceBox((ChoiceBox<String>) child);
            }
        });

        integerOnly(rubberFrom);
        integerOnly(rubberTo);
        integerOnly(crNiereFrom);
        integerOnly(crNiereTo);
        integerOnly(crStegFrom);
        integerOnly(crStegTo);
        integerOnly(caFrom);
        integerOnly(caTo);
    }

    private <T> String safeToString(T val, T minVal, T maxVal) {
        if (val.equals(minVal) || val.equals(maxVal)) {
            return "";
        }
        return val.toString();
    }

    private <T> void setTriple(Triple<T, T, Integer> triple, TextField from, TextField to, ChoiceBox<String> priority, Function<T, String> toStringFunc) {
        from.setText(toStringFunc.apply(triple.first));
        to.setText(toStringFunc.apply(triple.second));
        priority.getSelectionModel().select(triple.third.toString());
    }

    private void setDoubleTriple(Triple<Double, Double, Integer> triple, TextField from, TextField to, ChoiceBox<String> priority) {
        if (triple != null)
            setTriple(triple, from, to, priority, this::doubleToString);
    }

    private void setShortTriple(Triple<Short, Short, Integer> triple, TextField from, TextField to, ChoiceBox<String> priority) {
        if (triple != null)
            setTriple(triple, from, to, priority, this::shortToString);
    }

    private void setIntegerTriple(Triple<Integer, Integer, Integer> triple, TextField from, TextField to, ChoiceBox<String> priority) {
        if (triple != null)
            setTriple(triple, from, to, priority, this::integerToString);
    }

    private String doubleToString(Double val) {
        return safeToString(val, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    private String shortToString(Short val) {
        return safeToString(val, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    private String integerToString(Integer val) {
        return safeToString(val, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }


    private void setupValues() {
        Criteria criteria = SESSION.getCriteria();

        customers = criteria.getCustomers();
        partNames = criteria.getPartNames();

        setDoubleTriple(criteria.getDiameter_AT(), diameterATFrom, diameterATTo, diameterATPriority);
        setDoubleTriple(criteria.getLength_L_AT(), lengthLATFrom, lengthLATTo, lengthLATPriority);

        setDoubleTriple(criteria.getDiameter_IT(), diameterITFrom, diameterITTo, diameterITPriority);
        setDoubleTriple(criteria.getLength_L_IT(), lengthLITFrom, lengthLATTo, lengthLITPriority);

        setDoubleTriple(criteria.getDiameter_ZT(), diameterZTFrom, diameterZTTo, diameterZTPriority);
        setDoubleTriple(criteria.getLength_L_ZT(), lengthLZTFrom, lengthLZTTo, lengthLZTPriority);

        setShortTriple(criteria.getRubber(), rubberFrom, rubberTo, rubberPriority);

        setShortTriple(criteria.getCr_niere(), crNiereFrom, crNiereTo, crNierePriority);
        setIntegerTriple(criteria.getCr_steg(), crStegFrom, crStegTo, crStegPriority);

        setShortTriple(criteria.getCa(), caFrom, caTo, caPriority);
        setDoubleTriple(criteria.getCk(), ckFrom, ckTo, ckPriority);
        setDoubleTriple(criteria.getCt(), ctFrom, ctTo, ctPriority);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        setupHandlers();

        setupValues();
    }


    @FXML
    public void onClickPartNameFilterButton() {
        FilterController.onClickFilterButton("./src/frontend/BasicFXML/PartNameFilter.fxml",
                this,
                "Part Name");
    }

    @FXML
    public void onClickCustomerFilterButton() {
        FilterController.onClickFilterButton("./src/frontend/BasicFXML/CustomerFilter.fxml",
                this,
                "Customer");
    }

    private Integer getPriority(ChoiceBox<String> priority) {
        if (!priority.getValue().equals(""))
            return Integer.valueOf(priority.getValue());
        return Integer.MAX_VALUE;
    }

    private boolean isNotEmpty(TextField textField) {
        return textField.getText() != null && !textField.getText().trim().isEmpty();
    }

    private <T extends Comparable<T>> Triple<T, T, Integer> getTriple(TextField fromText, TextField toText, ChoiceBox<String> priority, Function<String, T> stringConverter, T minValue, T maxValue) {
        if (isNotEmpty(fromText) && isNotEmpty(toText)) {
            T from = stringConverter.apply(fromText.getText());
            T to = stringConverter.apply(toText.getText());
            if (from.compareTo(to) > 0)
                errorMessage.setText("From value must be less than or equal to the To value");
            else
                return new Triple<>(from, to, getPriority(priority));
        } else if (isNotEmpty(toText)) {
            T to = stringConverter.apply(toText.getText());
            return new Triple<>(minValue, to, getPriority(priority));
        } else if (isNotEmpty(fromText)) {
            T from = stringConverter.apply(fromText.getText());
            return new Triple<>(from, maxValue, getPriority(priority));
        }
        return null;
    }

    private Triple<Double, Double, Integer> getDoubleTriple(TextField fromText, TextField toText, ChoiceBox<String> priority) {
        return getTriple(fromText, toText, priority, Double::valueOf, Double.MIN_VALUE, Double.MAX_VALUE);
    }

    private Triple<Short, Short, Integer> getShortTriple(TextField fromText, TextField toText, ChoiceBox<String> priority) {
        return getTriple(fromText, toText, priority, Short::valueOf, Short.MIN_VALUE, Short.MAX_VALUE);
    }

    private Triple<Integer, Integer, Integer> getIntegerTriple(TextField fromText, TextField toText, ChoiceBox<String> priority) {
        return getTriple(fromText, toText, priority, Integer::valueOf, Integer.MIN_VALUE, Integer.MAX_VALUE);
    }


    @FXML
    public void onClickSearchButton() {
        Criteria criteria = SESSION.getCriteria();

        criteria.setCustomers(customers);
        criteria.setPartNames(partNames);

        criteria.setDiameter_AT(getDoubleTriple(diameterATFrom, diameterATTo, diameterATPriority));
        criteria.setLength_L_AT(getDoubleTriple(lengthLATFrom, lengthLATTo, lengthLATPriority));

        criteria.setDiameter_IT(getDoubleTriple(diameterITFrom, diameterITTo, diameterITPriority));
        criteria.setLength_L_IT(getDoubleTriple(lengthLITFrom, lengthLITTo, lengthLITPriority));

        criteria.setDiameter_ZT(getDoubleTriple(diameterZTFrom, diameterZTTo, diameterZTPriority));
        criteria.setLength_L_ZT(getDoubleTriple(lengthLZTFrom, lengthLZTTo, lengthLZTPriority));

        criteria.setRubber(getShortTriple(rubberFrom, rubberTo, rubberPriority));

        criteria.setCr_niere(getShortTriple(crNiereFrom, crNiereTo, crNierePriority));
        criteria.setCr_steg(getIntegerTriple(crStegFrom, crStegTo, crStegPriority));

        criteria.setCa(getShortTriple(caFrom, caTo, caPriority));
        criteria.setCk(getDoubleTriple(ckFrom, ckTo, ckPriority));
        criteria.setCt(getDoubleTriple(ctFrom, ctTo, ctPriority));

        BasicController.switchTab("./src/frontend/BasicFXML/SecondSearch.fxml", SESSION.getSearchTab());
    }

    @FXML
    public void onClearButton() {
        getUndividableChildren(mainPane).filter(x -> x instanceof TextField)
                .forEach(x -> ((TextField) x).setText(""));
        getUndividableChildren(mainPane).filter(x -> x instanceof ChoiceBox<?>)
                .forEach(x -> ((ChoiceBox<?>) x).getSelectionModel().selectFirst());
        customers = new ArrayList<>();
        partNames = new ArrayList<>();
    }

    @Override
    public void setParameters(List<? extends Filterable> parameters) {
        if (getClassOfList(parameters).equals(Part_name.class))
            partNames = parameters.stream().map(x -> (Part_name) x).collect(Collectors.toList());
        else if (getClassOfList(parameters).equals(Customer.class))
            customers = parameters.stream().map(x -> (Customer) x).collect(Collectors.toList());
    }


    @Override
    public List<? extends Filterable> getParameters(Class<?> type) {
        if (type.equals(Part_name.class))
            return partNames;
        if (type.equals(Customer.class))
            return customers;
        return null;
    }
}

