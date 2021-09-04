package carsharing.dao.impl;

import carsharing.dao.CarDao;
import carsharing.dao.Repository;
import carsharing.model.Car;
import carsharing.model.Company;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;
import java.util.function.Function;

import static java.lang.System.Logger.Level.ERROR;

@AllArgsConstructor
public class CarDaoImpl implements CarDao {
    private static final System.Logger LOGGER = System.getLogger("");
    private static final String SQL_ALL_CARS = "SELECT * FROM car";
    private static final String SQL_INSERT_CAR = "INSERT INTO CAR (name, company_id) VALUES (?, ?)";
    private static final String SQL_BY_COMPANY = "SELECT * FROM CAR WHERE company_id=?";
    private static final String SQL_BY_ID = "SELECT * FROM CAR WHERE id=?";

    private static final Function<ResultSet, Car> CAR_BUILDER = rs -> {
        try {
            return new Car(rs.getInt("id"), rs.getString("name"));
        } catch (SQLException e) {
            LOGGER.log(ERROR, e::getMessage);
        }
        return Car.EMPTY;
    };

    private final Repository repository;

    @Override
    public List<Car> getCarsByCompany(Company company) {
        return repository.select(SQL_BY_COMPANY, CAR_BUILDER, company.getId());
    }

    @Override
    public List<Car> getAllCars() {
        return repository.select(SQL_ALL_CARS, CAR_BUILDER);
    }

    @Override
    public void addCar(String name, Company company) {
        repository.insert(SQL_INSERT_CAR, name, company.getId());
    }

    @Override
    public Optional<Car> getCar(int id) {
        return repository.select(SQL_BY_ID, CAR_BUILDER, id).stream().findFirst();
    }

}
