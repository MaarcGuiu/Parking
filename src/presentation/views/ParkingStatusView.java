package presentation.views;

import business.model.Slot;
import presentation.components.RoundButton;
import presentation.controllers.AdminController;
import presentation.controllers.ParkingStatusController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Parking status view.
 */
public class ParkingStatusView {
    private static ParkingStatusController parkingStatusController;
    private static AdminController adminController;

    /**
     * Instantiates a new Parking status view.
     */
    public ParkingStatusView() {
    }

    /**
     * Show.
     *
     * @param mainPanel      the main panel
     * @param menuPanel      the menu panel
     * @param resetMainPanel the reset main panel
     * @param isAdmin        the is admin
     */
    public static void show(JPanel mainPanel, JPanel menuPanel, Runnable resetMainPanel, boolean isAdmin) {
        try {
            initControllers();
            resetMainPanel.run();

            List<Slot> slots = parkingStatusController.getAllSlots();

            JTable table = createTable(slots);
            JScrollPane scrollPane = createScrollPane(table);
            JLabel slotCounterLabel = createSlotCounterLabel();


            setupMainPanel(mainPanel, menuPanel, scrollPane, slotCounterLabel);

            addTableClickListener(table, slots, mainPanel, menuPanel, scrollPane, isAdmin);

        } catch (SQLException | ClassNotFoundException ex) {
            JOptionPane.showMessageDialog(null, ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private static void initControllers() throws SQLException, ClassNotFoundException {
        parkingStatusController = new ParkingStatusController();
        adminController = new AdminController();
    }

    private static JTable createTable(List<Slot> slots) {
        String[][] data = new String[slots.size()][5];
        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            data[i][0] = String.valueOf(slot.getIdSlot());
            data[i][1] = String.valueOf(slot.getFloor());
            data[i][2] = slot.getAvailabilityState() == 1 ? "Occupied" : "Free";
            data[i][3] = slot.getBooked() ? "Reserved" : "Not reserved";
            data[i][4] = slot.getVehiclePlate() != null ? slot.getVehiclePlate() : "";
        }
        String[] columns = {"Code", "Floor", "Current Status", "Reservation Status", "Vehicle Plate"};

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
        styleTable(table);

        return table;
    }

    private static void updateSlotCounterLabel(JLabel slotCounterLabel) {
        try {
            int total = parkingStatusController.getAllSlots().size();
            int occupied = 0;
            for (Slot slot : parkingStatusController.getAllSlots()) {
                if (slot.getAvailabilityState() == 1) {
                    occupied++;
                }
            }
            slotCounterLabel.setText("Plazas ocupadas: " + occupied + "/" + total);
        } catch (SQLException e) {
            slotCounterLabel.setText("Error al obtener los datos");
            e.printStackTrace();
        }
    }


    private static void styleTable(JTable table) {
        JTableHeader header = table.getTableHeader();
        header.setFont(new Font("Arial", Font.BOLD, 14));
        header.setBackground(new Color(70, 60, 130));
        header.setForeground(Color.WHITE);

        DefaultTableCellRenderer cellRenderer = new DefaultTableCellRenderer();
        cellRenderer.setHorizontalAlignment(SwingConstants.CENTER);
        cellRenderer.setBackground(new Color(230, 230, 250));
        cellRenderer.setForeground(Color.BLACK);
        table.setDefaultRenderer(Object.class, cellRenderer);

        table.setRowHeight(30);
        table.setShowGrid(true);
        table.setGridColor(Color.GRAY);
    }

    private static JScrollPane createScrollPane(JTable table) {
        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(220, 50, 600, 400);
        return scrollPane;
    }

    private static JLabel createSlotCounterLabel() {
        JLabel slotCounterLabel = new JLabel();
        slotCounterLabel.setFont(new Font("Arial", Font.BOLD, 16));
        slotCounterLabel.setBounds(220, 10, 600, 30);
        slotCounterLabel.setHorizontalAlignment(SwingConstants.CENTER);
        slotCounterLabel.setForeground(Color.WHITE);
        updateSlotCounterLabel(slotCounterLabel);
        return slotCounterLabel;
    }


    private static void setupMainPanel(JPanel mainPanel, JPanel menuPanel, JScrollPane scrollPane, JLabel slotCounterLabel) {
        mainPanel.removeAll();
        mainPanel.setLayout(null);

        mainPanel.add(menuPanel);

        updateSlotCounterLabel(slotCounterLabel);
        mainPanel.add(slotCounterLabel);

        scrollPane.setBounds(220, 50, 600, 400);
        mainPanel.add(scrollPane);

        mainPanel.revalidate();
        mainPanel.repaint();
    }



    private static void addTableClickListener(JTable table, List<Slot> slots, JPanel mainPanel,
                                              JPanel menuPanel, JScrollPane scrollPane, boolean isAdmin) {
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row == -1) return;

                String code = (String) table.getValueAt(row, 0);
                String floor = (String) table.getValueAt(row, 1);
                String vehicleType = "";
                String ownerName = "";
                String ownerEmail = "";

                for (Slot slot : slots) {
                    if (slot.getIdSlot() == Integer.parseInt(code)) {
                        vehicleType = slot.getVehicle();
                        if (slot.getVehicleObject() != null) {
                            ownerName = slot.getVehicleObject().getVehicleOwner().getUserName();
                            ownerEmail = slot.getVehicleObject().getVehicleOwner().getEmail();
                        }
                        break;
                    }
                }

                boolean isReserved = "Reserved".equals((String) table.getValueAt(row, 3));

                showSlotDetails(mainPanel, menuPanel, scrollPane, isAdmin, code, floor, vehicleType, ownerName, ownerEmail, isReserved, slots, table);
            }
        });
    }

    private static void showSlotDetails(JPanel mainPanel, JPanel menuPanel, JScrollPane scrollPane, boolean isAdmin,
                                        String code, String floor, String vehicleType, String ownerName, String ownerEmail, boolean isReserved,
                                        List<Slot> slots, JTable table) {
        mainPanel.removeAll();
        mainPanel.add(menuPanel);

        JPanel gradientPanel = createGradientPanel();
        addLabelsToDetailPanel(gradientPanel, code, floor, vehicleType, ownerName, ownerEmail, isReserved);

        if (isReserved && isAdmin) {
            addCancelButton(gradientPanel, mainPanel, menuPanel, scrollPane, code, slots, table);
        }

        RoundButton backButton = createBackButton(mainPanel, menuPanel, scrollPane);
        JPanel detailPanel = new JPanel();
        detailPanel.setLayout(null);
        detailPanel.setOpaque(false);
        detailPanel.setBounds(220, 50, 600, 400);
        detailPanel.add(backButton);
        detailPanel.add(gradientPanel);

        mainPanel.add(detailPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private static JPanel createGradientPanel() {
        JPanel gradientPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(190, 180, 230),
                        getWidth(), getHeight(), new Color(140, 130, 180));
                g2d.setPaint(gp);
                g2d.fillRoundRect(0, 0, getWidth(), getHeight(), 30, 30);
            }
        };
        gradientPanel.setLayout(null);
        gradientPanel.setBounds(50, 30, 400, 300);
        gradientPanel.setOpaque(false);
        return gradientPanel;
    }

    private static void addLabelsToDetailPanel(JPanel panel, String code, String floor, String vehicleType,
                                               String ownerName, String ownerEmail, boolean isReserved) {
        JLabel titleLabel = new JLabel("Detall de la Plaça", SwingConstants.CENTER);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(40, 20, 320, 30);
        panel.add(titleLabel);

        JLabel codeLabel = new JLabel("Codi: " + code);
        codeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        codeLabel.setForeground(Color.BLACK);
        codeLabel.setBounds(40, 70, 320, 25);
        panel.add(codeLabel);

        JLabel floorLabel = new JLabel("Pis: " + floor);
        floorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        floorLabel.setForeground(Color.BLACK);
        floorLabel.setBounds(40, 100, 320, 25);
        panel.add(floorLabel);

        JLabel typeLabel = new JLabel("Tipus de vehicle: " + vehicleType);
        typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
        typeLabel.setForeground(Color.BLACK);
        typeLabel.setBounds(40, 130, 320, 25);
        panel.add(typeLabel);

        if (isReserved) {
            JLabel userLabel = new JLabel("Usuari: " + ownerName);
            userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            userLabel.setForeground(Color.BLACK);
            userLabel.setBounds(40, 160, 320, 25);
            panel.add(userLabel);

            JLabel emailLabel = new JLabel("Email: " + ownerEmail);
            emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
            emailLabel.setForeground(Color.BLACK);
            emailLabel.setBounds(40, 190, 320, 25);
            panel.add(emailLabel);
        }
    }

    private static void addCancelButton(JPanel panel, JPanel mainPanel, JPanel menuPanel, JScrollPane scrollPane,
                                        String code, List<Slot> slots, JTable table) {
        RoundButton cancelButton = new RoundButton("Cancel·lar reserva");
        cancelButton.setBounds(40, 230, 160, 40);
        cancelButton.setBackground(new Color(210, 160, 20));
        cancelButton.setForeground(Color.WHITE);
        cancelButton.setFocusPainted(false);

        cancelButton.addActionListener(ev -> {
            try {
                Slot targetSlot = null;
                for (Slot slot : slots) {
                    if (slot.getIdSlot() == Integer.parseInt(code)) {
                        targetSlot = slot;
                        break;
                    }
                }

                if (targetSlot != null) {
                    if (targetSlot.getAvailabilityState() == 0) {
                        if (parkingStatusController.cancelSlot(targetSlot.getIdSlot())) {
                            parkingStatusController.createCancelledReservation(targetSlot.getIdSlot(), targetSlot.getVehicleObject().getUser().getId(), targetSlot.getVehiclePlate());

                            if (parkingStatusController.getFreeUnbookedSlots()) {
                                Slot newReservedSlot = parkingStatusController.getSlot(
                                        parkingStatusController.setUserNewReservationSlot(targetSlot.getIdSlot(), targetSlot.getVehiclePlate())
                                );
                                if (newReservedSlot != null) {
                                    newReservedSlot.setVehicleObject(targetSlot.getVehicleObject());
                                    newReservedSlot.setVehiclePlate(targetSlot.getVehiclePlate());
                                    adminController.editSlot(newReservedSlot);
                                }
                            }

                            JOptionPane.showMessageDialog(mainPanel, "Reserva cancel·lada. L’usuari serà notificat en el proper inici de sessió.");

                            List<Slot> updatedSlots = parkingStatusController.getAllSlots();
                            updateTableModel(updatedSlots, slots, table);
                            show(mainPanel, menuPanel, () -> {}, false); // recarregar vista (passa un Runnable buit, isAdmin pot ser gestionat segons necessitat)
                        } else {
                            JOptionPane.showMessageDialog(mainPanel, "Error al cancelar la reserva.");
                        }
                    } else {
                        JOptionPane.showMessageDialog(mainPanel, "Error al cancelar la reserva. El espacio esta ocupado actualmente.");
                    }
                }
            } catch (IllegalArgumentException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error de validació: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error de base de dades: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(mainPanel, "Error inesperat: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        panel.add(cancelButton);
    }

    private static void updateTableModel(List<Slot> updatedSlots, List<Slot> slots, JTable table) {
        String[][] updatedData = new String[updatedSlots.size()][5];
        for (int i = 0; i < updatedSlots.size(); i++) {
            Slot slot = updatedSlots.get(i);
            updatedData[i][0] = String.valueOf(slot.getIdSlot());
            updatedData[i][1] = String.valueOf(slot.getFloor());
            updatedData[i][2] = slot.getAvailabilityState() == 1 ? "Occupied" : "Free";
            updatedData[i][3] = slot.getBooked() ? "Reserved" : "Not reserved";
            updatedData[i][4] = slot.getVehiclePlate() != null ? slot.getVehiclePlate() : "";
        }

        String[] columns = {"Code", "Floor", "Current Status", "Reservation Status", "Vehicle Plate"};

        DefaultTableModel updatedModel = new DefaultTableModel(updatedData, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        slots.clear();
        slots.addAll(updatedSlots);
        table.setModel(updatedModel);
        table.revalidate();
        table.repaint();
    }

    private static RoundButton createBackButton(JPanel mainPanel, JPanel menuPanel, JScrollPane scrollPane) {
        RoundButton backButton = new RoundButton("←");
        backButton.setBounds(20, 20, 50, 30);
        backButton.setBackground(new Color(150, 130, 200));
        backButton.setForeground(Color.BLACK);
        backButton.setFocusPainted(false);
        backButton.addActionListener(ev -> {
            mainPanel.removeAll();
            mainPanel.add(menuPanel);
            mainPanel.add(scrollPane);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
        return backButton;
    }
}