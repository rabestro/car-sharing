package carsharing.dao.impl;

import carsharing.dao.CarDao;
import carsharing.dao.Repository;
import carsharing.model.Car;
import carsharing.model.Company;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.TRACE;

public class CarDaoImpl implements CarDao {
    System.Logger LOGGER = System.getLogger("");
    private static final String SQL_CARS = "SELECT * FROM car";

    private final Repository repository;

    public CarDaoImpl(Repository repository) {
        this.repository = repository;
    }

    @Override
    public List<Car> getCarsByCompany(Company company) {
        try (var connection = repository.getConnection();
             var statement = connection.createStatement()) {
            var sql = "SELECT id, name FROM CAR WHERE company_id=" + company.getId();
            LOGGER.log(TRACE, sql);
            var resultSet = statement.executeQuery(sql);
            var cars = new ArrayList<Car>();
            while (resultSet.next()) {
                var car = new Car(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME")
                );
                cars.add(car);
                LOGGER.log(TRACE, "- car {0}", car.getName());
            }
            return cars;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return Collections.emptyList();
    }


    @Override
    public Collection<Car> getAllCars() {
        try (var connection = repository.getConnection();
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(SQL_CARS)) {
            var cars = new ArrayList<Car>();
            while (resultSet.next()) {
                var car = new Car(
                        resultSet.getInt("ID"),
                        resultSet.getString("NAME")
                );
                cars.add(car);
            }
            return Collections.unmodifiableCollection(cars);
        } catch (SQLException throwable) {
            throwable.printStackTrace();
        }
        return Collections.emptySet();
    }

    @Override
    public void addCar(String name, Company company) {
        try (var connection = repository.getConnection();
             var statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            final var sql = "INSERT INTO CAR (name, company_id) VALUES ('"
                    + name + "', " + company.getId() + ")";
            LOGGER.log(INFO, sql);
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

}
