package carsharing.component;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.CustomerDao;
import carsharing.ui.Menu;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerList implements Component {
    private final CustomerDao customerDao;
    private final CarDao carDao;
    private final CompanyDao companyDao;

    @Override
    public void run() {
        var customers = customerDao.getAllCustomers();
        if (customers.isEmpty()) {
            println("The customer list is empty!");
            return;
        }
        var menu = Menu.create("Choose a customer:");
        customers.forEach(customer ->
                menu.add(customer.getName(), () -> new CustomerMenu(customer, carDao, companyDao)));
        menu.set(Menu.Property.EXIT, "Back").onlyOnce().addExit().run();
    }

}
