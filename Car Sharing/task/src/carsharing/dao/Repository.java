package carsharing.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.function.Function;

public interface Repository {
    Connection getConnection() throws SQLException;

    void update(String sql, Object... args);

    <T> List<T> select(String sql, Function<ResultSet, T> builder, Object... args);

}
