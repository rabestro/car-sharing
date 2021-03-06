package carsharing.dao.impl;

import carsharing.dao.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.function.Function;

import static java.lang.System.Logger.Level.ERROR;
import static java.lang.System.Logger.Level.TRACE;

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
    System.Logger LOGGER = System.getLogger("");

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

        } catch (SQLException ex) {
            ex.printStackTrace();
        }
    }

    @Override
    public void update(String sql, Object... args) {
        LOGGER.log(TRACE, sql);
        LOGGER.log(TRACE, () -> Arrays.toString(args));
        try (var connection = getConnection()) {
            var statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; ++i) {
                statement.setObject(i + 1, args[i]);
            }
            LOGGER.log(TRACE, statement::toString);
            var result = statement.executeUpdate();
            LOGGER.log(TRACE, "Processed {0} records.", result);
            connection.commit();
        } catch (SQLException e) {
            LOGGER.log(ERROR, e::getMessage);
        }
    }

    @Override
    public <T> List<T> select(String sql, Function<ResultSet, T> builder, Object... args) {
        LOGGER.log(TRACE, sql);
        LOGGER.log(TRACE, () -> Arrays.toString(args));
        try (var connection = getConnection()) {
            var statement = connection.prepareStatement(sql);
            for (int i = 0; i < args.length; ++i) {
                statement.setObject(i + 1, args[i]);
            }
            LOGGER.log(TRACE, statement::toString);
            var resultSet = statement.executeQuery();
            var list = new ArrayList<T>();
            while (resultSet.next()) {
                var object = builder.apply(resultSet);
                list.add(object);
            }
            return Collections.unmodifiableList(list);
        } catch (SQLException e) {
            LOGGER.log(ERROR, e::getMessage);
        }
        return Collections.emptyList();
    }

}
