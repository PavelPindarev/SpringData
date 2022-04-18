import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class fifthExercise {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);

        Properties properties = new Properties();
        properties.setProperty("user", "root");
        properties.setProperty("password", "pavel_2004");

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/minions_db", properties);

        int villainId = Integer.parseInt(scanner.nextLine());

        PreparedStatement selectVillain = connection.prepareStatement(
                "SELECT name FROM villains WHERE id = ?");
        selectVillain.setInt(1, villainId);
        ResultSet villainSet = selectVillain.executeQuery();

        if (!villainSet.next()) {
            System.out.println("No such villain was found");
            return;
        }

        String villainName = villainSet.getString("name");

        PreparedStatement selectAllVillainMinions = connection.prepareStatement(
                "SELECT COUNT(DISTINCT minion_id) as m_count" +
                        " FROM minions_villains WHERE villain_id = ?");
        selectAllVillainMinions.setInt(1, villainId);
        ResultSet minionsCountSet = selectAllVillainMinions.executeQuery();
        minionsCountSet.next();
        int countMinionsDeleted = minionsCountSet.getInt("m_count");

        connection.setAutoCommit(false);

        try {
            PreparedStatement deleteMinionsVillains = connection.prepareStatement(
                    "DELETE FROM minions_villains WHERE villain_id = ?");
            deleteMinionsVillains.setInt(1, villainId);
            deleteMinionsVillains.executeUpdate();

            PreparedStatement deleteVillain = connection.prepareStatement(
                    "DELETE FROM villains WHERE id = ?");
            deleteVillain.setInt(1, villainId);
            deleteVillain.executeUpdate();

            connection.commit();

            System.out.println(villainName + " was deleted");
            System.out.println(countMinionsDeleted + " minions released");
        } catch (SQLException e) {
            e.printStackTrace();

            connection.rollback();
        }

        connection.close();
    }
}