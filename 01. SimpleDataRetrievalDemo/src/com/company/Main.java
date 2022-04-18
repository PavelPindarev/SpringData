package com.company;

import java.sql.*;
import java.util.Properties;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) throws SQLException {
        Scanner sc = new Scanner(System.in);

        System.out.print("Enter username default (root): ");
        String user = sc.nextLine();
        user = user.equals("") ? "root" : user;
        System.out.println();

        System.out.print("Enter password default (empty):");
        String password = sc.nextLine().trim();
        System.out.println();

        Properties props = new Properties();
        props.setProperty("user", user);
        props.setProperty("password", password);

        Connection connection = DriverManager
                .getConnection("jdbc:mysql://localhost:3306/diablo", props);


        PreparedStatement stmt =
                connection.prepareStatement("SELECT user_name, first_name, last_name, COUNT(game_id) AS 'games_count'" +
                        " FROM users AS u " +
                        " JOIN" +
                        " users_games AS ug ON u.id = ug.user_id" +
                        " WHERE user_name = ?" +
                        " GROUP BY user_name;");

        String username = sc.nextLine();
        stmt.setString(1, username);
        ResultSet rs = stmt.executeQuery();

        if (rs.next()) {
            String dbUsername = rs.getString("user_name");
            String dbFirstname = rs.getString("first_name");
            String dbLastname = rs.getString("last_name");
            int dbCountOfGames = rs.getInt("games_count");

            System.out.printf("User: %s\n", dbUsername);
            System.out.printf("%s %s has played %d games", dbFirstname, dbLastname, dbCountOfGames);
        } else {
            System.out.println("No such user exists");
        }
        connection.close();
    }
}