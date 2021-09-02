package carsharing.dao;

import carsharing.model.Car;
import carsharing.model.Company;

import java.util.Collection;
import java.util.Optional;

public interface CompanyDao {
    Collection<Company> getAllCompanies();

    Collection<Car> getAllCars();

    void addCompany(String name);

    void addCar(String name, Company company);

    void createTable();

    Optional<Company> getCompany(int id);

    Collection<Car> getCarsByCompany(Company company);
}
