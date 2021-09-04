package carsharing.component;

import carsharing.dao.CarDao;
import carsharing.dao.CompanyDao;
import carsharing.model.Car;
import carsharing.model.Customer;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;
import lombok.AllArgsConstructor;

@AllArgsConstructor
public class CustomerMenu implements TextInterface, Runnable {
    private final Customer customer;
    private final CarDao carDao;
    private final CompanyDao companyDao;

    @Override
    public void run() {
        Menu.create()
                .add("Rent a car", this::rent)
                .add("Return a rented car", this::returnCar)
                .add("My rented car", this::carInfo)
                .set(Menu.Property.EXIT, "Back")
                .onlyOnce()
                .addExit()
                .run();
    }

    private void rent() {
        if (customer.getCarId() > 0) {
            println("You've already rented a car!");
            return;
        }
        var companies = companyDao.getAllCompanies();

    }

    private void returnCar() {
        carDao.getCar(customer.getCarId())
                .ifPresentOrElse(car -> customer.setCarId(0), this::noCarError);
    }

    private void carInfo() {
        carDao.getCar(customer.getCarId())
                .ifPresentOrElse(this::printCarInfo, this::noCarError);
    }

    private void noCarError() {
        println("You didn't rent a car!");
    }

    private void printCarInfo(Car car) {
        println("Your rented car:");
        println(car.getName());
        println("Company:");
        companyDao.getCompany(car.getCompanyId())
                .ifPresentOrElse(company -> println(company.getName()), () -> println("?"));
    }
}
