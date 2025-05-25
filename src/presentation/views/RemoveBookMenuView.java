package presentation.views;

import business.model.User;
import business.model.Vehicle;
import business.model.Slot;
import presentation.components.RoundButton;
import presentation.controllers.LoginController;
import presentation.controllers.UserController;
import presentation.controllers.ParkingStatusController;

import javax.swing.*;
import javax.swing.table.DefaultTableModel;
import java.awt.*;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.Map;

public class RemoveBookMenuView extends JPanel {

    private JPanel mainPanel;
    private User loggedUser;
    private RoundButton cancelReservationButton;
    private RoundButton backButton;
    private JLabel closeButton;
    private JTable reservationsTable;
    private RoundButton bookMenuButton; 
    private RoundButton removeBookMenuButton;
    private JTextField licensePlateField;
    private UserController userController;
    private ParkingStatusController parkingStatusController;
    private LoginController loginController;
    private ArrayList<Vehicle> userBookings;
    private Map<String, Integer> plateToSlotMap; // Para mapear cada matrícula a su ID de slot
    /**
     * Constructor for RemoveBookMenuView.
     * Initializes the view with the logged user and sets up the UI components.
     *
     * @param loggedUser The user who is currently logged in.
     */
    public RemoveBookMenuView(User loggedUser) {
        this.loggedUser = loggedUser;
        initControllers();
        initData();
        initLayout();
        initMenu();
        initTitleAndClose();
        initReservationsTable();
        initConfirmPanel();
        initListeners();
    }
    /**
     * Initializes the controllers needed for this view.
     * Catches any SQL exceptions and shows an error message if the controller cannot be initialized.
     */
    private void initControllers() {
        try {
            userController = new UserController();
            parkingStatusController = new ParkingStatusController();
            loginController = new LoginController();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Initializes the data needed for the view, such as user bookings and slot mappings.
     * Catches any SQL exceptions and shows an error message if the data cannot be loaded.
     */
    private void initData() {
        plateToSlotMap = new HashMap<>();
        try {
            userBookings = userController.getPanelBookings(loggedUser.getId());
            List<Slot> allSlots = userController.getAllSlotsReserved();

            if (allSlots != null) {
                for (Slot slot : allSlots) {
                    if (slot.getVehiclePlate() != null && slot.getBooked()) {
                        plateToSlotMap.put(slot.getVehiclePlate(), slot.getIdSlot());
                    }
                }
            }

            if (userBookings == null) {
                userBookings = new ArrayList<>();
            }
        } catch (SQLException e) {
            userBookings = new ArrayList<>();
            JOptionPane.showMessageDialog(this, "Error loading reservations: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Initializes the layout of the RemoveBookMenuView.
     * Sets the layout manager, preferred size, and adds the main panel with a gradient background.
     */
    private void initLayout() {
        setLayout(null);
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
        add(mainPanel);
    }
    /**
     * Initializes the menu panel with buttons for booking and removing bookings.
     * The menu is styled with a specific background color and font.
     */
    private void initMenu() {
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
        bookMenuButton.setBackground(new Color(150, 130, 200));
        bookMenuButton.setForeground(Color.BLACK);
        bookMenuButton.setFocusPainted(false);
        menuPanel.add(bookMenuButton);

        removeBookMenuButton = new RoundButton("Remove book");
        removeBookMenuButton.setBounds(20, 270, 160, 40);
        removeBookMenuButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeBookMenuButton.setBackground(new Color(255, 200, 0));
        removeBookMenuButton.setForeground(Color.BLACK);
        removeBookMenuButton.setFocusPainted(false);
        menuPanel.add(removeBookMenuButton);
    }
    /**
     * Initializes the title and close button for the RemoveBookMenuView.
     * The title is displayed at the top of the view, and the close button allows users to return to the user menu.
     */
    private void initTitleAndClose() {
        JLabel titleLabel = new JLabel("CANCEL A RESERVATION", SwingConstants.CENTER);
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(200 + (900 - 200 - 400) / 2, 30, 400, 40);
        mainPanel.add(titleLabel);

        closeButton = new JLabel("\u2716");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 22));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBounds(840, 20, 30, 30);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(closeButton);
    }
    /**
     * Initializes the reservations table that displays the user's bookings.
     * The table is non-editable and styled with a specific font and row height.
     */
    private void initReservationsTable() {
        String[] columns = {"License Plate", "Vehicle Type", "Slot ID"};
        DefaultTableModel model = new DefaultTableModel(columns, 0) {
            @Override
            public boolean isCellEditable(int row, int column) { return false; }
        };

        if (userBookings != null && !userBookings.isEmpty()) {
            for (Vehicle vehicle : userBookings) {
                Integer slotId = plateToSlotMap.get(vehicle.getPlate());
                model.addRow(new Object[]{
                        vehicle.getPlate(),
                        vehicle.getType(),
                        slotId != null ? slotId.toString() : "N/A"
                });
            }
        }

        reservationsTable = new JTable(model);
        reservationsTable.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        reservationsTable.setFont(new Font("Arial", Font.PLAIN, 14));
        reservationsTable.getTableHeader().setFont(new Font("Arial", Font.BOLD, 14));
        reservationsTable.setRowHeight(25);

        JScrollPane scrollPane = new JScrollPane(reservationsTable);
        scrollPane.setBounds(250, 90, 550, 200);
        mainPanel.add(scrollPane);
    }

    /**
     * Initializes the confirmation panel where users can enter their license plate to confirm cancellation.
     * The panel includes a label, text field, and a button to cancel the reservation.
     */
    private void initConfirmPanel() {
        JPanel confirmPanel = new JPanel();
        confirmPanel.setLayout(null);
        confirmPanel.setOpaque(false);
        confirmPanel.setBounds(250, 310, 550, 100);
        mainPanel.add(confirmPanel);

        JLabel confirmLabel = new JLabel("Enter license plate to confirm cancellation:");
        confirmLabel.setForeground(Color.WHITE);
        confirmLabel.setFont(new Font("Arial", Font.BOLD, 14));
        confirmLabel.setBounds(0, 10, 300, 25);
        confirmPanel.add(confirmLabel);

        licensePlateField = new JTextField();
        licensePlateField.setBounds(0, 40, 300, 30);
        licensePlateField.setFont(new Font("Arial", Font.PLAIN, 14));
        confirmPanel.add(licensePlateField);

        cancelReservationButton = new RoundButton("CANCEL RESERVATION");
        cancelReservationButton.setFont(new Font("Arial", Font.BOLD, 14));
        cancelReservationButton.setBounds(350, 30, 200, 40);
        cancelReservationButton.setBackground(new Color(204, 60, 60));
        cancelReservationButton.setForeground(Color.WHITE);
        confirmPanel.add(cancelReservationButton);
    }
    /**
     * Initializes the listeners for the buttons and other interactive components in the view.
     * This includes handling cancellation of reservations and navigation back to the user menu.
     */
    private void initListeners() {
        cancelReservationButton.addActionListener(e -> handleCancelReservation());

        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                goBackToUserMenu();
            }
            @Override
            public void mouseEntered(java.awt.event.MouseEvent e) {
                closeButton.setForeground(new Color(255, 80, 80));
            }
            @Override
            public void mouseExited(java.awt.event.MouseEvent e) {
                closeButton.setForeground(Color.WHITE);
            }
        });

        bookMenuButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            if (parentFrame != null) {
                parentFrame.setContentPane(new BookMenuView(loggedUser));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }
    /**
     * Handles the cancellation of a reservation based on the selected row in the table and the entered license plate.
     * Validates the input and performs the cancellation operation.
     */
    private void handleCancelReservation() {
        int selectedRow = reservationsTable.getSelectedRow();
        String enteredPlate = licensePlateField.getText().trim();

        if (selectedRow == -1) {
            JOptionPane.showMessageDialog(this, "Please select a reservation to cancel.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String selectedPlate = (String) reservationsTable.getValueAt(selectedRow, 0);

        if (!Objects.equals(enteredPlate, selectedPlate)) {
            JOptionPane.showMessageDialog(this, "The entered license plate doesn't match the selected reservation.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        Integer slotId = plateToSlotMap.get(selectedPlate);

        if (slotId == null) {
            JOptionPane.showMessageDialog(this, "Cannot find the slot ID for this reservation. Please refresh and try again.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            if (userController.checkUserBooking(selectedPlate)) {
                if (parkingStatusController.cancelSlot(slotId)) {
                    parkingStatusController.createCancelledReservation(slotId, loggedUser.getId(), selectedPlate);
                    JOptionPane.showMessageDialog(this, "Reservation for " + selectedPlate + " has been successfully cancelled.", "Success", JOptionPane.INFORMATION_MESSAGE);
                    refreshReservations();
                    licensePlateField.setText("");
                } else {
                    JOptionPane.showMessageDialog(this, "Failed to cancel the reservation.", "Error", JOptionPane.ERROR_MESSAGE);
                }
            } else {
                JOptionPane.showMessageDialog(this, "No active reservation found for " + selectedPlate, "Error", JOptionPane.ERROR_MESSAGE);
            }
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(this, "Error during cancellation: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
    /**
     * Navigates back to the user menu view.
     * This method is called when the close button is clicked.
     */
    private void goBackToUserMenu() {
        setVisible(false);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.setContentPane(new UserMenuView(loggedUser)); // Navigate back to User Menu
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }
/**
     * Returns the main panel of this view.
     * This method is used to retrieve the main panel for adding to a JFrame or other container.
     *
     * @return The main JPanel containing the RemoveBookMenuView components.
     */
    public JPanel getMainPanel() {
        return mainPanel;
    }

    /**
     * Actualiza la lista de reservas y la tabla después de una cancelación
     */
    private void refreshReservations() {
        try {
            // Recargar los vehículos del usuario
            userBookings = userController.getPanelBookings(loggedUser.getId());
            
            // Recargar los slots reservados
            List<Slot> allSlots = userController.getAllSlotsReserved();
            plateToSlotMap = new HashMap<>();

            // Actualizar el mapa de matrículas a IDs
            if (allSlots != null && !allSlots.isEmpty()) {
                for (Slot slot : allSlots) {
                    if (slot.getVehiclePlate() != null && slot.getBooked()) {
                        plateToSlotMap.put(slot.getVehiclePlate(), slot.getIdSlot());
                    }
                }
            }
            
            // Actualizar la tabla
            DefaultTableModel model = (DefaultTableModel) reservationsTable.getModel();
            model.setRowCount(0); // Limpiar la tabla
            
            if (userBookings != null && !userBookings.isEmpty()) {
                for (Vehicle vehicle : userBookings) {
                    Integer slotId = plateToSlotMap.get(vehicle.getPlate());
                    model.addRow(new Object[]{
                        vehicle.getPlate(),
                        vehicle.getType(),
                        slotId != null ? slotId.toString() : "N/A"
                    });
                }
            }
            
            // Repintar la tabla
            reservationsTable.repaint();
            
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error refreshing reservations: " + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
} 