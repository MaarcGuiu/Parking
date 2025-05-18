package presentation.views;

import business.model.User;
import presentation.components.RoundButton;
import presentation.components.RoundTextField;
import presentation.controllers.EnterController;
import presentation.controllers.LeaveController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;

public class EntryParkingView extends JPanel {
    private static final String SELECT_VEHICLE_OPTION = "--Select vehicle--";
    private JPanel mainPanel;
    private User loggedUser;
    private EnterController enterController;

    public EntryParkingView(User loggedUser) {
        enterController = new EnterController(loggedUser);
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

        JLabel exitParkingTitle = new JLabel("ENTER PARKING", SwingConstants.CENTER);
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

        JLabel vehicleLabel = new JLabel("VEHICLE");
        vehicleLabel.setFont(new Font("Arial", Font.BOLD, 16));
        vehicleLabel.setForeground(Color.BLACK);
        vehicleLabel.setBounds(30, 100, 100, 30);
        userInteractionPanel.add(vehicleLabel);

        String[] vehicleTypes = {"--Select vehicle--", "Car", "Truck", "Motorbike"};
        JComboBox<String> vehicleComboBox = new JComboBox<>(vehicleTypes);
        vehicleComboBox.setSelectedIndex(0);
        vehicleComboBox.setBounds(120, 100, 200, 30);
        userInteractionPanel.add(vehicleComboBox);

        JButton enterActionButton = new RoundButton("ENTER");
        enterActionButton.setBounds(145, 200, 150, 40);
        enterActionButton.setBackground(new Color(204, 140, 0));
        enterActionButton.setForeground(Color.WHITE);
        enterActionButton.setFont(new Font("Arial", Font.BOLD, 14));
        userInteractionPanel.add(enterActionButton);

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
        enterParkingButton.setBackground(new Color(255, 200, 0));
        enterParkingButton.setForeground(Color.BLACK);
        enterParkingButton.setFocusPainted(false);
        menuPanel.add(enterParkingButton);

        JButton leaveParkingButton = new RoundButton("Leave Parking");
        leaveParkingButton.setBounds(20, 230, 160, 40);
        leaveParkingButton.setBackground(new Color(150, 130, 200));
        leaveParkingButton.setForeground(Color.BLACK);
        leaveParkingButton.setFocusPainted(false);
        menuPanel.add(leaveParkingButton);


        mainPanel.add(menuPanel);
        add(mainPanel);

        enterActionButton.addActionListener(e -> {
            String plate = plateField.getText().toUpperCase();
            String vehicle = vehicleComboBox.getSelectedItem().toString();

            if (plate.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Enter the license plate number", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            } else {
                if (!enterController.isValidPlateFormat(plate)) {
                    JOptionPane.showMessageDialog(this, "Invalid plate format. Must be 3 letters followed by 3 digits.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }
            }

            try {
                if (!enterController.isUserPlate(loggedUser, plate) && enterController.vehicleExists(plate)) {
                    JOptionPane.showMessageDialog(this, "This vehicle belongs to another user.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                String registeredVehicle = enterController.registeredVehicle(plate);
                if ("is_inside".equals(registeredVehicle)) {
                    JOptionPane.showMessageDialog(this, "The vehicle entered is already inside the parking lot.", "Error", JOptionPane.ERROR_MESSAGE);
                    return;
                }

                if (!"success".equals(registeredVehicle)) {
                    if (vehicle.equals(SELECT_VEHICLE_OPTION)) {
                        JOptionPane.showMessageDialog(this, "Enter the type of vehicle so we can register it.", "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    String result = enterController.registerVehicle(loggedUser, plate, vehicle);
                    if (!"success".equals(result)) {
                        JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
                        return;
                    }

                    JOptionPane.showMessageDialog(this, "Vehicle registrat", "Enter Parking", JOptionPane.INFORMATION_MESSAGE);
                }

                String isBooked = enterController.isBooked(plate);
                if ("success".equals(isBooked)) {
                    int slotId = enterController.getSlotIdByPlate(plate);
                    enterController.registerEntryLogs("entry", plate, slotId);
                    JOptionPane.showMessageDialog(this, "The vehicle has been correctly entered into the parking lot thanks to the reservation made for this license plate.", "Enter Parking", JOptionPane.INFORMATION_MESSAGE);
                } else {
                    if (vehicle.equals(SELECT_VEHICLE_OPTION)) {
                        JOptionPane.showMessageDialog(this, "Enter the type of vehicle so we can assign you an available space.", "Error", JOptionPane.ERROR_MESSAGE);
                    } else {
                        String place = enterController.placesAvailable(plate, vehicle);
                        if (place != null) {
                            if ("notEqual".equals(place)) {
                                JOptionPane.showMessageDialog(this, "The vehicle registered does not match the vehicle type selected.", "Error", JOptionPane.ERROR_MESSAGE);
                            } else {
                                int slotId = enterController.getSlotIdByPlate(plate);
                                enterController.registerEntryLogs("entry", plate, slotId);
                                JOptionPane.showMessageDialog(this, place, "Enter Parking", JOptionPane.INFORMATION_MESSAGE);
                            }
                        } else {
                            JOptionPane.showMessageDialog(this, "No available space could be found due to the conditions of this vehicle.", "Error", JOptionPane.ERROR_MESSAGE);
                        }
                    }
                }
            } catch (SQLException ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "A database error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                ex.printStackTrace();
                JOptionPane.showMessageDialog(this, "An unexpected error occurred: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        leaveParkingButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.setContentPane(new ExitParkingView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                setVisible(false);
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(EntryParkingView.this);
                parentFrame.setContentPane(new UserMenuView(loggedUser));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
}
