package frontend.HistoryControllers;

import backend.Entities.Category;
import backend.Managers.CategoryManager;
import frontend.BasicControllers.FilterController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CategoryFilterController extends FilterController<Category> {
    @Override
    protected List<Category> getParameters() {
        return CategoryManager.getAllCategories();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        setType(Category.class);
    }
}
