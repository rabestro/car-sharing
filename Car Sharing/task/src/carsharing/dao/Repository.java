package carsharing.dao;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;

public interface Repository {
    Connection getConnection() throws SQLException;

    void insert(String sql, Object... args);

    <T> List<T> select(String sql, Function<ResultSet, T> builder, Object... args);

    <T> List<T> getAll(String sql, Function<ResultSet, T> builder);

}
