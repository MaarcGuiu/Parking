package presentation.views;

import business.model.Slot;
import presentation.components.RoundButton;
import presentation.components.RoundTextField;
import presentation.controllers.AdminController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;

public class AdminMenuView extends JPanel {
    private JPanel mainPanel;
    private final AdminController adminController = new AdminController();

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

        // MODIFICAR EL COLOR DE LOS BOTONES DEL MENU
        List<JButton> menuButtons = new ArrayList<>();
        menuButtons.add(createButton);
        menuButtons.add(editButton);
        menuButtons.add(deleteButton);
        menuButtons.add(statisticsButton);
        menuButtons.add(parkingStatusButton);

        Color defaultButtonColor = new Color(150, 130, 200);

        Runnable resetMainPanel = () -> {
            for (JButton btn : menuButtons) {
                btn.setBackground(defaultButtonColor);
            }
            mainPanel.removeAll();
            mainPanel.add(menuPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        };

        parkingStatusButton.addActionListener(e -> {
            boolean isAdmin = true;
            if (parkingStatusButton != null) {
                parkingStatusButton.setBackground(Color.YELLOW);
            }
            ParkingStatusView.show(mainPanel, menuPanel, resetMainPanel, isAdmin);
        });


        createButton.addActionListener(e -> {
            resetMainPanel.run();
            createButton.setBackground(Color.YELLOW);
            mainPanel.removeAll();
            mainPanel.add(menuPanel);

            JPanel formPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0, new Color(190, 180, 230), getWidth(), getHeight(), new Color(140, 130, 180));
                    g2d.setPaint(gp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                }
            };
            formPanel.setLayout(null);
            formPanel.setBounds(250, 80, 400, 300);
            formPanel.setOpaque(false);

            JLabel titleLabel = new JLabel("CREATE NEW SLOT");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setBounds(270, 30, 300, 30);
            mainPanel.add(titleLabel);

            JLabel idLabel = new JLabel("ID:");
            idLabel.setFont(new Font("Arial", Font.BOLD, 14));
            idLabel.setBounds(40, 30, 100, 25);
            formPanel.add(idLabel);

            RoundTextField idField = new RoundTextField(15);
            idField.setBounds(140, 30, 200, 30);
            formPanel.add(idField);

            JLabel floorLabel = new JLabel("FLOOR:");
            floorLabel.setFont(new Font("Arial", Font.BOLD, 14));
            floorLabel.setBounds(40, 80, 100, 25);
            formPanel.add(floorLabel);

            JComboBox<String> floorCombo = new JComboBox<>(new String[]{"1", "2", "3"});
            floorCombo.setBounds(140, 80, 200, 30);
            formPanel.add(floorCombo);

            JLabel vehicleLabel = new JLabel("VEHICLE:");
            vehicleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            vehicleLabel.setBounds(40, 130, 100, 25);
            formPanel.add(vehicleLabel);

            JComboBox<String> vehicleCombo = new JComboBox<>(new String[]{"Car", "Motorbike", "Truck"});
            vehicleCombo.setBounds(140, 130, 200, 30);
            formPanel.add(vehicleCombo);

            RoundButton confirmButton = new RoundButton("CREATE");
            confirmButton.setBounds(120, 200, 160, 40);
            confirmButton.setBackground(new Color(210, 160, 20));
            confirmButton.setForeground(Color.WHITE);
            confirmButton.setFocusPainted(false);
            formPanel.add(confirmButton);

            confirmButton.addActionListener(ev -> {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int floor = Integer.parseInt((String) floorCombo.getSelectedItem());
                    String vehicle = (String) vehicleCombo.getSelectedItem();

                    Slot slot = new Slot(vehicle, id, floor);
                    adminController.createSlot(slot);

                    JOptionPane.showMessageDialog(mainPanel,
                            "Slot creado:\nID: " + id + "\nFloor: " + floor + "\nVehicle: " + vehicle,
                            "Success", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel,
                            "ID y Floor deben ser números válidos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            mainPanel.add(formPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        editButton.addActionListener(e -> {
            resetMainPanel.run();
            editButton.setBackground(Color.YELLOW);

            JLabel titleLabel = new JLabel("EDIT SLOT");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setBounds(270, 30, 300, 30);
            mainPanel.add(titleLabel);

            JPanel formPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0, new Color(190, 180, 230), getWidth(), getHeight(), new Color(140, 130, 180));
                    g2d.setPaint(gp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                }
            };
            formPanel.setLayout(null);
            formPanel.setBounds(250, 80, 400, 350);
            formPanel.setOpaque(false);

            JLabel idLabel = new JLabel("ID:");
            idLabel.setFont(new Font("Arial", Font.BOLD, 14));
            idLabel.setBounds(40, 30, 100, 25);
            formPanel.add(idLabel);

            RoundTextField idField = new RoundTextField(15);
            idField.setBounds(140, 30, 200, 30);
            formPanel.add(idField);

            JLabel occupiedLabel = new JLabel("Is Occupied:");
            occupiedLabel.setFont(new Font("Arial", Font.BOLD, 14));
            occupiedLabel.setBounds(40, 80, 100, 25);
            formPanel.add(occupiedLabel);

            JComboBox<String> occupiedCombo = new JComboBox<>(new String[]{"Yes", "No"});
            occupiedCombo.setBounds(140, 80, 200, 30);
            formPanel.add(occupiedCombo);

            JLabel floorLabel = new JLabel("FLOOR:");
            floorLabel.setFont(new Font("Arial", Font.BOLD, 14));
            floorLabel.setBounds(40, 130, 100, 25);
            formPanel.add(floorLabel);

            JComboBox<Integer> floorCombo = new JComboBox<>(new Integer[]{1, 2, 3});
            floorCombo.setBounds(140, 130, 200, 30);
            formPanel.add(floorCombo);

            JLabel vehicleLabel = new JLabel("VEHICLE:");
            vehicleLabel.setFont(new Font("Arial", Font.BOLD, 14));
            vehicleLabel.setBounds(40, 180, 100, 25);
            formPanel.add(vehicleLabel);

            JComboBox<String> vehicleCombo = new JComboBox<>(new String[]{"Car", "Motorbike", "Truck"});
            vehicleCombo.setBounds(140, 180, 200, 30);
            formPanel.add(vehicleCombo);

            RoundButton editConfirmButton = new RoundButton("EDIT");
            editConfirmButton.setBounds(120, 230, 160, 40);
            editConfirmButton.setBackground(new Color(210, 160, 20));
            editConfirmButton.setForeground(Color.WHITE);
            editConfirmButton.setFocusPainted(false);
            formPanel.add(editConfirmButton);

            editConfirmButton.addActionListener(ev -> {
                try {
                    int id = Integer.parseInt(idField.getText());
                    int occupied = occupiedCombo.getSelectedItem().equals("Yes") ? 1 : 0;
                    int floor = (Integer) floorCombo.getSelectedItem();
                    String vehicle = (String) vehicleCombo.getSelectedItem();

                    Slot updatedSlot = new Slot(vehicle, id, floor, occupied); // Crear objeto Slot actualizado
                    boolean updated = adminController.editSlot(updatedSlot); // Usar el método correcto

                    if (updated) {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Slot actualizado:\nID: " + id + "\nFloor: " + floor + "\nVehicle: " + vehicle,
                                "Edited", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel,
                                "No se encontró un slot con ese ID.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel,
                            "ID y Floor deben ser números válidos.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            mainPanel.add(formPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        deleteButton.addActionListener(e -> {
            resetMainPanel.run();
            deleteButton.setBackground(Color.YELLOW);

            JLabel titleLabel = new JLabel("DELETE SLOT");
            titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
            titleLabel.setForeground(Color.BLACK);
            titleLabel.setBounds(270, 30, 300, 30);
            mainPanel.add(titleLabel);

            JPanel formPanel = new JPanel() {
                @Override
                protected void paintComponent(Graphics g) {
                    super.paintComponent(g);
                    Graphics2D g2d = (Graphics2D) g;
                    g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                    GradientPaint gp = new GradientPaint(0, 0, new Color(190, 180, 230), getWidth(), getHeight(), new Color(140, 130, 180));
                    g2d.setPaint(gp);
                    g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
                }
            };
            formPanel.setLayout(null);
            formPanel.setBounds(250, 80, 400, 200);
            formPanel.setOpaque(false);

            JLabel idLabel = new JLabel("ID:");
            idLabel.setFont(new Font("Arial", Font.BOLD, 14));
            idLabel.setBounds(40, 40, 100, 25);
            formPanel.add(idLabel);

            RoundTextField idField = new RoundTextField(15);
            idField.setBounds(140, 40, 200, 30);
            formPanel.add(idField);

            RoundButton deleteConfirmButton = new RoundButton("Delete");
            deleteConfirmButton.setBounds(120, 100, 160, 40);
            deleteConfirmButton.setBackground(new Color(210, 160, 20));
            deleteConfirmButton.setForeground(Color.WHITE);
            deleteConfirmButton.setFocusPainted(false);
            formPanel.add(deleteConfirmButton);

            deleteConfirmButton.addActionListener(ev -> {
                try {
                    int id = Integer.parseInt(idField.getText());
                    boolean deleted = adminController.deleteSlot(id);
                    if (deleted) {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Slot eliminado: ID " + id,
                                "Deleted", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel,
                                "No se encontró un slot con ese ID.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel,
                            "ID debe ser un número válido.",
                            "Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            mainPanel.add(formPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        });

        mainPanel.add(menuPanel);
        add(mainPanel);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
    private void showReservationPopUp() {

        JDialog dialog = new JDialog((Frame) null, "Reservation Details", true);
        dialog.setSize(300, 200);
        dialog.setLayout(null);
        dialog.setLocationRelativeTo(null); // Center the dialog on the screen

        // Add labels and fields
        JLabel vehicleTypeLabel = new JLabel("Vehicle type: car");
        vehicleTypeLabel.setBounds(20, 20, 200, 20);
        dialog.add(vehicleTypeLabel);

        JLabel userNameLabel = new JLabel("User name: Admin");
        userNameLabel.setBounds(20, 50, 200, 20);
        dialog.add(userNameLabel);

        JLabel userEmailLabel = new JLabel("User email: 123@123.com");
        userEmailLabel.setBounds(20, 80, 200, 20);
        dialog.add(userEmailLabel);

        JButton okButton = new JButton("Ok");
        okButton.setBounds(30, 120, 50, 30);
        dialog.add(okButton);

        JButton cancelButton = new JButton("Cancel reservation");
        cancelButton.setBounds(120, 120, 150, 30);
        dialog.add(cancelButton);

        // Add action listener to the button
        cancelButton.addActionListener(e -> {
            System.out.println("Reservation canceled");
            dialog.dispose(); // Close the dialog
        });

        // Show the dialog
        dialog.setVisible(true);
    }
}
