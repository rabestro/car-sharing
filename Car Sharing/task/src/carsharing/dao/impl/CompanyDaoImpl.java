package carsharing.dao.impl;

import carsharing.dao.CompanyDao;
import carsharing.model.Car;
import carsharing.model.Company;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.*;

import static java.lang.System.Logger.Level.INFO;
import static java.lang.System.Logger.Level.TRACE;

public class CompanyDaoImpl implements CompanyDao {
    System.Logger LOGGER = System.getLogger("");

    private static final String DEFAULT_DB_NAME = "car_sharing";
    private static final String DRIVER = "org.h2.Driver";
    private static final String PATH = "jdbc:h2:./src/carsharing/db/";
    private static final String SQL_COMPANIES = "SELECT * FROM company";
    private static final String SQL_CARS = "SELECT * FROM car";

    private static final String SQL_CREATE_COMPANY_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS company (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) UNIQUE NOT NULL)";

    private static final String SQL_CREATE_CAR_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS car (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) UNIQUE NOT NULL, " +
            "company_id INT NOT NULL," +
            " constraint CAR_COMPANY_ID_FK " +
            " foreign key (COMPANY_ID) references COMPANY (ID))";

    private final String connectionName;

    public CompanyDaoImpl(String dbFilename) throws ClassNotFoundException {
        connectionName = PATH + dbFilename;
        Class.forName(DRIVER);
    }

    public CompanyDaoImpl() throws ClassNotFoundException {
        this(DEFAULT_DB_NAME);
    }

    @Override
    public void createTable() {
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement()) {

            connection.setAutoCommit(true);
            statement.execute(SQL_CREATE_COMPANY_TABLE);
            statement.execute(SQL_CREATE_CAR_TABLE);
            statement.execute("ALTER TABLE company ALTER COLUMN id RESTART WITH 1");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public Collection<Company> getAllCompanies() {
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(SQL_COMPANIES)) {
            var companies = new ArrayList<Company>();
            while (resultSet.next()) {
                var company = new Company();
                company.setId(resultSet.getInt("ID"));
                company.setName(resultSet.getString("NAME"));
                companies.add(company);
            }
            return Collections.unmodifiableCollection(companies);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptySet();
    }

    @Override
    public Collection<Car> getAllCars() {
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement();
             var resultSet = statement.executeQuery(SQL_CARS)) {
            var cars = new ArrayList<Car>();
            while (resultSet.next()) {
                var car = new Car();
                car.setId(resultSet.getInt("ID"));
                car.setName(resultSet.getString("NAME"));
                cars.add(car);
            }
            return Collections.unmodifiableCollection(cars);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptySet();
    }

    @Override
    public List<Car> getCarsByCompany(Company company) {
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement()) {
            var sql = "SELECT id, name FROM CAR WHERE company_id=" + company.getId();
            var resultSet = statement.executeQuery(sql);
            var cars = new ArrayList<Car>();
            if (resultSet.next()) {
                var car = new Car();
                car.setId(resultSet.getInt("ID"));
                car.setName(resultSet.getString("NAME"));
                cars.add(car);
                LOGGER.log(INFO, "- car {0}", car);
            }
            return cars;
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Collections.emptyList();
    }

    @Override
    public void addCompany(String name) {
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            final var sql = "INSERT INTO COMPANY (name) VALUES ('" + name + "')";
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public void addCar(String name, Company company) {
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            final var sql = "INSERT INTO CAR (name, company_id) VALUES ('"
                    + name + "', " + company.getId() + ")";
            LOGGER.log(INFO, sql);
            statement.executeUpdate(sql);
            connection.commit();
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }

    @Override
    public Optional<Company> getCompany(int id) {
        LOGGER.log(TRACE, "searching company id={0}", id);
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement()) {
            final var sql = "SELECT id, name FROM COMPANY WHERE id=" + id;

            var resultSet = statement.executeQuery(sql);
            if (resultSet.next()) {
                var company = new Company();
                company.setId(resultSet.getInt("ID"));
                company.setName(resultSet.getString("NAME"));
                return Optional.of(company);
            }
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
        return Optional.empty();
    }
}
