package presentation.views;

import business.model.User;
import presentation.components.RoundButton;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;

public class UserMenuView extends JPanel {
    private JPanel mainPanel;
    private User loggedUser;

    public UserMenuView(User loggedUser) {
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

        // Menú lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);

        // Título centrado en el menú
        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30); // Ancho igual al panel para centrar
        menuPanel.add(menuTitle);

        // Botones del menú
        // Botón 1: Bookings
        JButton bookingsButton = new RoundButton("Bookings");
        bookingsButton.setBounds(20, 70, 160, 40);
        bookingsButton.setBackground(new Color(150, 130, 200));
        bookingsButton.setForeground(Color.BLACK);
        bookingsButton.setFocusPainted(false);
        menuPanel.add(bookingsButton);

        // Botón 2: Parking Status
        JButton parkingStatusButton = new RoundButton("Parking Status");
        parkingStatusButton.setBounds(20, 130, 160, 40);
        parkingStatusButton.setBackground(new Color(150, 130, 200));
        parkingStatusButton.setForeground(Color.BLACK);
        parkingStatusButton.setFocusPainted(false);
        menuPanel.add(parkingStatusButton);

        parkingStatusButton.addActionListener(e -> {
            // Cambiar el color del botón a amarillo
            parkingStatusButton.setBackground(Color.YELLOW);

            // Crear datos y columnas para la tabla
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
        });


        // Botón 3: Statistics
        JButton statisticsButton = new RoundButton("Statistics");
        statisticsButton.setBounds(20, 190, 160, 40);
        statisticsButton.setBackground(new Color(150, 130, 200));
        statisticsButton.setForeground(Color.BLACK);
        statisticsButton.setFocusPainted(false);
        menuPanel.add(statisticsButton);

        // Botón 4: Enter - Leave parking
        JButton enterLeaveButton = new RoundButton("Enter - Leave parking");
        enterLeaveButton.setBounds(20, 250, 160, 40);
        enterLeaveButton.setBackground(new Color(150, 130, 200));
        enterLeaveButton.setForeground(Color.BLACK);
        enterLeaveButton.setFocusPainted(false);
        menuPanel.add(enterLeaveButton);

        mainPanel.add(menuPanel);
        add(mainPanel);

        enterLeaveButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.setContentPane(new EntryParkingView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
