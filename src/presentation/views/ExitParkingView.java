package presentation.views;

import business.model.User;
import presentation.components.RoundButton;
import presentation.components.RoundTextField;
import presentation.controllers.LeaveController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class ExitParkingView extends JPanel {
    private JPanel mainPanel;
    private User loggedUser;
    private LeaveController leaveController;

    public ExitParkingView(User loggedUser) {
        leaveController = new LeaveController(loggedUser);
        this.loggedUser = loggedUser;
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

        JLabel closeButton = new JLabel("\u2716");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 22));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBounds(840, 20, 30, 30);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(closeButton);

        JPanel userInteractionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(190, 180, 230), getWidth(), getHeight(), new Color(140, 130, 180));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30); // Bordes arrodonits
                g2.setColor(new Color(255, 255, 255, 50)); // Color del borde (blanc translúcid)
                g2.setStroke(new BasicStroke(2)); // Amplada del borde
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30); // Borde arrodonit
                g2.dispose();
            }
        };
        userInteractionPanel.setBounds(335, 120, 425, 270);

        userInteractionPanel.setLayout(null);
        userInteractionPanel.setOpaque(false);
        mainPanel.add(userInteractionPanel);


        JLabel plateLabel = new JLabel("PLATE");
        plateLabel.setFont(new Font("Arial", Font.BOLD, 16));
        plateLabel.setForeground(Color.BLACK);
        plateLabel.setBounds(30, 30, 100, 30);
        userInteractionPanel.add(plateLabel);

        JTextField plateField = new RoundTextField(20);
        plateField.setBounds(120, 30, 200, 30);
        userInteractionPanel.add(plateField);

        JButton leaveActionButton = new RoundButton("LEAVE");
        leaveActionButton.setBounds(145, 200, 150, 40);
        leaveActionButton.setBackground(new Color(204, 140, 0));
        leaveActionButton.setForeground(Color.WHITE);
        leaveActionButton.setFont(new Font("Arial", Font.BOLD, 14));
        userInteractionPanel.add(leaveActionButton);

        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);

        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30); // Ancho igual al panel para centrar
        menuPanel.add(menuTitle);

        JButton enterParkingButton = new RoundButton("Enter Parking");
        enterParkingButton.setBounds(20, 150, 160, 40);
        enterParkingButton.setBackground(new Color(150, 130, 200));
        enterParkingButton.setForeground(Color.BLACK);
        enterParkingButton.setFocusPainted(false);
        menuPanel.add(enterParkingButton);

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

            try {
                if (plate.isEmpty()) {
                    JOptionPane.showMessageDialog(this, "Enter the license plate number", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                } else {
                    if (!leaveController.isValidPlateFormat(plate)) {
                        JOptionPane.showMessageDialog(this, "Invalid plate format. Must be 3 uppercase letters followed by 3 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }
                }

                String userPlate = leaveController.userPlate(loggedUser, plate);
                if (!"success".equals(userPlate)) {
                    JOptionPane.showMessageDialog(this, userPlate, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String plateInside = leaveController.isVehicleInside(plate);
                if (!"success".equals(plateInside)) {
                    JOptionPane.showMessageDialog(this, plateInside, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                int slotId = leaveController.getSlotIdByPlate(plate);

                String updateSlot = leaveController.updateSlot(plate);
                if (!"success".equals(updateSlot)) {
                    JOptionPane.showMessageDialog(this, updateSlot, "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                leaveController.registerExitLogs("leave", plate, slotId);

                JOptionPane.showMessageDialog(this, "The vehicle is outside!", "Exit Parking", JOptionPane.INFORMATION_MESSAGE);

            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        enterParkingButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.setContentPane(new EntryParkingView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                setVisible(false);
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(ExitParkingView.this);
                parentFrame.setContentPane(new UserMenuView(loggedUser));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
}