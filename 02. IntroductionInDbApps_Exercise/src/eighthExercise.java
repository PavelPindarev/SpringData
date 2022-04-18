import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class eighthExercise {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "pavel_2004");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        System.out.print("Enter id: ");
        int id = Integer.parseInt(scanner.nextLine());

        CallableStatement callProcedure = connection.prepareCall(
                "CALL usp_get_older(?)");

        callProcedure.setInt(1, id);
        callProcedure.execute();

        PreparedStatement statement = connection.prepareStatement(
                "SELECT DISTINCT(name) as 'name', age FROM minions");
        ResultSet resultSet = statement.executeQuery();

        while (resultSet.next()) {
            String minionName = resultSet.getString("name");
            int minionAge = resultSet.getInt("age");

            System.out.printf("%s %d\n", minionName, minionAge);
        }
        connection.close();
    }
}
