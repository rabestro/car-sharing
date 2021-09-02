package carsharing.component;

import carsharing.dao.CompanyDao;
import carsharing.model.Company;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;

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
            final var menu = Menu.create("Choose a company:");

            companies.forEach(c -> menu.add(String.valueOf(c.getId()), c.getName(), () -> company(c)));
            menu.set(Menu.Property.EXIT, "Back").addExit().run();
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
        final var cars = dao.getCarsByCompany(company);
        if (cars.isEmpty()) {
            println("The car list is empty!");
            return;
        }
        cars.forEach(car -> println("{0}. {1}", car.getId(), car.getName()));
    }
}
