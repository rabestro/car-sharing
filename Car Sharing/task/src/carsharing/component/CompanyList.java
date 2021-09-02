package carsharing.component;

import carsharing.dao.CompanyDao;
import carsharing.model.Company;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;

import static java.lang.System.Logger.Level.INFO;

public class CompanyList implements TextInterface, Runnable {
    private final CompanyDao dao;

    public CompanyList(CompanyDao dao) {
        this.dao = dao;
    }

    @Override
    public void run() {
        var companies = dao.getAllCompanies();
        if (companies.isEmpty()) {
            println("The company list is empty!");
        } else {
            println("Choose a company:");
            companies.forEach(c -> println("{0}. {1}", c.getId(), c.getName()));
            var id = Integer.parseInt(scanner.nextLine());
            LOGGER.log(INFO, "selected company {0}", id);
            dao.getCompany(id)
                    .ifPresentOrElse(this::company, () -> println("Company not found"));
        }
    }

    private void company(Company company) {
        Menu.create("'" + company.getName() + "' company:")
                .add("Car list", () -> carList(company))
                .add("Create a car", () -> createCar(company))
                .set(Menu.Property.EXIT, "Back")
                .addExit()
                .run();
    }
    private void createCar(Company company) {
        println("Enter the car name:");
        final var name = scanner.nextLine();
        dao.addCar(name, company);
    }

    private void carList(Company company) {
        println("''{0}' cars:", company.getName());
        var cars = dao.getCarsByCompany(company);
        cars.forEach(car -> println("{0}. {1}", car.getId(), car.getName()));
    }
}
