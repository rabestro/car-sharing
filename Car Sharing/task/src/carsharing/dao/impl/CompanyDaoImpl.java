package carsharing.dao.impl;

import carsharing.dao.CompanyDao;
import carsharing.model.Company;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class CompanyDaoImpl implements CompanyDao {
    private static final String DEFAULT_DB_NAME = "car_sharing";
    private static final String DRIVER = "org.h2.Driver";
    private static final String PATH = "jdbc:h2:./src/carsharing/db/";
    private static final String SQL_COMPANIES = "SELECT * FROM company";
    private static final String SQL_CREATE_TABLE = "CREATE TABLE IF NOT EXISTS company (" +
            "id INTEGER AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) UNIQUE NOT NULL" +
            ")";

    private static int lastId = 0;

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
            statement.executeUpdate(SQL_CREATE_TABLE);

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
    public void addCompany(String name) {
        try (var connection = DriverManager.getConnection(connectionName);
             var statement = connection.createStatement()) {
            connection.setAutoCommit(true);
            final var sql = "INSERT INTO COMPANY (name) VALUES ('" + name + "')";
            statement.executeUpdate(sql);
        } catch (SQLException throwables) {
            throwables.printStackTrace();
        }
    }
}
