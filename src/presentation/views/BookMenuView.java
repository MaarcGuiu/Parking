package presentation.views;

import business.model.User;
import business.model.Vehicle;
import business.model.Slot;
import presentation.controllers.UserController;
import presentation.controllers.EnterController;
import presentation.controllers.ParkingStatusController;
import java.awt.*;
import javax.swing.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import presentation.components.RoundButton;
import presentation.components.RoundTextField;

/**
 * The type Book menu view.
 */
public class BookMenuView extends JPanel {

    private JPanel mainPanel;
    private User loggedUser;
    private RoundTextField plateField;
    private JComboBox<String> vehicleCombo;
    private JComboBox<String> slotComboBox;
    private RoundButton createButton;
    private RoundButton backButton;
    private JLabel closeButton;
    private RoundButton bookMenuButton;
    private RoundButton removeBookMenuButton;
    private UserController userController;
    private EnterController enterController;
    private ParkingStatusController parkingStatusController;
    private List<Slot> compatibleSlots;

    /**
     * Instantiates a new Book menu view.
     *
     * @param loggedUser the logged user
     */
    public BookMenuView(User loggedUser) {
        this.loggedUser = loggedUser;
        compatibleSlots = new ArrayList<>();
        setLayout(null);

        initControllers();
        initView();

    }
    /**
     * Initializes the controllers used in this view.
     */
    private void initControllers() {
        try {
            userController = new UserController();
            enterController = new EnterController(loggedUser);
            parkingStatusController = new ParkingStatusController();

        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Initializes the view components and layout.
     */
    private void initView() {
        initializeMainPanel();
        initializeInputPanel();
        initializeListeners();

        loadAvailableSlots();
    }
    /**
     * Initializes the main panel with a gradient background and menu buttons.
     */
    private void initializeMainPanel() {
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                        0, 0, new Color(44, 37, 80),
                        getWidth(), getHeight(), new Color(161, 141, 204)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 500);
        add(mainPanel);

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
        bookMenuButton.setBackground(new Color(255, 200, 0));
        bookMenuButton.setForeground(Color.BLACK);
        bookMenuButton.setFocusPainted(false);
        menuPanel.add(bookMenuButton);

        removeBookMenuButton = new RoundButton("Remove book");
        removeBookMenuButton.setBounds(20, 270, 160, 40);
        removeBookMenuButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeBookMenuButton.setBackground(new Color(150, 130, 200));
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
    }
    /**
     * Initializes the input panel with fields for booking a slot.
     */
    private void initializeInputPanel() {
        JPanel inputPanel = new JPanel();
        inputPanel.setLayout(null);
        inputPanel.setOpaque(false);
        inputPanel.setBounds(350, 120, 400, 250);
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

        String[] vehicles = { "Car", "Motorbike", "Truck" };
        vehicleCombo = new JComboBox<>(vehicles);
        vehicleCombo.setBounds(180, 70, 200, 30);
        inputPanel.add(vehicleCombo);

        JLabel slotLabel = new JLabel("SELECT SLOT:");
        slotLabel.setForeground(Color.WHITE);
        slotLabel.setFont(new Font("Arial", Font.BOLD, 14));
        slotLabel.setBounds(50, 120, 120, 25);
        inputPanel.add(slotLabel);

        slotComboBox = new JComboBox<>();
        slotComboBox.setBounds(180, 120, 200, 30);
        slotComboBox.setEnabled(false);
        inputPanel.add(slotComboBox);

        createButton = new RoundButton("CREATE BOOKING");
        createButton.setFont(new Font("Arial", Font.BOLD, 14));
        createButton.setBounds(125, 190, 150, 40);
        createButton.setBackground(new Color(204, 140, 0));
        createButton.setForeground(Color.WHITE);
        inputPanel.add(createButton);
    }
    /**
     * Initializes the listeners for the components in this view.
     */
    private void initializeListeners() {
        vehicleCombo.addActionListener(e -> loadAvailableSlots());

        createButton.addActionListener(e -> createBooking());

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                goBackToUserMenu();
            }

            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                closeButton.setForeground(Color.RED);
            }

            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                closeButton.setForeground(Color.WHITE);
            }
        });

        bookMenuButton.addActionListener(e -> {
            // Actualment no fa res
        });

        removeBookMenuButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                parentFrame.setContentPane(new RemoveBookMenuView(loggedUser));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
    /**
     * Creates a booking based on the input fields.
     */
    private void createBooking() {
        String plate = plateField.getText().trim();
        String vehicleType = (String) vehicleCombo.getSelectedItem();

        if (plate.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please enter a license plate.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (slotComboBox.getSelectedIndex() == -1) {
            JOptionPane.showMessageDialog(this, "Please select a parking slot.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (!enterController.isValidPlateFormat(plate)) {
                JOptionPane.showMessageDialog(this,
                        "Invalid plate format. It should be 3 letters followed by 3 numbers (e.g., ABC123).",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (userController.checkUserBooking(plate)) {
                JOptionPane.showMessageDialog(this,
                        "This vehicle already has a reservation.",
                        "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            if (!enterController.vehicleExists(plate)) {
                JOptionPane.showMessageDialog(this,
                        "The Vehicle doesn't exist!",
                            "Error", JOptionPane.ERROR_MESSAGE);

                return;

            }

            int selectedIndex = slotComboBox.getSelectedIndex();
            Slot selectedSlot = compatibleSlots.get(selectedIndex);

            userController.updateTheSlotBooked(plate, selectedSlot.getIdSlot());

            JOptionPane.showMessageDialog(this,
                    "Reservation created successfully!\nVehicle: " + plate +
                            "\nSlot ID: " + selectedSlot.getIdSlot() +
                            "\nFloor: " + selectedSlot.getFloor(),
                    "Success", JOptionPane.INFORMATION_MESSAGE);

            plateField.setText("");
            vehicleCombo.setSelectedIndex(0);
            loadAvailableSlots();

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Unexpected error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Loads the available slots based on the selected vehicle type.
     */
    private void loadAvailableSlots() {
        try {
            String vehicleType = (String) vehicleCombo.getSelectedItem();

            // Obtener slots libres
            ArrayList<Slot> freeSlots = userController.getFreeUnbookedSlots();

            if (freeSlots == null || freeSlots.isEmpty()) {
                slotComboBox.removeAllItems();
                slotComboBox.setEnabled(false);
                compatibleSlots.clear();
                return;
            }

            // Filtrar slots compatibles con el tipo de vehículo
            compatibleSlots.clear();
            for (Slot slot : freeSlots) {
                String slotType = slot.getVehicle();
                if (slotType != null && slotType.equalsIgnoreCase(vehicleType)) {
                    compatibleSlots.add(slot);
                }
            }

            // Actualizar
            slotComboBox.removeAllItems();

            if (compatibleSlots.isEmpty()) {
                slotComboBox.addItem("No slots available for " + vehicleType);
                slotComboBox.setEnabled(false);
            } else {
                for (Slot slot : compatibleSlots) {
                    String slotInfo = String.format("Slot ID: %d - Floor: %s",
                            slot.getIdSlot(),
                            slot.getFloor());
                    slotComboBox.addItem(slotInfo);
                }
                slotComboBox.setEnabled(true);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(
                    this,
                    "Error loading available slots: " + ex.getMessage(),
                    "Error",
                    JOptionPane.ERROR_MESSAGE
            );
            slotComboBox.removeAllItems();
            slotComboBox.setEnabled(false);
            compatibleSlots.clear();
        }
    }
    /**
     * Navigates back to the user menu view.
     */
    private void goBackToUserMenu() {
        setVisible(false);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.setContentPane(new UserMenuView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    /**
     * Gets main panel.
     *
     * @return the main panel
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }
}
