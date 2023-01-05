package frontend.AdminControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
import backend.Managers.PartManager;
import backend.Models.PartBasic;
import frontend.CellClasses.CategoryCell;
import frontend.CellClasses.PartCell;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Objects;
import java.util.ResourceBundle;

public class CategoryMainController implements Initializable {

    @FXML
    public ListView<Category> categoryList;
    public ListView<PartBasic> partList;
    public Button prevPageButton;
    public Button nextPageButton;
    public Label pageLabel;

    private int currentPage = 1;
    private final int itemsPerPage = 17;
    private int pages;
    private Integer selectedCategoryId;
    private ObservableList<PartBasic> parts;

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
            if(pages > 1){
                nextPageButton.setVisible(true);
            }
        });
        updateCategoryList();
    }

    /**
     * Updates categories ListView from database
     */
    public void updateCategoryList(){
        ObservableList<Category> categories = CategoryManager.getAllCategories();
        if(categories == null)
            return;

        categoryList.setItems(categories);
        categoryList.setCellFactory(x -> new CategoryCell(this));
    }

    public void updatePartListAndPageLabel(Integer categoryId){
        pageLabel.setText(String.valueOf(currentPage));

        int offset = (currentPage - 1) * itemsPerPage;
        int itemCount = itemsPerPage;
        int from = 0;
        int to = 0;
        if(pages != 1) {
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

        parts = PartManager.getPartsBasicByCatogoryId(categoryId, itemCount, offset);
        if(parts == null){
            Alert alert = new Alert(Alert.AlertType.ERROR);
            alert.setTitle("ERROR");
            alert.setContentText("Error in loading parts.");
            alert.showAndWait();
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
        try {
            FXMLLoader loader = new FXMLLoader();
            String fxmlDocPath = "./src/frontend/AdminFXML/CategoryAdd.fxml";
            FileInputStream fxmlStream = new FileInputStream(fxmlDocPath);
            AnchorPane root = loader.load(fxmlStream);

            CategoryAddController controller = loader.getController();
            controller.init(CategoryMainController.this);

            Scene scene = new Scene(root);
            Stage stage = new Stage();
            stage.setResizable(false);
            stage.setScene(scene);
            stage.show();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @FXML
    public void prevPage() {
        if(selectedCategoryId == null)
            return;
        currentPage--;
        updatePartListAndPageLabel(selectedCategoryId);

        checkPageButtonsVisible();

        if(Objects.equals(currentPage, pages - 1)){
            nextPageButton.setVisible(true);
        }
    }

    @FXML
    public void nextPage() {
        if(selectedCategoryId == null)
            return;
        currentPage++;
        updatePartListAndPageLabel(selectedCategoryId);

        checkPageButtonsVisible();

        if(Objects.equals(currentPage, pages)){
            nextPageButton.setVisible(false);
        }
    }

    public void onIncerasePartRating(PartBasic part){
        int index = parts.indexOf(part);
        PartBasic part2 = parts.get(index - 1);

        if(Objects.equals(part.getRating(), part2.getRating())){
            equalsRatingAlert();
            return;
        }

        if(index - 1 == 0)
            currentPage--;
        checkPageButtonsVisible();

        PartManager.SwapRatings(part.getPartNumber(), part2.getPartNumber());
        updatePartListAndPageLabel(selectedCategoryId);
    }

    public void onDecreasePartRating(PartBasic part){
        int index = parts.indexOf(part);
        PartBasic part2 = parts.get(index + 1);

        if(Objects.equals(part.getRating(), part2.getRating())){
            equalsRatingAlert();
            return;
        }

        if(index + 1 == parts.size() - 1)
            currentPage++;
        checkPageButtonsVisible();

        PartManager.SwapRatings(part.getPartNumber(), part2.getPartNumber());
        updatePartListAndPageLabel(selectedCategoryId);
    }

    private void checkPageButtonsVisible(){
        prevPageButton.setVisible(currentPage != 1);
        nextPageButton.setVisible(currentPage != pages);
    }


    private void equalsRatingAlert(){
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("INFO");
        alert.setContentText("Rating of these parts are equal.");
        alert.showAndWait();
    }
}
