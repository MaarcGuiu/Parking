package presentation.views;

import business.model.Slot;
import presentation.components.RoundButton;
import presentation.controllers.LoginController;
import presentation.controllers.ParkingStatusController;

import javax.swing.*;
import javax.swing.table.*;
import java.awt.*;
import java.awt.event.*;
import java.util.List;

public class ParkingStatusView {
    private static ParkingStatusController parkingStatusController;

    public static void show(JPanel mainPanel, JPanel menuPanel, Runnable resetMainPanel, boolean isAdmin) {
        parkingStatusController = new ParkingStatusController();
        resetMainPanel.run();

        List<Slot> slots = parkingStatusController.getAllSlots();

        String[][] data = new String[slots.size()][5];

        for (int i = 0; i < slots.size(); i++) {
            Slot slot = slots.get(i);
            data[i][0] = slot.getIdSlot()+"";
            data[i][1] = String.valueOf(slot.getFloor());
            data[i][2] = slot.getAvailabilityState() == 1 ? "Occupied" : "Free";
            data[i][3] = slot.getBooked() ? "Reserved" : "Not reserved";
            data[i][4] = slot.getVehiclePlate() != null ? slot.getVehiclePlate() : "";
        }
        // Code =idslot,Floor= plant, availabilityState= ocupado o no,booked= reservado o no, vehiclePlate= vehicle_plate
        String[] columns = {"Code", "Floor", "Current Status", "Reservation Status", "Vehicle Plate"};

        DefaultTableModel model = new DefaultTableModel(data, columns) {
            @Override
            public boolean isCellEditable(int row, int column) {
                return false;
            }
        };

        JTable table = new JTable(model);
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

        JScrollPane scrollPane = new JScrollPane(table);
        scrollPane.setBounds(220, 50, 600, 400);

        mainPanel.removeAll();
        mainPanel.add(menuPanel);
        mainPanel.add(scrollPane);
        mainPanel.revalidate();
        mainPanel.repaint();

        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                int row = table.getSelectedRow();
                if (row != -1) {
                    String code = (String) table.getValueAt(row, 0);
                    String floor = (String) table.getValueAt(row, 1);
                    String vehicleType = "";
                    String ownerName = "";
                    String ownerEmail = "";
                    for (Slot slot : slots) {
                        if (slot.getIdSlot() == Integer.parseInt(code)) {
                            vehicleType = slot.getVehicleObject().getType();
                            ownerName = slot.getVehicleObject().getVehicleOwner().getUserName();
                            ownerEmail =  slot.getVehicleObject().getVehicleOwner().getEmail();
                        }
                    }
                    boolean isReserved = "Reserved".equals((String) table.getValueAt(row, 3));

                    mainPanel.removeAll();
                    mainPanel.add(menuPanel);

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

                    JLabel titleLabel = new JLabel("Detall de la Plaça", SwingConstants.CENTER);
                    titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
                    titleLabel.setForeground(Color.BLACK);
                    titleLabel.setBounds(40, 20, 320, 30);
                    gradientPanel.add(titleLabel);

                    JLabel codeLabel = new JLabel("Codi: " + code);
                    codeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    codeLabel.setForeground(Color.BLACK);
                    codeLabel.setBounds(40, 70, 320, 25);
                    gradientPanel.add(codeLabel);

                    JLabel floorLabel = new JLabel("Pis: " + floor);
                    floorLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    floorLabel.setForeground(Color.BLACK);
                    floorLabel.setBounds(40, 100, 320, 25);
                    gradientPanel.add(floorLabel);

                    JLabel typeLabel = new JLabel("Tipus de vehicle: " + vehicleType);
                    typeLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                    typeLabel.setForeground(Color.BLACK);
                    typeLabel.setBounds(40, 130, 320, 25);
                    gradientPanel.add(typeLabel);

                    if (isReserved) {
                        JLabel userLabel = new JLabel("Usuari: " +  ownerName);
                        userLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                        userLabel.setForeground(Color.BLACK);
                        userLabel.setBounds(40, 160, 320, 25);
                        gradientPanel.add(userLabel);

                        JLabel emailLabel = new JLabel("Email: " + ownerEmail);
                        emailLabel.setFont(new Font("Arial", Font.PLAIN, 16));
                        emailLabel.setForeground(Color.BLACK);
                        emailLabel.setBounds(40, 190, 320, 25);
                        gradientPanel.add(emailLabel);

                        if (isAdmin) {
                            RoundButton cancelButton = new RoundButton("Cancel·lar reserva");
                            cancelButton.setBounds(40, 230, 160, 40);
                            cancelButton.setBackground(new Color(210, 160, 20));
                            cancelButton.setForeground(Color.WHITE);
                            cancelButton.setFocusPainted(false);
                            cancelButton.addActionListener(ev -> {
                                JOptionPane.showMessageDialog(mainPanel, "Reserva cancel·lada. L’usuari serà notificat en el proper inici de sessió.");
                                model.setValueAt("0", row, 4);
                                model.setValueAt("", row, 5);
                            });
                            gradientPanel.add(cancelButton);
                        }
                    }

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
            }
        });
    }
}
