import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class secondExercise {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties props = new Properties();
        props.setProperty("user", "root");
        props.setProperty("password", "pavel_2004");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", props);

        PreparedStatement statement = connection.prepareStatement("SELECT " +
                "    DISTINCT(v.`name`) as 'villain_name', m.`name` as 'minion_name', m.age as 'minion_age' " +
                "FROM " +
                "    villains AS v " +
                "        JOIN " +
                "    minions_villains AS mv ON mv.villain_id = v.id " +
                "        JOIN " +
                "    minions AS m ON m.id = minion_id " +
                "WHERE " +
                "    v.id = ?;");

        String villainId = scanner.nextLine();

        statement.setString(1, villainId);

        ResultSet rs = statement.executeQuery();

        int count = 1;
        while (rs.next()) {

            String villainName = rs.getString("villain_name");
            String minionName = rs.getString("minion_name");
            int minionAge = rs.getInt("minion_age");
            String arranger = count + ". ";

            if (count == 1) {
                System.out.println("Villain: " + villainName);
            }

            System.out.println(arranger + minionName + " " + minionAge);

            count++;
        }
        if (count == 1) {
            System.out.printf("No villain with ID %s exists in the database.", villainId);
        }
        connection.close();
    }
}
