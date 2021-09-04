package carsharing.dao;

import carsharing.model.Car;
import carsharing.model.Company;

import java.util.List;
import java.util.Optional;

public interface CarDao {
    List<Car> getCarsByCompany(Company company);

    List<Car> getFreeCarsByCompany(Company company);

    List<Car> getAllCars();

    void addCar(String name, Company company);

    Optional<Car> getCar(int id);
}
