package com.example.project_2;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class DatabaseConnection {
    Connection connection;
    static DatabaseConnection dc;
    Statement statement;

    private DatabaseConnection() throws SQLException {
        connection = DriverManager.getConnection("jdbc:sqlite:src/sakila.db");
        statement = connection.createStatement();
    }

    static DatabaseConnection getConnection() throws SQLException {
        if (dc == null) {
            dc = new DatabaseConnection();
        }

        return dc;
    }

    void addActor(String fName, String lName) throws SQLException {
        statement.executeUpdate("INSERT INTO actor (first_name, last_name) VALUES (\"" + fName + "\",\"" + lName + "\")");
    }

    void removeActor(String fName, String lName) throws SQLException {
        statement.executeUpdate("DELETE FROM actor WHERE first_name = \"" + fName + "\" AND last_name = \"" + lName + "\"");
    }

    List<String> listActor() throws SQLException {
        List<String> list = new ArrayList<>();

        ResultSet resultSet = statement.executeQuery("select first_name, last_name from actor");

        while (resultSet.next()) {
            list.add(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
        }

        return list;
    }
}
