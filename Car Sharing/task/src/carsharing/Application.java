package carsharing;

import carsharing.component.CompanyList;
import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.Repository;
import carsharing.dao.impl.CarDaoImpl;
import carsharing.dao.impl.CompanyDaoImpl;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;

public class Application implements TextInterface, Runnable {
    private final CompanyDao dao;
    private final CarDao carDao;

    public Application(Repository repository) {
        this.dao = new CompanyDaoImpl(repository);
        this.carDao = new CarDaoImpl(repository);
    }

    @Override
    public void run() {
        final var subMenu = Menu.create()
                .add("Company list", new CompanyList(dao, carDao))
                .add("Create a company", this::createCompany)
                .set(Menu.Property.EXIT, "Back")
                .addExit();

        final var menu = Menu.create()
                .add("Log in as a manager", subMenu)
                .add("Log in as a customer", subMenu)
                .add("Create a customer", this::createCustomer)
                .addExit();

        menu.run();
    }

    private void createCustomer() {

    }

    private void createCompany() {
        println("Enter the company name:");
        final var name = scanner.nextLine();
        dao.addCompany(name);
    }

}
