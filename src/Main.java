import presentation.controllers.MainController;

import java.sql.*;

public class Main {

    static Connection connection;

    public static void main(String[] args) {
        MainController controller = new MainController();
        controller.run();
    }
}