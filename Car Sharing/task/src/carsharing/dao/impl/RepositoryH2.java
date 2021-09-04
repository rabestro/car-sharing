package carsharing.dao.impl;

import carsharing.dao.Repository;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class RepositoryH2 implements Repository {
    private static final String DEFAULT_DB_NAME = "car_sharing";
    private static final String DRIVER = "org.h2.Driver";
    private static final String PATH = "jdbc:h2:./src/carsharing/db/";

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
}
