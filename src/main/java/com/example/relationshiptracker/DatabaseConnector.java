package com.example.relationshiptracker;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DatabaseConnector {

    private static final String URL = "jdbc:sqlserver://localhost:1433;databaseName=relationship_tracker_db;integratedSecurity=true;";
    private static final String USER = "";
    private static final String PASSWORD = "";

    // Establish a database connection
    public static Connection getConnection() throws SQLException {
        return DriverManager.getConnection(URL);
    }

    public static void createTable() {
        try (Connection connection = getConnection()) {
            String createTableSQL = "CREATE TABLE IF NOT EXISTS relationships ("
                    + "id INT PRIMARY KEY IDENTITY(1,1),"
                    + "partner1 NVARCHAR(255),"
                    + "partner2 NVARCHAR(255),"
                    + "start_date DATE"
                    + ")";

            try (PreparedStatement statement = connection.prepareStatement(createTableSQL)) {
                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void insertData(String partner1, String partner2, java.sql.Date startDate) {
        try (Connection connection = getConnection()) {
            String insertSQL = "INSERT INTO relationships (partner1, partner2, start_date) VALUES (?, ?, ?)";

            try (PreparedStatement statement = connection.prepareStatement(insertSQL)) {
                statement.setString(1, partner1);
                statement.setString(2, partner2);
                statement.setDate(3, startDate);

                statement.executeUpdate();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void fetchData() {
        try (Connection connection = getConnection()) {
            String selectSQL = "SELECT * FROM relationships";

            try (PreparedStatement statement = connection.prepareStatement(selectSQL);
                 ResultSet resultSet = statement.executeQuery()) {

                while (resultSet.next()) {
                    int id = resultSet.getInt("id");
                    String partner1 = resultSet.getString("partner1");
                    String partner2 = resultSet.getString("partner2");
                    java.sql.Date startDate = resultSet.getDate("start_date");

                    System.out.println("ID: " + id + ", Partners: " + partner1 + " and " + partner2 + ", Start Date: " + startDate);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}