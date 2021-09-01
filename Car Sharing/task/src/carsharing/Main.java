package carsharing;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        Class.forName("org.h2.Driver");
        final var connectionName = getConnectionName(args);

        try (var connection = DriverManager.getConnection(connectionName);
             var st = connection.createStatement()) {
            connection.setAutoCommit(true);
            st.execute("DROP TABLE IF EXISTS COMPANY");

            final var sql = "CREATE TABLE COMPANY ("
                    + "ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(80) NOT NULL, UNIQUE(NAME))";
            st.executeUpdate(sql);

        }
        new Application(connectionName).run();
    }

    private static String getConnectionName(String... args) {
        final var name = args.length == 2 ? args[1] : "carsharing";
        return "jdbc:h2:" + "../task/src/carsharing/db/" + name;
    }
}