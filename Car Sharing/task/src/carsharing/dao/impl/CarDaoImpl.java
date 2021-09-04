package carsharing.dao.impl;

import carsharing.dao.CarDao;
import carsharing.dao.Repository;
import carsharing.model.Car;
import carsharing.model.Company;
import lombok.AllArgsConstructor;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

import static java.lang.System.Logger.Level.ERROR;

@AllArgsConstructor
public class CarDaoImpl implements CarDao {
    private static final System.Logger LOGGER = System.getLogger("");
    private static final String SQL_INSERT_CAR = "INSERT INTO CAR (name, company_id) VALUES (?, ?)";
    private static final String SQL_BY_COMPANY = "SELECT * FROM CAR WHERE company_id=?";
    private static final String SQL_BY_ID = "SELECT * FROM CAR WHERE id=?";
    private static final String SQL_FREE = "select * from car where COMPANY_ID = ? and " +
            "id not in (select RENTED_CAR_ID from CUSTOMER where RENTED_CAR_ID != 0)";
    private static final Function<ResultSet, Car> CAR_BUILDER = rs -> {
        try {
            return new Car(
                    rs.getInt("id"),
                    rs.getString("name"),
                    rs.getInt("company_id"));
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
    public List<Car> getFreeCarsByCompany(Company company) {
        return repository.select(SQL_FREE, CAR_BUILDER, company.getId());
    }

    @Override
    public void addCar(String name, Company company) {
        repository.update(SQL_INSERT_CAR, name, company.getId());
    }

    @Override
    public Optional<Car> getCar(Integer id) {
        return repository.select(SQL_BY_ID, CAR_BUILDER, id).stream().findFirst();
    }

}
