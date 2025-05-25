import persistence.ConnectionDB;
import presentation.controllers.MainController;

import java.sql.*;
import java.util.Scanner;

/**
 * The type Main.
 */
public class Main {

    private static Connection connection;

    /**
     * The entry point of application.
     *
     * @param args the input arguments
     */
    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.run();
    }
}
