package frontend.BasicControllers.SearchControllers;

import backend.Entities.Customer;
import backend.Managers.CustomerManager;
import frontend.BasicControllers.AbstractControllers.FilterController;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class CustomerFilterController extends FilterController<Customer> {
    @Override
    protected List<Customer> getParameters() {
        return CustomerManager.getAllCustomers();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        super.initialize(location, resources);
        setType(Customer.class);
    }
}
