package backend.Wrappers;

import backend.Entities.Part;
import backend.Managers.CustomerManager;
import backend.Managers.PartNameManager;
import backend.Managers.CategoryManager;
import backend.Models.Constants;
import backend.Sessions.SESSION;
import frontend.Controllers.AbstractControllers.FilterController;
import frontend.Controllers.AbstractControllers.FilterMasterController;
import frontend.Controllers.AbstractControllers.MainController;
import frontend.Controllers.SearchControllers.SecondSearchControllers.SecondSearchEditCategory;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;

import java.math.BigInteger;

public class PartWrapper {

    private Part part;
    private Button button;
    private CheckBox checkBox;

    private FilterMasterController filterMasterController;

    private boolean isChecked = false;

    public PartWrapper(Part part, FilterMasterController filterMasterController) {
        this.part = part;
        this.button = new Button("+");

        if (!SESSION.isAdmin() && part.getCategory_id() != null) {
            button.setDisable(true);
        }
        this.checkBox = new CheckBox();
        this.filterMasterController = filterMasterController;

        button.setOnAction(event -> {

            //admin can change and set
            if (SESSION.isAdmin()){

                SecondSearchEditCategory secondSearchEditCategory = MainController.setNewStage("/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchEditCategory.fxml", Constants.WINDOW_TITLE_EDIT_CATEGORY);
                secondSearchEditCategory.setPart(part);
                secondSearchEditCategory.setFilterMasterController(filterMasterController);

            } else {

                if (part.getCategory_id() == null) {

                    SecondSearchEditCategory secondSearchEditCategory = MainController.setNewStage("/frontend/FXML/SearchFXML/SecondSearchFXML/SecondSearchEditCategory.fxml", Constants.WINDOW_TITLE_EDIT_CATEGORY);
                    secondSearchEditCategory.setPart(part);
                    secondSearchEditCategory.setFilterMasterController(filterMasterController);

                } else {
                    System.out.println("Zmena nie je mozna");
                }
            }
        });

        checkBox.setOnAction(event -> {

            if (checkBox.isSelected()){
                isChecked = true;
            }

            if (!checkBox.isSelected()){
                isChecked = false;
            }
        });



    }

    public Part getPart() {return part;}

    public void setPart(Part part) {this.part = part;}

    public CheckBox getCheckBox() {return checkBox;}

    public Button getButton() {return button;}

    public String getPartNumber() {return part.getPart_number();}

    public String getCustomer() {return CustomerManager.getCustomer(part.getCustomer_id()).getCustomer_name();}

    public String getPartName() {return PartNameManager.getPartName(part.getPart_name_id()).getPart_name();}

    public String getCategoryName() {return CategoryManager.getCategory(part.getCategory_id()).getCategory_name();}

    public Short getRubberValue() {return part.getRubber();}

    public Double getDiameterAT() {return part.getDiameter_AT();}

    public Double getLengthLAT() {return part.getLength_L_AT();}

    public Double getDiameterIT() {return part.getDiameter_IT();}

    public Double getLengthLIT() {return part.getLength_L_IT();}

    public Double getDiameterZT() {return part.getDiameter_ZT();}

    public Double getLengthLZT() {return part.getLength_L_ZT();}

    public Integer getCrSteg() {return part.getCr_steg();}

    public Short getCrNiere() {return part.getCr_niere();}

    public Short getCaValue() {return part.getCa();}

    public Double getCtValue() {return part.getCt();}

    public Double getCkValue() {return part.getCk();}

    public String getDiameterATTOL() {return part.getDiameter_AT_tol();}

    public String getDiameterITTOL() {return part.getDiameter_IT_tol();}

    public String getDiameterZTTOL() {return part.getDiameter_ZT_tol();}

    public String getLengthLATTOL() {return part.getLength_L_AT_tol();}

    public String getLengthLITTOL() {return part.getLength_L_IT_tol();}

    public String getLengthLZTTOL() {return part.getLength_L_ZT_tol();}

    public boolean isChecked() {
        return isChecked;
    }
}
