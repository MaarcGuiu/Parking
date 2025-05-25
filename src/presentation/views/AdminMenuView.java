package presentation.views;

import business.ParkingOccupancyManager;
import business.model.Slot;
import persistence.LogsSqlDao;
import presentation.components.RoundButton;
import presentation.components.RoundTextField;
import presentation.controllers.AdminController;
import presentation.controllers.ParkingOccupancyController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicReference;

public class AdminMenuView extends JPanel implements OccupancyChangeListener {
    private JPanel mainPanel;
    private JPanel menuPanel;
    private JButton createButton;
    private JButton editButton;
    private JButton deleteButton;
    private JButton statisticsButton;
    private JButton parkingStatusButton;
    private JButton settingsButton;

    private AdminController adminController;
    private persistence.LogsSqlDao LogsSqlDao;
    private business.ParkingOccupancyManager ParkingOccupancyManager = new ParkingOccupancyManager(LogsSqlDao);
    private final ParkingOccupancyController parkingOccupancyService = new ParkingOccupancyController(ParkingOccupancyManager);

    private AtomicReference<int[]> occupancyDataRef = new AtomicReference<>(new int[60]);

    private JPanel timeBarChartPanel;

    public AdminMenuView() {
        initializeControllers();
        principalAdminMenu();
    }

    private void initializeControllers() {
        try {
            adminController = new AdminController();
            LogsSqlDao = new LogsSqlDao();
        } catch (SQLException | ClassNotFoundException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void principalAdminMenu() {
        setupFrame();
        setupMainPanel();
        setupMenuPanel();
        setupMenuButtons();
        resetMainPanel();
        setupButtonActions();

        mainPanel.add(menuPanel);
        add(mainPanel);
    }

    private void setupFrame() {
        setLayout(null);
        parkingOccupancyService.addOccupancyChangeListener(this);
    }

    private void setupMainPanel() {
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
    }

    private void setupMenuPanel() {
        // Menú lateral
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);

        // Título centrado en el menú
        JLabel menuTitle = new JLabel("ADMIN MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30); // Ancho igual al panel para centrar
        menuPanel.add(menuTitle);
    }

    private void setupMenuButtons() {
        createButton = new RoundButton("Create");
        createButton.setBounds(20, 70, 160, 40);
        createButton.setBackground(new Color(150, 130, 200));
        createButton.setForeground(Color.BLACK);
        createButton.setFocusPainted(false);
        menuPanel.add(createButton);

        editButton = new RoundButton("Edit");
        editButton.setBounds(20, 130, 160, 40);
        editButton.setBackground(new Color(150, 130, 200));
        editButton.setForeground(Color.BLACK);
        editButton.setFocusPainted(false);
        menuPanel.add(editButton);

        deleteButton = new RoundButton("Delete");
        deleteButton.setBounds(20, 190, 160, 40);
        deleteButton.setBackground(new Color(150, 130, 200));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusPainted(false);
        menuPanel.add(deleteButton);

        statisticsButton = new RoundButton("Statistics");
        statisticsButton.setBounds(20, 250, 160, 40);
        statisticsButton.setBackground(new Color(150, 130, 200));
        statisticsButton.setForeground(Color.BLACK);
        statisticsButton.setFocusPainted(false);
        menuPanel.add(statisticsButton);

        parkingStatusButton = new RoundButton("Parking Status");
        parkingStatusButton.setBounds(20, 310, 160, 40);
        parkingStatusButton.setBackground(new Color(150, 130, 200));
        parkingStatusButton.setForeground(Color.BLACK);
        parkingStatusButton.setFocusPainted(false);
        menuPanel.add(parkingStatusButton);

        settingsButton = new RoundButton("Log out");
        settingsButton.setBounds(20, 370, 160, 40);
        settingsButton.setBackground(new Color(150, 130, 200));
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setFocusPainted(false);
        menuPanel.add(settingsButton);
    }

    private void resetMainPanel() {
        List<JButton> menuButtons = new ArrayList<>();
        menuButtons.add(createButton);
        menuButtons.add(editButton);
        menuButtons.add(deleteButton);
        menuButtons.add(statisticsButton);
        menuButtons.add(parkingStatusButton);
        menuButtons.add(settingsButton);

        Color defaultButtonColor = new Color(150, 130, 200);

        for (JButton btn : menuButtons) {
            btn.setBackground(defaultButtonColor);
        }
        mainPanel.removeAll();
        mainPanel.add(menuPanel);
        mainPanel.revalidate();
        mainPanel.repaint();
    }

    private void setupButtonActions() {
        setupParkingStatusButtonAction();
        setupCreateButtonAction();
        setupEditButtonAction();
        setupDeleteButtonAction();
        setupSettingButtonAction();
        setupStatisticsButton();
    }

    private void setupParkingStatusButtonAction() {
        parkingStatusButton.addActionListener(e -> {
            boolean isAdmin = true;
            ParkingStatusView.show(mainPanel, menuPanel, this::resetMainPanel, isAdmin);
            parkingStatusButton.setBackground(Color.YELLOW);
        });
    }

    private void setupCreateButtonAction() {
        createButton.addActionListener(e -> {
            resetMainPanel();
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
                    String text = "";
                    text = adminController.createSlot(slot);

                    JOptionPane.showMessageDialog(mainPanel,
                            text,
                            "Create", JOptionPane.INFORMATION_MESSAGE);
                    if (text.contains("created")) {
                        boolean isAdmin = true;
                        if (parkingStatusButton != null) {
                            parkingStatusButton.setBackground(Color.YELLOW);
                        }
                        ParkingStatusView.show(mainPanel, menuPanel, this::resetMainPanel, isAdmin);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"ID y Floor deben ser números válidos.","Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"Error de validació: " + ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"Error de base de dades: " + ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            mainPanel.add(formPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }


    private void setupEditButtonAction() {
        editButton.addActionListener(e -> {
            resetMainPanel();
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
                    int floor = (Integer) floorCombo.getSelectedItem();
                    String vehicle = (String) vehicleCombo.getSelectedItem();

                    Slot updatedSlot = new Slot(vehicle, id, floor, 0); // Crear objeto Slot actualizado
                    boolean updated = adminController.editSlot(updatedSlot); // Usar el método correcto

                    if (updated) {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Slot actualizado:\nID: " + id + "\nFloor: " + floor + "\nVehicle: " + vehicle,
                                "Edited", JOptionPane.INFORMATION_MESSAGE);
                    } else {
                        JOptionPane.showMessageDialog(mainPanel,
                                "Error the id of the slot may don t exsists or this slot is occupied rhight now.",
                                "Error", JOptionPane.ERROR_MESSAGE);
                    }
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"ID y Floor deben ser números válidos.","Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"Error de validació: " + ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"Error de base de dades: " + ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            mainPanel.add(formPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    private void setupDeleteButtonAction() {
        deleteButton.addActionListener(e -> {
            resetMainPanel();
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
                    String deleted = adminController.deleteSlot(id);
                    JOptionPane.showMessageDialog(mainPanel,
                            deleted + " " + id,
                            "Deleted", JOptionPane.INFORMATION_MESSAGE);
                } catch (NumberFormatException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"ID debe ser un número válido.","Error", JOptionPane.ERROR_MESSAGE);
                } catch (IllegalArgumentException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"Error de validació: " + ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                } catch (SQLException ex) {
                    JOptionPane.showMessageDialog(mainPanel,"Error de base de dades: " + ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
                }
            });

            mainPanel.add(formPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        });
    }

    private void setupSettingButtonAction() {
        settingsButton.addActionListener(e -> {
            settingsButton.setBackground(Color.YELLOW);

            setVisible(true);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            handleLogout();
            parentFrame.revalidate();
            parentFrame.repaint();
        });
    }

    private void setupStatisticsButton() {
        statisticsButton.addActionListener(e -> {
            try {
                resetMainPanel();
                statisticsButton.setBackground(Color.YELLOW);
                initializeStatisticsView();
            } catch (Exception ex) {
                JOptionPane.showMessageDialog(this, ex.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
            }
        });
    }

    @Override
    public void onOccupancyChanged(int[] newData) {
        // Actualizar los datos y repintar en el hilo de eventos de Swing
        SwingUtilities.invokeLater(() -> {
            occupancyDataRef.set(newData);
            if (timeBarChartPanel != null) {
                timeBarChartPanel.repaint();
            }
        });
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

    private void initializeStatisticsView() {
        clearMainPanel();
        JPanel chartContainer = createChartContainer();
        updateOccupancyData();
        setupTimeBarChartPanel(chartContainer);
    }

    private void clearMainPanel() {
        mainPanel.removeAll();
        mainPanel.add(menuPanel);

        JLabel titleLabel = new JLabel("PARKING STATISTICS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(400, 30, 300, 30);
        mainPanel.add(titleLabel);
    }

    private JPanel createChartContainer() {
        JPanel chartContainer = new JPanel() {
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
        chartContainer.setLayout(null);
        chartContainer.setBounds(230, 80, 640, 380);
        chartContainer.setOpaque(false);

        JLabel chartTitle = new JLabel("Vehicles per Minute (Last 60 Minutes)", SwingConstants.CENTER);
        chartTitle.setFont(new Font("Arial", Font.BOLD, 18));
        chartTitle.setBounds(0, 10, 640, 30);
        chartContainer.add(chartTitle);

        return chartContainer;
    }

    private void updateOccupancyData() {
        try {
            parkingOccupancyService.updateOccupancyData();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
        occupancyDataRef.set(parkingOccupancyService.getCurrentOccupancy());
    }

    private void setupTimeBarChartPanel(JPanel chartContainer) {
        timeBarChartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                int leftMargin = 60, rightMargin = 20, topMargin = 40, bottomMargin = 60;
                int chartWidth = getWidth() - leftMargin - rightMargin;
                int chartHeight = getHeight() - topMargin - bottomMargin;

                // Draw axes
                g2d.setColor(Color.BLACK);
                g2d.drawLine(leftMargin, getHeight() - bottomMargin, getWidth() - rightMargin, getHeight() - bottomMargin); // X axis
                g2d.drawLine(leftMargin, topMargin, leftMargin, getHeight() - bottomMargin); // Y axis

                // Draw Y labels and ticks
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                int maxOccupancy = 60;
                int yStep = 10;
                for (int i = 0; i <= 5; i++) {
                    int value = i * yStep;
                    int y = getHeight() - bottomMargin - (i * chartHeight / 5);
                    g2d.drawString(String.valueOf(value), leftMargin - 30, y + 5);
                    g2d.drawLine(leftMargin - 5, y, leftMargin, y);
                }

                // Y axis title
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                Graphics2D g2d2 = (Graphics2D) g2d.create();
                g2d2.translate(20, getHeight() / 2);
                g2d2.rotate(-Math.PI / 2);
                g2d2.drawString("Number of Vehicles", 0, 0);
                g2d2.dispose();

                // Draw X labels and ticks
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                for (int i = 0; i < 60; i += 10) {
                    int x = leftMargin + (i * chartWidth / 60);
                    g2d.drawString(i + " min ago", x - 15, getHeight() - bottomMargin + 20);
                    g2d.drawLine(x, getHeight() - bottomMargin, x, getHeight() - bottomMargin + 5);
                }
                g2d.drawString("now", getWidth() - rightMargin - 15, getHeight() - bottomMargin + 20);

                // X axis title
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("Time (minutes ago)", getWidth() / 2 - 200, getHeight() - 15);

                // Draw bars
                int barWidth = chartWidth / 65;
                int[] currentData = occupancyDataRef.get();

                for (int i = 0; i < 60; i++) {
                    int totalVehicles = currentData[i];
                    int x = leftMargin + (i * chartWidth / 60);
                    int y = getHeight() - bottomMargin - (totalVehicles * chartHeight / maxOccupancy);
                    int height = (totalVehicles * chartHeight / maxOccupancy);

                    GradientPaint barGradient = new GradientPaint(
                            x, y, new Color(65, 105, 225),
                            x, y + height, new Color(30, 70, 180)
                    );
                    g2d.setPaint(barGradient);
                    g2d.fillRect(x, y, barWidth, height);

                    g2d.setColor(new Color(40, 40, 40, 120));
                    g2d.drawRect(x, y, barWidth, height);

                    if (height > 20) {
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(new Font("Arial", Font.PLAIN, 8));
                        String valueText = String.valueOf(totalVehicles);
                        int textWidth = g2d.getFontMetrics().stringWidth(valueText);
                        g2d.drawString(valueText, x + (barWidth - textWidth) / 2, y + 10);
                    }
                }

                // Legend
                int legendX = leftMargin + 170;
                int legendY = topMargin + 225;
                g2d.setColor(new Color(65, 105, 225));
                g2d.fillRect(legendX, legendY, 15, 15);
                g2d.setColor(Color.BLACK);
                g2d.drawString("Vehicles", legendX + 20, legendY + 12);

                // Last update time
                g2d.setFont(new Font("Arial", Font.ITALIC, 10));
                g2d.drawString("Last updated: " + LocalDateTime.now().format(DateTimeFormatter.ofPattern("HH:mm:ss")),
                        leftMargin + 350, topMargin + 20);
            }
        };

        timeBarChartPanel.setBounds(10, 50, 620, 300);
        chartContainer.add(timeBarChartPanel);
        mainPanel.add(chartContainer);
        mainPanel.revalidate();
        mainPanel.repaint();
    }


    private void handleLogout() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new MainView());
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
