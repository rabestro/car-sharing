package carsharing.dao;

import java.sql.Connection;
import java.sql.SQLException;

public interface Repository {
    Connection getConnection() throws SQLException;
}
