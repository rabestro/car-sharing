package carsharing;

import java.sql.DriverManager;
import java.sql.SQLException;

public class Main {

    public static void main(String[] args) throws ClassNotFoundException, SQLException {
        // write your code here
        final var database = args.length == 2 ? args[1] : "carsharing";

        Class.forName("org.h2.Driver");
        final var fileName = "../task/src/carsharing/db/" + database;

        try (var connection = DriverManager.getConnection("jdbc:h2:" + fileName);
             var st = connection.createStatement()) {
            connection.setAutoCommit(true);
            st.execute("DROP TABLE IF EXISTS COMPANY");

            final var sql = "CREATE TABLE COMPANY ("
                    + "ID INT PRIMARY KEY AUTO_INCREMENT, NAME VARCHAR(80) NOT NULL, UNIQUE(NAME))";
            st.executeUpdate(sql);

        }

    }
}