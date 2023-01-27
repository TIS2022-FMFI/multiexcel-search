package frontend.Wrappers;

import backend.Entities.Drawing;
import backend.Entities.Part;
import backend.Managers.CategoryManager;
import backend.Managers.CustomerManager;
import backend.Managers.DrawingManager;
import backend.Managers.PartNameManager;
import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.FilterMasterController;
import frontend.Controllers.AbstractControllers.MainController;
import frontend.Controllers.SearchControllers.SecondSearchControllers.SecondSearchEditCategory;
import javafx.embed.swing.SwingFXUtils;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.image.ImageView;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.Objects;

import static backend.Models.Constants.WITHOUT_CATEGORY_ID;


public class PartWrapper {

    private Part part;
    private final Button button;
    private final CheckBox checkBox;

    private final FilterMasterController filterMasterController;

    private boolean isChecked = false;

    public PartWrapper(Part part, FilterMasterController filterMasterController) {

        this.part = part;

        button = new Button();
        ImageView imageView = new ImageView(Objects.requireNonNull(getClass().getResource("/frontend/Images/editImage.png")).toExternalForm());
        button.setGraphic(imageView);

        if (!SESSION.isAdmin() && !part.getCategory_id().equals(WITHOUT_CATEGORY_ID)) {
            button.setDisable(true);
        }
        this.checkBox = new CheckBox();
        this.filterMasterController = filterMasterController;

        button.setOnAction(event -> {

            //admin can change and set
            if (SESSION.isAdmin()) {

                SecondSearchEditCategory secondSearchEditCategory = MainController.setNewStage("/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchEditCategory.fxml", Constants.WINDOW_TITLE_EDIT_CATEGORY);
                secondSearchEditCategory.setPart(part);
                secondSearchEditCategory.setFilterMasterController(filterMasterController);

            } else {
                if (part.getCategory_id().equals(WITHOUT_CATEGORY_ID)) {

                    SecondSearchEditCategory secondSearchEditCategory = MainController.setNewStage("/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchEditCategory.fxml", Constants.WINDOW_TITLE_EDIT_CATEGORY);
                    secondSearchEditCategory.setPart(part);
                    secondSearchEditCategory.setFilterMasterController(filterMasterController);

                } else {
                    System.out.println("Zmena nie je mozna");
                }
            }
        });

        checkBox.setOnAction(event -> {

            if (checkBox.isSelected()) {
                isChecked = true;
            }

            if (!checkBox.isSelected()) {
                isChecked = false;
            }
        });


    }

    public Part getPart() {
        return part;
    }

    public void setPart(Part part) {
        this.part = part;
    }

    public CheckBox getCheckBox() {
        return checkBox;
    }

    public Button getButton() {
        return button;
    }


    public String getPartNumber() {
        return part.getPart_number();
    }

    public String getCustomer() {
        if (part.getCustomer_id() == null) {
            return "";
        }
        return CustomerManager.getCustomer(part.getCustomer_id()).getCustomer_name();
    }

    public String getPartName() {
        if (part.getPart_name_id() == null)
            return "";
        return PartNameManager.getPartName(part.getPart_name_id()).getPart_name();
    }

    public String getCategoryName() {
        return CategoryManager.getCategory(part.getCategory_id()).getCategory_name();
    }


    public ImageView getImage() throws IOException {
        if (part.getDrawing_id() == null) {
            ImageView imageView = new ImageView(SwingFXUtils.toFXImage(new BufferedImage(256, 256, BufferedImage.TYPE_INT_ARGB), null));

            imageView.setFitHeight(200);
            imageView.setFitWidth(200);
            imageView.setPreserveRatio(true);

            return imageView;

        }
        Drawing image = DrawingManager.getDrawing(part.getDrawing_id());
        ByteArrayInputStream inStreambj = new ByteArrayInputStream(image.getDrawing());

        ImageView imageView = new ImageView(SwingFXUtils.toFXImage(ImageIO.read(inStreambj), null));
        imageView.setFitHeight(200);
        imageView.setFitWidth(200);
        imageView.setPreserveRatio(true);

        return imageView;
    }

    public String getRubberValue() {
        if (part.getRubber() == null) return "";
        return part.getRubber() + " ShA";
    }

    public String getDiameterAT() {
        if (part.getDiameter_AT() == null) return "";
        return "Ø " + part.getDiameter_AT();
    }

    public String getLengthLAT() {
        if (part.getLength_L_AT() == null) return "";
        return "Ø " + part.getLength_L_AT();
    }

    public String getDiameterIT() {
        if (part.getDiameter_IT() == null) return "";
        return "Ø " + part.getDiameter_IT();
    }

    public String getLengthLIT() {
        if (part.getLength_L_IT() == null) return "";
        return "Ø " + part.getLength_L_IT();
    }

    public String getDiameterZT() {
        if (part.getDiameter_ZT() == null) return "";
        return "Ø " + part.getDiameter_ZT();
    }

    public String getLengthLZT() {
        if (part.getLength_L_ZT() == null) return "";
        return "Ø " + part.getLength_L_ZT();
    }

    public String getCrSteg() {
        if (part.getCr_steg() == null) return "";
        return part.getCr_steg() + " N/mm";
    }

    public String getCrNiere() {
        if (part.getCr_niere() == null) return "";
        return part.getCr_niere() + " N/mm";
    }

    public String getCaValue() {
        if (part.getCa() == null) return "";
        return part.getCa() + " N/mm";
    }

    public String getCtValue() {
        if (part.getCt() == null) return "";
        return part.getCt() + " Nm/°";
    }

    public String getCkValue() {
        if (part.getCk() == null) return "";
        return part.getCk() + " Nm/°";
    }

    public String getDiameterATTOL() {
        return part.getDiameter_AT_tol();
    }

    public String getDiameterITTOL() {
        return part.getDiameter_IT_tol();
    }

    public String getDiameterZTTOL() {
        return part.getDiameter_ZT_tol();
    }

    public String getLengthLATTOL() {
        return part.getLength_L_AT_tol();
    }

    public String getLengthLITTOL() {
        return part.getLength_L_IT_tol();
    }

    public String getLengthLZTTOL() {
        return part.getLength_L_ZT_tol();
    }

    public boolean isChecked() {
        return isChecked;
    }
}
