import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.Scanner;

public class sixthExercise {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "pavel_2004");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", properties);


        PreparedStatement statement = connection.prepareStatement("SELECT DISTINCT(name) FROM minions");
        ResultSet resultSet = statement.executeQuery();

        List<String> names = new ArrayList<>();

        while (resultSet.next()) {
            names.add(resultSet.getString("name"));
        }
        int countOfMinions = names.size();

        for (int i = 0; i < countOfMinions / 2; i++) {
            System.out.println(names.get(i));
            System.out.println(names.get(countOfMinions - 1 - i));
        }
        connection.close();
    }
}
