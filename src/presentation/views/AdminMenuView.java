package presentation.views;

import business.model.User;
import presentation.components.RoundButton;

import javax.swing.*;
import java.awt.*;

public class AdminMenuView extends JPanel {
    private JPanel mainPanel;

    public AdminMenuView() {
        // Permitir posicionamiento absoluto
        setLayout(null);

        // Panel principal con degradado
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(44, 37, 80), getWidth(), getHeight(), new Color(161, 141, 204));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 500);

        // Menú lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);

        // Título centrado en el menú
        JLabel menuTitle = new JLabel("ADMIN MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30); // Ancho igual al panel para centrar
        menuPanel.add(menuTitle);

        // Botones del menú del admin
        // Botón 1: Create
        JButton createButton = new RoundButton("Create");
        createButton.setBounds(20, 70, 160, 40);
        createButton.setBackground(new Color(150, 130, 200));
        createButton.setForeground(Color.BLACK);
        createButton.setFocusPainted(false);
        menuPanel.add(createButton);

        // Botón 2: Edit
        JButton editButton = new RoundButton("Edit");
        editButton.setBounds(20, 130, 160, 40);
        editButton.setBackground(new Color(150, 130, 200));
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);
        menuPanel.add(editButton);

        // Botón 3: Delete
        JButton deleteButton = new RoundButton("Delete");
        deleteButton.setBounds(20, 190, 160, 40);
        deleteButton.setBackground(new Color(150, 130, 200));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusPainted(false);
        menuPanel.add(deleteButton);

        // Botón 4: Statistics
        JButton statisticsButton = new RoundButton("Statistics");
        statisticsButton.setBounds(20, 250, 160, 40);
        statisticsButton.setBackground(new Color(150, 130, 200));
        statisticsButton.setForeground(Color.BLACK);
        statisticsButton.setFocusPainted(false);
        menuPanel.add(statisticsButton);

        // Botón 5: Parking Status
        JButton parkingStatusButton = new RoundButton("Parking Status");
        parkingStatusButton.setBounds(20, 310, 160, 40);
        parkingStatusButton.setBackground(new Color(150, 130, 200));
        parkingStatusButton.setForeground(Color.BLACK);
        parkingStatusButton.setFocusPainted(false);
        menuPanel.add(parkingStatusButton);

        mainPanel.add(menuPanel);
        add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
