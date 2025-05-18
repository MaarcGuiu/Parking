package presentation.views;

import business.model.User;
import business.model.Vehicle;
import business.model.Slot;
import presentation.controllers.UserController;
import presentation.controllers.LoginController;
import presentation.controllers.EnterController;
import presentation.controllers.ParkingStatusController;
import java.awt.*;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import presentation.components.RoundButton;
import presentation.components.RoundTextField;

public class BookMenuView extends JPanel {

    private JPanel mainPanel;
    private User loggedUser;
    private RoundTextField plateField;
    private JComboBox<String> vehicleCombo;
    private RoundButton createButton;
    private RoundButton backButton;
    private JLabel closeButton;
    private RoundButton bookMenuButton; // This view's button
    private RoundButton removeBookMenuButton;
    private UserController userController;
    private EnterController enterController;
    private ParkingStatusController parkingStatusController;

    public BookMenuView(User loggedUser) {
        this.loggedUser = loggedUser;
        this.userController = new UserController();
        this.enterController = new EnterController(loggedUser);
        this.parkingStatusController = new ParkingStatusController();
        setLayout(null);

        // Panel principal
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                    0,
                    0,
                    new Color(44, 37, 80),
                    getWidth(),
                    getHeight(),
                    new Color(161, 141, 204)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 500);
        add(mainPanel);

        // Menu lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);
        mainPanel.add(menuPanel);

        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30);
        menuPanel.add(menuTitle);


        bookMenuButton = new RoundButton("Book");
        bookMenuButton.setBounds(20, 210, 160, 40);
        bookMenuButton.setFont(new Font("Arial", Font.BOLD, 16));
        bookMenuButton.setBackground(new Color(255, 200, 0)); // Active color - we are in Book view
        bookMenuButton.setForeground(Color.BLACK);
        bookMenuButton.setFocusPainted(false);
        menuPanel.add(bookMenuButton);

        removeBookMenuButton = new RoundButton("Remove book");
        removeBookMenuButton.setBounds(20, 270, 160, 40);
        removeBookMenuButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeBookMenuButton.setBackground(new Color(150, 130, 200)); // Inactive color
        removeBookMenuButton.setForeground(Color.BLACK);
        removeBookMenuButton.setFocusPainted(false);
        menuPanel.add(removeBookMenuButton);

        JLabel titleLabel = new JLabel("BOOK A SLOT", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(200 + (900 - 200 - 300) / 2, 50, 300, 40);
        mainPanel.add(titleLabel);

        closeButton = new JLabel("\u2716");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 22));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBounds(840, 20, 30, 30);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(closeButton);

        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setOpaque(false);

        inputPanel.setBounds(350, 120, 400, 200);
        mainPanel.add(inputPanel);

        JLabel plateLabel = new JLabel("PLATE:");
        plateLabel.setForeground(Color.WHITE);
        plateLabel.setFont(new Font("Arial", Font.BOLD, 14));
        plateLabel.setBounds(50, 20, 120, 25);
        inputPanel.add(plateLabel);

        plateField = new RoundTextField(20);
        plateField.setBounds(180, 20, 200, 30);
        inputPanel.add(plateField);

        JLabel vehicleLabel = new JLabel("VEHICLE TYPE:");
        vehicleLabel.setForeground(Color.WHITE);
        vehicleLabel.setFont(new Font("Arial", Font.BOLD, 14));
        vehicleLabel.setBounds(50, 70, 120, 25);
        inputPanel.add(vehicleLabel);

        String[] vehicles = { "Car", "Motorcycle", "Truck" };
        vehicleCombo = new JComboBox<>(vehicles);
        vehicleCombo.setBounds(180, 70, 200, 30);
        inputPanel.add(vehicleCombo);

        createButton = new RoundButton("CREATE BOOKING");
        createButton.setFont(new Font("Arial", Font.BOLD, 10));
        createButton.setBounds(125, 140, 150, 40);
        createButton.setBackground(new Color(204, 140, 0));
        createButton.setForeground(Color.WHITE);
        inputPanel.add(createButton);

        backButton = new RoundButton("Back");
        backButton.setBounds(250, 420, 100, 40);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(backButton);

        createButton.addActionListener(e -> {
            String plate = plateField.getText();
            String vehicleType = (String) vehicleCombo.getSelectedItem();
            if (plate.isEmpty()) {
                JOptionPane.showMessageDialog(
                    this,
                    "Please enter a license plate.",
                    "Error",
                    JOptionPane.ERROR_MESSAGE
                );
            } else {
                try {
                    // 1. Verificar si el vehículo ya tiene una reserva
                    if (userController.checkUserBooking(plate)) {
                        JOptionPane.showMessageDialog(
                            this,
                            "This vehicle already has a reservation.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                    // 2. Verificar si hay plazas disponibles usando ParkingStatusManager.getFreeUnbookedSlots()
                    if (userController.getFreeUnbookedSlots().isEmpty()) {
                        JOptionPane.showMessageDialog(
                            this,
                            "No parking slots available.",
                            "Error", 
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                    // 3. Crear el vehículo para verificar compatibilidad
                    Vehicle vehicle = new Vehicle(plate, "Unknown", "Unknown", "Unknown", loggedUser, vehicleType);
                    
                    // 4. Verificar tipo de vehículo usando EnterManager.placesAvailable() para comprobación adicional
                    String placeCheck = enterController.placesAvailable(plate, vehicleType);
                    if ("notEqual".equals(placeCheck)) {
                        JOptionPane.showMessageDialog(
                            this,
                            "The vehicle registered does not match the vehicle type selected.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                    // 5. Buscar slots libres que coincidan con el tipo de vehículo
                    ArrayList<Slot> freeSlots = userController.getFreeUnbookedSlots();
                    ArrayList<Slot> matchingSlots = new ArrayList<>();
                    
                    for (Slot slot : freeSlots) {
                        if (userController.isYourVehicleCorrect(slot, vehicle)) {
                            matchingSlots.add(slot);
                        }
                    }
                    
                    if (!matchingSlots.isEmpty()) {
                        JOptionPane.showMessageDialog(
                            this,
                            "No available spaces for the selected vehicle type.",
                            "Error",
                            JOptionPane.ERROR_MESSAGE
                        );
                        return;
                    }
                    
                    // 6. Seleccionar el slot y crear la reserva
                    Slot selectedSlot = matchingSlots.get(0);
                    userController.updateTheSlotBooked(plate, selectedSlot.getIdSlot());
                    
                    JOptionPane.showMessageDialog(
                        this,
                        "Reservation created for " + plate + "!\n" +
                        "Slot ID: " + selectedSlot.getIdSlot() + "\n" +
                        "Floor: " + selectedSlot.getFloor(),
                        "Success",
                        JOptionPane.INFORMATION_MESSAGE
                    );
                    
                    plateField.setText("");
                    vehicleCombo.setSelectedIndex(0);
                    
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Database error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                } catch (Exception ex) {
                    JOptionPane.showMessageDialog(
                        this,
                        "Error: " + ex.getMessage(),
                        "Error",
                        JOptionPane.ERROR_MESSAGE
                    );
                }
            }
        });

        backButton.addActionListener(e -> {
            goBackToReservationMenu();
        });

        closeButton.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    goBackToUserMenu();
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    closeButton.setForeground(Color.RED); // Highlight on hover
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    closeButton.setForeground(Color.WHITE); // Restore color
                }
            }
        );

        bookMenuButton.addActionListener(e -> {
            System.out.println("Already in Book View.");
        });

        removeBookMenuButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                parentFrame.setContentPane(new RemoveBookMenuView(loggedUser));
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                System.err.println(
                    "Error: Could not find parent frame for BookMenuView."
                );
            }
        });
    }
    private void goBackToReservationMenu() {
        setVisible(false);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.setContentPane(new ReservationView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }
    private void goBackToUserMenu() {
        setVisible(false);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.setContentPane(new UserMenuView(loggedUser)); // Navigate back to User Menu
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
