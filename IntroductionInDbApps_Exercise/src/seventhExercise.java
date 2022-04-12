import java.sql.*;
import java.util.Arrays;
import java.util.Properties;
import java.util.Scanner;

public class seventhExercise {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "pavel_2004");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        PreparedStatement statementLowerCase = connection.prepareStatement(
                "UPDATE minions SET name = LOWER(name)");

        statementLowerCase.executeUpdate();


        int[] ids = Arrays.stream(scanner.nextLine()
                .split("\\s+")).mapToInt(Integer::parseInt).toArray();

        for (int i = 0; i < ids.length; i++) {
            int id = ids[i];

            PreparedStatement increaseStatement = connection.prepareStatement(
                    "UPDATE minions SET age = age + 1 WHERE id = ?");
            increaseStatement.setInt(1, id);
            increaseStatement.executeUpdate();
        }

        PreparedStatement increaseStatement = connection.prepareStatement("SELECT DISTINCT(name), age FROM minions");
        ResultSet resultSet = increaseStatement.executeQuery();

        while (resultSet.next()) {
            System.out.println(resultSet.getString("name") + " " + resultSet.getInt("age"));
        }

        connection.close();
    }
}
