package presentation.views;

import business.model.User;
import presentation.components.RoundButton;
import presentation.components.RoundTextField;

import javax.swing.*;
import java.awt.*;

public class ExitParkingView extends JPanel {
    private JPanel mainPanel;
    //private User loggedUser;

    public ExitParkingView(/*User loggedUser*/) {
        //this.loggedUser = loggedUser;
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

        JLabel exitParkingTitle = new JLabel("LEAVE PARKING", SwingConstants.CENTER);
        exitParkingTitle.setForeground(Color.WHITE);
        exitParkingTitle.setFont(new Font("Arial", Font.BOLD, 20));
        exitParkingTitle.setBounds(300, 20, 500, 30);
        mainPanel.add(exitParkingTitle);

        JPanel userInteractionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                g2.setColor(new Color(255, 255, 255, 0)); // Transparent
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Bordes arrodonits
                g2.setColor(new Color(255, 255, 255, 50)); // Color del borde (blanc translúcid)
                g2.setStroke(new BasicStroke(2)); // Amplada del borde
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Borde arrodonit
                g2.dispose();
            }
        };
        userInteractionPanel.setBounds(350, 120, 400, 200);
        userInteractionPanel.setLayout(null);
        userInteractionPanel.setOpaque(false);
        mainPanel.add(userInteractionPanel);


        JLabel plateLabel = new JLabel("PLATE");
        plateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        plateLabel.setForeground(Color.BLACK);
        plateLabel.setBounds(30, 30, 100, 30);
        userInteractionPanel.add(plateLabel);

        JTextField plateField = new RoundTextField(20);
        plateField.setBounds(100, 30, 200, 30);
        userInteractionPanel.add(plateField);

        JButton leaveActionButton = new RoundButton("LEAVE");
        leaveActionButton.setBounds(120, 120, 150, 40);
        leaveActionButton.setBackground(new Color(204, 140, 0));
        leaveActionButton.setForeground(Color.WHITE);
        leaveActionButton.setFont(new Font("Arial", Font.BOLD, 14));
        userInteractionPanel.add(leaveActionButton);

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
        // Botón 1: Enter Parking
        JButton enterParkingButton = new RoundButton("Enter Parking");
        enterParkingButton.setBounds(20, 150, 160, 40);
        enterParkingButton.setBackground(new Color(150, 130, 200));
        enterParkingButton.setForeground(Color.BLACK);
        enterParkingButton.setFocusPainted(false);
        menuPanel.add(enterParkingButton);

        // Botón 2: Leave Parking
        JButton leaveParkingButton = new RoundButton("Leave Parking");
        leaveParkingButton.setBounds(20, 230, 160, 40);
        leaveParkingButton.setBackground(new Color(255, 200, 0));
        leaveParkingButton.setForeground(Color.BLACK);
        leaveParkingButton.setFocusPainted(false);
        menuPanel.add(leaveParkingButton);


        mainPanel.add(menuPanel);
        add(mainPanel);

        leaveActionButton.addActionListener(e -> {
            String plate = plateField.getText();

            if (plate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Please check that you have entered the correct license plate number", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                //Afegir lògica
                //String result = leaveController(plate);

                //if ("success".equals(result)) {
                    JOptionPane.showMessageDialog(this, "The vehicle is outside!", "Exit Parking", JOptionPane.INFORMATION_MESSAGE);
                /*} else {
                    JOptionPane.showMessageDialog(this, "Invalid license plate number", "Error", JOptionPane.ERROR_MESSAGE);
                }*/
            }
        });

        //Afegir quan es faci EnterParkingView
        /*
        enterParkingButton.addActionListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(EnterParkingView.this);
                parentFrame.setContentPane(new MainView());  // Cambia el contenido por MainView
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });*/
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
