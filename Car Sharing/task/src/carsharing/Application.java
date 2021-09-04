package carsharing;

import carsharing.component.CompanyList;
import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.impl.CompanyDaoImpl;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;

public class Application implements TextInterface, Runnable {
    private final CompanyDao dao;
    private final CarDao carDao;

    public Application(CompanyDaoImpl dao, CarDao carDao) {
        this.dao = dao;
        this.carDao = carDao;
    }

    @Override
    public void run() {
        final var subMenu = Menu.create()
                .add("Company list", new CompanyList(dao, carDao))
                .add("Create a company", this::create)
                .set(Menu.Property.EXIT, "Back")
                .addExit();

        final var menu = Menu.create()
                .add("Log in as a manager", subMenu)
                .addExit();

        menu.run();
    }

    private void create() {
        println("Enter the company name:");
        final var name = scanner.nextLine();
        dao.addCompany(name);
    }

}
