import java.sql.*;
import java.util.Scanner;

public class Project {
    public static void main(String[] args) throws SQLException {
        Scanner scanner = new Scanner(System.in);
        Connection cn = DriverManager.getConnection("jdbc:sqlite:src/sakila.db");

        Statement statement = cn.createStatement();

        while (true) {
            System.out.println("\nEnter a command: ");

            String input = scanner.nextLine();

            if (input.equals("ADD")) {
                System.out.println("\nEnter first name: ");
                String fName = scanner.nextLine();

                System.out.println("\nEnter last name: ");
                String lName = scanner.nextLine();

                statement.executeUpdate("INSERT INTO actor (first_name, last_name) VALUES (\"" + fName + "\",\"" + lName + "\")");
            } else if (input.equals("REMOVE")) {
                System.out.println("\nEnter first name: ");
                String fName = scanner.nextLine();

                System.out.println("\nEnter last name: ");
                String lName = scanner.nextLine();

                statement.executeUpdate("DELETE FROM actor WHERE first_name = \"" + fName + "\" AND last_name = \"" + lName + "\"");
            } else if (input.equals("LIST")) {
                ResultSet resultSet = statement.executeQuery("select first_name, last_name from actor");

                while (resultSet.next()) {
                    System.out.println(resultSet.getString("first_name") + " " + resultSet.getString("last_name"));
                }
            } else if (input.equals("EXIT")) {
                cn.close();
                System.exit(0);
            } else {
                System.out.println("\nPlease enter a valid command.");
            }
        }
    }
}
