package frontend.Controllers.FilterControllers;

import backend.Entities.Part_name;
import backend.Managers.PartNameManager;
import frontend.Controllers.AbstractControllers.FilterController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class PartNameFilterController extends FilterController<Part_name> {
    @Override
    protected List<Part_name> getParameters() {
        return PartNameManager.getAllPartNames();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        setType(Part_name.class);
    }
}
