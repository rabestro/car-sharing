package carsharing;

import carsharing.dao.CompanyDao;
import carsharing.dao.impl.CompanyDaoImpl;
import carsharing.model.Company;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;

public class Application implements TextInterface, Runnable {
    private final CompanyDao companyDao;

    public Application(CompanyDaoImpl dao) {
        companyDao = dao;
    }

    @Override
    public void run() {
        companyDao.createTable();

        final var subMenu = Menu.create()
                .add("Company list", this::list)
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
        companyDao.addCompany(name);
    }

    private void list() {
        var companies = companyDao.getAllCompanies();
        if (companies.isEmpty()) {
            println("The company list is empty!");
        } else {
            println("Company list:");
            companies.forEach(c -> println("{0}. {1}", c.getId(), c.getName()));
            var id = Integer.parseInt(scanner.nextLine());
            companyDao.getCompany(id).ifPresent(this::company);
        }
    }

    private void company(Company company) {
        Menu.create("'" + company.getName() + "' company:")
                .add("Car list", this::carList)
                .add("Create a car", this::createCar)
                .set(Menu.Property.EXIT, "Back")
                .addExit();
    }

    private void createCar() {

    }

    private void carList() {

    }
}
