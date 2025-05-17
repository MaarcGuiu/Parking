package presentation.controllers;

import business.SimulationManager;
import presentation.views.MainView;

import javax.swing.*;

public class MainController {


    public void run() {
        SwingUtilities.invokeLater(() -> {
            SimulationManager simulationManager = new SimulationManager();

            Thread simulationThread = new Thread(simulationManager);

            simulationThread.start();

            // Crear el JFrame principal
            JFrame mainFrame = new JFrame("THE PARKING LS");
            mainFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            mainFrame.setSize(900, 500);
            mainFrame.setLocationRelativeTo(null); // Centra el JFrame

            // Crear la vista principal (MainView) como JPanel
            MainView mainView = new MainView();

            // Establecer el JPanel como el contenido del JFrame
            mainFrame.setContentPane(mainView);
            mainFrame.revalidate();
            mainFrame.repaint();

            // Hacer visible el JFrame
            mainFrame.setVisible(true);
        });
    }
}
