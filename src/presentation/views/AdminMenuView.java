package presentation.views;

import business.model.User;
import presentation.components.RoundButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class AdminMenuView extends JPanel {
    private JPanel mainPanel;

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

        parkingStatusButton.addActionListener(e -> {
            parkingStatusButton.setBackground(Color.YELLOW);

            String[][] data = {
                    {"###", "1", "0000", "0", "0"},
                    {"###", "2", "0000", "0", "0"},
                    {"###", "3", "0000", "0", "0"}
            };
            String[] columns = {"Code", "Floor", "License Plate", "Current Status", "Reservation Status"};

            javax.swing.table.DefaultTableModel model = new javax.swing.table.DefaultTableModel(data, columns) {
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
            mainPanel.add(scrollPane);
            mainPanel.revalidate();
            mainPanel.repaint();


            table.addMouseListener(new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent evt) {
                    int row = table.getSelectedRow();
                    int column = table.getSelectedColumn();
                    if (row != -1 && column != -1) {
                        Object value = table.getValueAt(row, column);
                        //TODO: Si es admin anar a una view de detall
                        showReservationPopUp();
                    }
                }
            });
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
