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
    /**
     * Constructor for ExitParkingView.
     * Initializes the view with the logged user and sets up the UI components.
     *
     * @param loggedUser The user who is currently logged in.
     */
    public ExitParkingView(User loggedUser) {
        try {
            leaveController = new LeaveController(loggedUser);
            this.loggedUser = loggedUser;
        } catch (SQLException e) {
            showErrorMessage(e.getMessage());
            return;
        }

        setLayout(null);

        initializeMainPanel();
        initializeCloseButton();
        initializeUserInteractionPanel();
        initializeMenuPanel();

        add(mainPanel);
    }
    /**
     * Initializes the main panel with a gradient background and title.
     */
    private void initializeMainPanel() {
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
    }
    /**
     * Initializes the close button that allows users to return to the user menu.
     */
    private void initializeCloseButton() {
        JLabel closeButton = new JLabel("\u2716");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 22));
        closeButton.setForeground(Color.BLACK);
        closeButton.setBounds(840, 20, 30, 30);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                switchToUserMenuView();
            }
        });
        mainPanel.add(closeButton);
    }
    /**
     * Initializes the user interaction panel where users can enter their vehicle's license plate.
     */
    private void initializeUserInteractionPanel() {
        JPanel userInteractionPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2 = (Graphics2D) g.create();
                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                GradientPaint gp = new GradientPaint(0, 0, new Color(190, 180, 230), getWidth(), getHeight(), new Color(140, 130, 180));
                g2.setPaint(gp);
                g2.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                g2.setColor(new Color(255, 255, 255, 50));
                g2.setStroke(new BasicStroke(2));
                g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, 30, 30);
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

        leaveActionButton.addActionListener(e -> handleLeaveAction(plateField));
    }
    /**
     * Initializes the menu panel with buttons for entering and leaving parking.
     */
    private void initializeMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);

        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30);
        menuPanel.add(menuTitle);

        JButton enterParkingButton = new RoundButton("Enter Parking");
        enterParkingButton.setBounds(20, 150, 160, 40);
        enterParkingButton.setBackground(new Color(150, 130, 200));
        enterParkingButton.setForeground(Color.BLACK);
        enterParkingButton.setFocusPainted(false);
        enterParkingButton.addActionListener(e -> switchToEntryParkingView());
        menuPanel.add(enterParkingButton);

        JButton leaveParkingButton = new RoundButton("Leave Parking");
        leaveParkingButton.setBounds(20, 230, 160, 40);
        leaveParkingButton.setBackground(new Color(255, 200, 0));
        leaveParkingButton.setForeground(Color.BLACK);
        leaveParkingButton.setFocusPainted(false);
        menuPanel.add(leaveParkingButton);

        mainPanel.add(menuPanel);
    }
    /**
     * Handles the leave action when the user clicks the "LEAVE" button.
     * Validates the input and interacts with the LeaveController to process the exit.
     *
     * @param plateField The text field where the user enters their vehicle's license plate.
     */
    private void handleLeaveAction(JTextField plateField) {
        String plate = plateField.getText();

        try {
            if (plate.isEmpty()) {
                showErrorMessage("Enter the license plate number");
                return;
            }
            if (!leaveController.isValidPlateFormat(plate)) {
                showErrorMessage("Invalid plate format. Must be 3 uppercase letters followed by 3 digits.");
                return;
            }

            String userPlate = leaveController.userPlate(loggedUser, plate);
            if (!"success".equals(userPlate)) {
                showErrorMessage(userPlate);
                return;
            }

            String plateInside = leaveController.isVehicleInside(plate);
            if (!"success".equals(plateInside)) {
                showErrorMessage(plateInside);
                return;
            }

            int slotId = leaveController.getSlotIdByPlate(plate);

            String updateSlot = leaveController.updateSlot(plate);
            if (!"success".equals(updateSlot)) {
                showErrorMessage(updateSlot);
                return;
            }

            leaveController.registerExitLogs("leave", plate, slotId);
            JOptionPane.showMessageDialog(this, "The vehicle is outside!", "Exit Parking", JOptionPane.INFORMATION_MESSAGE);

        } catch (SQLException ex) {
            ex.printStackTrace();
            showErrorMessage("Database error: " + ex.getMessage());
        }
    }
    /**
     * Switches the view to the user menu.
     */
    private void switchToUserMenuView() {
        setVisible(false);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new UserMenuView(loggedUser));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
/**
     * Switches the view to the entry parking view.
     */
    private void switchToEntryParkingView() {
        setVisible(false);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new EntryParkingView(loggedUser));
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    /**
     * Displays an error message dialog.
     *
     * @param message The error message to display.
     */
    private void showErrorMessage(String message) {
        JOptionPane.showMessageDialog(this, message, "Error", JOptionPane.ERROR_MESSAGE);
    }
}