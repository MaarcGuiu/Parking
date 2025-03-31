import Persistence.ConnectionDB;

import java.io.IOException;
import java.sql.*;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.Date;
import java.util.Scanner;

public class Main {

    static Connection connection;

    public static void main(String[] args) {

        boolean timeToQuit = false;
        try (Scanner input = new Scanner(System.in)) {
            connection = ConnectionDB.getInstance();
            if (connection == null){
                System.out.println("No s'ha pogut establir la conexió");
                return;
            }

            do {
                try {
                   executeMenu(input);
                   timeToQuit = true;
                } catch (Exception e) {
                    System.out.println("Error " + e.getClass().getName());
                    System.out.println("Message: " + e.getMessage());
                }
            } while (!timeToQuit);
        } catch (Exception e) {
            System.out.println("Message: " + e.getMessage());
        }
    }

    public static void executeMenu(Scanner input) throws SQLException {

        PreparedStatement pstmt  = connection.prepareStatement("SELECT * FROM users");
        ResultSet rs = pstmt.executeQuery();

        while (rs.next()){
            System.out.println(rs.getString("Id") + " " +
                    rs.getString(2) + " " +
                    rs.getFloat(3) );
        }

    }
}