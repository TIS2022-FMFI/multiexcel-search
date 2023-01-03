package frontend.HistoryControllers;

import backend.Entities.User;
import backend.Managers.UserManager;
import frontend.BasicControllers.AbstractControllers.FilterController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class UserFilterController extends FilterController<User> {

    @Override
    protected List<User> getParameters() {
        return UserManager.getUsers(false);
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        setType(User.class);
    }
}
