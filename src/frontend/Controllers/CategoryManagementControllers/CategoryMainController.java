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
import javafx.scene.image.ImageView;
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
    public Button addButton;
    private int currentPage = 1;
    private int pages;
    private Integer selectedCategoryId;
    private ObservableList<PartBasic> parts;

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
            updatePartListAndPageLabel(categoryId);
            pages = PartManager.getPartCountForCategoryId(categoryId) / itemsPerPage + 1;
            if (pages > 1) {
                nextPageButton.setVisible(true);
            }
        });

        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/addImage.png")).toExternalForm());
        addButton.setGraphic(imageView);

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
            } else if (currentPage > 1 && currentPage < pages - 1) {
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

    private void checkPageButtonsVisible() {
        prevPageButton.setVisible(currentPage != 1);
        nextPageButton.setVisible(currentPage != pages);
    }


    private void swapRatingFailedAlert() {
        MainController.showAlert(Alert.AlertType.INFORMATION, "INFO", "Swap ratings failed");
    }

}
