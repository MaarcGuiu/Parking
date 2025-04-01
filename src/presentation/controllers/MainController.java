package presentation.controllers;

import presentation.views.MainView;

import javax.swing.*;

public class MainController {

    public void run() {
        SwingUtilities.invokeLater(() -> {
            new MainView();
        });
    }
}
