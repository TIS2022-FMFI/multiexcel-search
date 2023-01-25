package frontend.Controllers.CategoryManagementControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
import backend.Managers.PartManager;
import backend.Models.Constants;
import backend.Models.PartBasic;
import backend.Sessions.SESSION;
import frontend.CellClasses.CategoryCell;
import frontend.CellClasses.PartCell;
import frontend.Controllers.AbstractControllers.MainController;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.layout.AnchorPane;

import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CategoryMainController implements Initializable {

    private final int itemsPerPage = 17;
    @FXML
    public ListView<Category> categoryList;
    public ListView<PartBasic> partList;
    public Button prevPageButton;
    public Button nextPageButton;
    public Label pageLabel;
    public Label noPartsLabel;
    private int currentPage = 1;
    private int pages;
    private Integer selectedCategoryId;
    private ObservableList<PartBasic> parts;

    /**
     * refreshing categories after import
     */
    public static void refreshCategories(){
        try{
            Platform.runLater(() -> {
                FXMLLoader loader = new FXMLLoader(Class.class.getResource("/frontend/FXML/CategoryManagementFXML/CategoryMain.fxml"));
                try {
                    AnchorPane root = loader.load();
                    SESSION.getCategoryTab().setContent(root);
                } catch (IOException e) {
                    MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
                }
            });
       }
       catch (Exception e){
            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", e.toString());
       }
    }

    /**
     * Default Fxml initialization
     */
    @Override
    public void initialize(URL location, ResourceBundle resources) {
        categoryList.setOnMouseClicked(x -> {
            currentPage = 1;
            Integer categoryId = categoryList.getSelectionModel().getSelectedItem().getCategory_id();
            selectedCategoryId = categoryId;
            int partsCount = PartManager.getPartCountForCategoryId(categoryId);
            pages = partsCount / itemsPerPage + 1;
            if(partsCount == 0){
                noPartsInCategory(categoryList.getSelectionModel().getSelectedItem().getCategory_name());
            }
            else {
                noPartsLabel.setVisible(false);
                updatePartListAndPageLabel(categoryId);
            }
            nextPageButton.setVisible(pages > 1);
            prevPageButton.setVisible(false);
        });
        updateCategoryList();
    }

    /**
     * Updates categories ListView from database
     */
    public void updateCategoryList() {
        ObservableList<Category> categories = CategoryManager.getAllCategories();
        if (categories == null)
            return;

        categoryList.setItems(categories);
        categoryList.setCellFactory(x -> new CategoryCell(this));
    }

    /**
     * Updates parts ListView from database and pages
     *
     * @param categoryId - id of category
     */
    public void updatePartListAndPageLabel(Integer categoryId) {
        pageLabel.setText(String.valueOf(currentPage));

        int offset = (currentPage - 1) * itemsPerPage;
        int itemCount = itemsPerPage;
        int from = 0;
        int to = 0;
        if (pages != 1) {
            if (currentPage == 1) {
                itemCount++;
                to--;
            } else if (currentPage > 1 && currentPage < pages) {
                offset--;
                itemCount += 2;
                from++;
                to--;
            } else if (currentPage == pages) {
                offset--;
                itemCount++;
                from++;
            }
        }

        parts = PartManager.getPartsBasicByCategoryId(categoryId, itemCount, offset);
        if (parts == null) {

            MainController.showAlert(Alert.AlertType.ERROR, "ERROR", "Error in loading parts.");
            return;
        }
        if (parts.isEmpty()) {
            partList.setItems(FXCollections.observableArrayList());
            return;
        }
        ObservableList<PartBasic> showParts = FXCollections.observableArrayList();
        showParts.addAll(parts.subList(from, parts.size() + to));
        partList.setItems(showParts);
        partList.setCellFactory(y -> new PartCell(this, currentPage == 1,
                currentPage == pages, showParts.size()));
    }

    /**
     * Opens new window for adding new category
     */
    @FXML
    public void addCategory() {
        String fxmlDocPath = "/frontend/FXML/CategoryManagementFXML/CategoryAdd.fxml";
        CategoryAddController controller = MainController.setNewStage(fxmlDocPath, Constants.WINDOW_TITLE_CATEGORY_MANAGMENT_ADD);
        if (controller != null)
            controller.init(CategoryMainController.this);
    }

    /**
     * previous page of parts
     */
    @FXML
    public void prevPage() {
        if (selectedCategoryId == null)
            return;
        currentPage--;
        updatePartListAndPageLabel(selectedCategoryId);

        checkPageButtonsVisible();

        if (Objects.equals(currentPage, pages - 1)) {
            nextPageButton.setVisible(true);
        }
    }

    /**
     * next page of parts
     */
    @FXML
    public void nextPage() {
        if (selectedCategoryId == null)
            return;
        currentPage++;
        updatePartListAndPageLabel(selectedCategoryId);

        checkPageButtonsVisible();

        if (Objects.equals(currentPage, pages)) {
            nextPageButton.setVisible(false);
        }
    }

    /**
     * increases rating of part
     *
     * @param part - part to increase
     */
    public void onIncerasePartRating(PartBasic part) {
        int index = parts.indexOf(part);
        PartBasic part2 = parts.get(index - 1);

        if (index - 1 == 0 && currentPage > 1)
            currentPage--;
        checkPageButtonsVisible();

        if(!PartManager.swapRatings(part.getPartNumber(), part2.getPartNumber())){
            swapRatingFailedAlert();
            return;
        }
        updatePartListAndPageLabel(selectedCategoryId);
    }

    /**
     * decreases rating of part
     *
     * @param part - part to decrease
     */
    public void onDecreasePartRating(PartBasic part) {
        int index = parts.indexOf(part);
        PartBasic part2 = parts.get(index + 1);

        if (index + 1 == parts.size() - 1 && currentPage < pages)
            currentPage++;
        checkPageButtonsVisible();

        if(!PartManager.swapRatings(part.getPartNumber(), part2.getPartNumber())){
            swapRatingFailedAlert();
            return;
        }
        updatePartListAndPageLabel(selectedCategoryId);
    }

    /**
     * clears partList
     */
    public void clearPartsList(){
        nextPageButton.setVisible(false);
        prevPageButton.setVisible(false);
        currentPage = 0;
        selectedCategoryId = null;
        parts = FXCollections.observableArrayList();
        partList.setItems(parts);
        pages = 0;
        pageLabel.setText("");
    }

    /**
     * checks if prev page button and next page button are visible
     */
    private void checkPageButtonsVisible() {
        prevPageButton.setVisible(currentPage != 1);
        nextPageButton.setVisible(currentPage != pages);
    }

    /**
     * swap of rating failed
     */
    private void swapRatingFailedAlert() {
        MainController.showAlert(Alert.AlertType.INFORMATION, "INFO", "Swap ratings failed");
    }

    /**
     * shows if category has no parts
     *
     * @param categoryName - name of category
     */
    private void noPartsInCategory(String categoryName){
        noPartsLabel.setText("Category " + categoryName + " has no parts");
        noPartsLabel.setVisible(true);
        parts = FXCollections.observableArrayList();
        partList.setItems(parts);
        pageLabel.setText("");
    }
}
