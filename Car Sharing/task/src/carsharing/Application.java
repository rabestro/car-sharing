package carsharing;

import carsharing.component.CompanyList;
import carsharing.component.CustomerMenu;
import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.CustomerDao;
import carsharing.dao.Repository;
import carsharing.dao.impl.CarDaoImpl;
import carsharing.dao.impl.CompanyDaoImpl;
import carsharing.dao.impl.CustomerDaoImpl;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;

public class Application implements TextInterface, Runnable {
    private final CompanyDao companyDao;
    private final CarDao carDao;
    private final CustomerDao customerDao;

    public Application(Repository repository) {
        carDao = new CarDaoImpl(repository);
        companyDao = new CompanyDaoImpl(repository);
        customerDao = new CustomerDaoImpl(repository);
    }

    @Override
    public void run() {
        final var subMenu = Menu.create()
                .add("Company list", new CompanyList(companyDao, carDao))
                .add("Create a company", this::createCompany)
                .set(Menu.Property.EXIT, "Back")
                .addExit();

        final var menu = Menu.create()
                .add("Log in as a manager", subMenu)
                .add("Log in as a customer", new CustomerMenu(customerDao))
                .add("Create a customer", this::createCustomer)
                .addExit();

        menu.run();
    }

    private void createCustomer() {
        println("Enter the customer name:");
        customerDao.addCustomer(scanner.nextLine());
        println("The customer was added!");
    }

    private void createCompany() {
        println("Enter the company name:");
        final var name = scanner.nextLine();
        companyDao.addCompany(name);
    }

}
