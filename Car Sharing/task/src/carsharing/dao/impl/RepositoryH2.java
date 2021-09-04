package carsharing.dao.impl;

import carsharing.dao.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RepositoryH2 implements Repository {
    private static final String DEFAULT_DB_NAME = "car_sharing";
    private static final String DRIVER = "org.h2.Driver";
    private static final String PATH = "jdbc:h2:./src/carsharing/db/";

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

    private static final String SQL_CREATE_CUSTOMER_TABLE = "" +
            "CREATE TABLE IF NOT EXISTS customer (" +
            "id INT AUTO_INCREMENT PRIMARY KEY, " +
            "name VARCHAR(255) UNIQUE NOT NULL, " +
            "rented_car_id INT," +
            " constraint CUSTOMER_CAR_ID_FK " +
            " foreign key (rented_car_id) references car (id))";

    private final String connectionName;

    public RepositoryH2(String dbFilename) throws ClassNotFoundException {
        connectionName = PATH + dbFilename;
        Class.forName(DRIVER);
    }

    public RepositoryH2() throws ClassNotFoundException {
        this(DEFAULT_DB_NAME);
    }

    @Override
    public Connection getConnection() throws SQLException {
        return DriverManager.getConnection(connectionName);
    }

    public void createTables() {
        try (var connection = this.getConnection();
             var statement = connection.createStatement()) {

            connection.setAutoCommit(true);
            statement.execute(SQL_CREATE_COMPANY_TABLE);
            statement.execute(SQL_CREATE_CAR_TABLE);
            statement.execute(SQL_CREATE_CUSTOMER_TABLE);
            statement.execute("ALTER TABLE company ALTER COLUMN id RESTART WITH 1");
            statement.execute("ALTER TABLE car ALTER COLUMN id RESTART WITH 1");

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }
}
