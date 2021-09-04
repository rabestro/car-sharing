package carsharing.component;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.dao.CustomerDao;
import carsharing.model.Car;
import carsharing.model.Company;
import carsharing.model.Customer;
import carsharing.ui.Menu;
import lombok.AllArgsConstructor;

import static java.lang.System.Logger.Level.DEBUG;

@AllArgsConstructor
public class CustomerMenu implements Component {
    private final Customer customer;
    private final CarDao carDao;
    private final CompanyDao companyDao;
    private final CustomerDao customerDao;

    @Override
    public void run() {
        LOGGER.log(DEBUG, "Customer: {0}", customer.getName());
        Menu.create()
                .add("Rent a car", this::rent)
                .add("Return a rented car", this::returnCar)
                .add("My rented car", this::carInfo)
                .set(Menu.Property.EXIT, "Back")
                .addExit()
                .run();
    }

    private void rent() {
        if (customer.getCarId() > 0) {
            println("You''ve already rented a car!");
            return;
        }
        var companies = companyDao.getAllCompanies();
        if (companies.isEmpty()) {
            println("The company list is empty!");
            return;
        }
        var menu = Menu.create("Choose a company:");
        companies.forEach(company -> menu.add(company.getName(), () -> rentCar(company)));
        menu.set(Menu.Property.EXIT, "Back").onlyOnce().addExit().run();
    }

    private void rentCar(Company company) {
        var cars = carDao.getCarsByCompany(company);
        if (cars.isEmpty()) {
            println("No available cars in the ''{0}'' company", company.getName());
            return;
        }
        var menu = Menu.create("Choose a car:");
        cars.forEach(car -> menu.add(car.getName(), () -> {
            println("You rented ''{0}''", car.getName());
            customer.setCarId(car.getId());
            customerDao.update(customer);
        }));
        menu.set(Menu.Property.EXIT, "Back").onlyOnce().addExit().run();
    }

    private void returnCar() {
        carDao.getCar(customer.getCarId())
                .ifPresentOrElse(car -> {
                    customer.setCarId(0);
                    customerDao.update(customer);
                    println("You''ve returned a rented car!");
                }, this::noCarError);
    }

    private void carInfo() {
        carDao.getCar(customer.getCarId())
                .ifPresentOrElse(this::printCarInfo, this::noCarError);
    }

    private void noCarError() {
        println("You didn''t rent a car!");
    }

    private void printCarInfo(Car car) {
        println("Your rented car:");
        println(car.getName());
        println("Company:");
        companyDao.getCompany(car.getCompanyId())
                .ifPresentOrElse(company -> println(company.getName()), () -> println("?"));
    }
}
