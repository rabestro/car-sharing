package carsharing.dao;

import carsharing.model.Car;
import carsharing.model.Company;

import java.util.Collection;
import java.util.List;

public interface CarDao {
    List<Car> getCarsByCompany(Company company);

    Collection<Car> getAllCars();

    void addCar(String name, Company company);

}
