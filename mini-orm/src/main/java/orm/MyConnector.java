package orm;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class MyConnector {
    private static Connection connection;
    private static final String connectionString = "jdbc:mysql://localhost:3306/";

    public static void createConnection(String user, String password, String dbName) throws SQLException {
        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        connection = DriverManager.getConnection(connectionString + dbName, props);

    }

    public static Connection getConnection() {
        return connection;
    }

}
