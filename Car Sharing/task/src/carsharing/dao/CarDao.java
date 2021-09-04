package carsharing.dao;

import carsharing.model.Car;
import carsharing.model.Company;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface CarDao {
    List<Car> getCarsByCompany(Company company);

    Collection<Car> getAllCars();

    void addCar(String name, Company company);

    Optional<Car> getCar(int id);
}
