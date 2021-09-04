package carsharing.component;

import carsharing.dao.CarDao;
import carsharing.model.Company;
import carsharing.ui.Menu;
import carsharing.ui.TextInterface;
import lombok.AllArgsConstructor;

import java.util.stream.IntStream;

@AllArgsConstructor
public class CompanyMenu implements TextInterface, Runnable {
    private final Company company;
    private final CarDao carDao;

    @Override
    public void run() {
        Menu.create("'" + company.getName() + "' company:")
                .add("Car list", this::carList)
                .add("Create a car", this::createCar)
                .set(Menu.Property.EXIT, "Back")
                .addExit()
                .run();
    }

    private void createCar() {
        println("Enter the car name:");
        final var name = scanner.nextLine();
        carDao.addCar(name, company);
    }

    private void carList() {
        println("''{0}' cars:", company.getName());
        final var cars = carDao.getCarsByCompany(company);
        if (cars.isEmpty()) {
            println("The car list is empty!");
            return;
        }
        IntStream.range(0, cars.size())
                .forEach(i -> println("{0}. {1}", i + 1, cars.get(i).getName()));
    }
}
