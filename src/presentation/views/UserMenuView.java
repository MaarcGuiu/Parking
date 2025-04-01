package presentation.views;

import presentation.components.RoundButton;

import javax.swing.*;
import java.awt.*;

public class UserMenuView extends JPanel {
    private JPanel mainPanel;

    public UserMenuView() {
        setLayout(null); // Permitir posicionamiento absoluto

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
        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30); // Ancho igual al panel para centrar
        menuPanel.add(menuTitle);

        // Botones del menú
        String[] menuItems = {"Bookings", "Parking Status", "Statistics", "Enter - Leave parking"};
        int yPosition = 70;
        for (String item : menuItems) {
            JButton button = new RoundButton(item);
            button.setBounds(20, yPosition, 160, 40);
            button.setBackground(new Color(150, 130, 200));
            button.setForeground(Color.BLACK);
            button.setFocusPainted(false);
            menuPanel.add(button);
            yPosition += 60;
        }

        mainPanel.add(menuPanel);
        add(mainPanel); // Asegurar que el panel se agregue a UserMenuView
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
