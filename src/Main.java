import persistence.ConnectionDB;
import presentation.controllers.MainController;

import java.sql.*;
import java.util.Scanner;

public class Main {

    static Connection connection;

    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.run();
    }
}