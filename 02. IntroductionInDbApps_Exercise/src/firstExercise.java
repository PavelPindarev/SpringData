import java.sql.*;
import java.util.Properties;

public class firstExercise {
    public static void main(String[] args) throws SQLException {

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pavel_2004");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        PreparedStatement statement = connection.prepareStatement("SELECT `name`," +
                " COUNT(DISTINCT(minion_id)) AS 'minions_count' " +
                " FROM villains AS v " +
                " JOIN" +
                " minions_villains AS mv ON v.id = mv.villain_id" +
                " GROUP BY `name`" +
                " HAVING minions_count > 15" +
                " ORDER BY minions_count DESC;");

        ResultSet rs = statement.executeQuery();

        while (rs.next()) {
            String dbName = rs.getString("name");
            int dbMinionsCount = rs.getInt("minions_count");

            System.out.printf("%s %d\n", dbName, dbMinionsCount);
        }
        connection.close();
    }
}
