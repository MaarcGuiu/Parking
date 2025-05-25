package presentation.views;

import business.ParkingOccupancyManager;
import business.model.User;
import persistence.LogsSqlDao;
import presentation.components.RoundButton;
import presentation.controllers.ParkingOccupancyController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.concurrent.atomic.AtomicReference;

public class UserMenuView extends JPanel implements OccupancyChangeListener{
    private JPanel mainPanel;
    private User loggedUser;
    private CardLayout cardLayout;  // Necesitamos esto para cambiar de vista
    private JPanel cardPanel;      // Panel que contendrá todas las vistas
    private JPanel menuPanel;
    private AtomicReference<int[]> occupancyDataRef = new AtomicReference<>(new int[60]);
    private persistence.LogsSqlDao LogsSqlDao;
    private business.ParkingOccupancyManager ParkingOccupancyManager;
    private ParkingOccupancyController parkingOccupancyService;

    private JButton bookingsButton;
    private JButton parkingStatusButton;
    private JButton statisticsButton;
    private JButton enterLeaveButton;
    private JButton settingsButton;
    private JPanel timeBarChartPanel;

    public UserMenuView(User loggedUser) {
        this.loggedUser = loggedUser;
        initDaoAndServices();
        initMainAndMenuPanels();
        initMenuButtons();
        registerListeners();
    }

    private void initDaoAndServices() {
        try {
            LogsSqlDao = new LogsSqlDao();
            ParkingOccupancyManager = new ParkingOccupancyManager(LogsSqlDao);
            parkingOccupancyService = new ParkingOccupancyController(ParkingOccupancyManager);
            parkingOccupancyService.addOccupancyChangeListener(this);
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initMainAndMenuPanels() {
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
        menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);

        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30);
        menuPanel.add(menuTitle);

        mainPanel.add(menuPanel);
        add(mainPanel);
    }

    private void initMenuButtons() {
        bookingsButton = createMenuButton("Bookings", 70);
        parkingStatusButton = createMenuButton("Parking Status", 130);
        statisticsButton = createMenuButton("Statistics", 190);
        enterLeaveButton = createMenuButton("Enter - Leave parking", 250);
        settingsButton = createMenuButton("Settings", 310);

        menuPanel.add(bookingsButton);
        menuPanel.add(parkingStatusButton);
        menuPanel.add(statisticsButton);
        menuPanel.add(enterLeaveButton);
        menuPanel.add(settingsButton);
    }

    private JButton createMenuButton(String text, int yPosition) {
        JButton button = new RoundButton(text);
        button.setBounds(20, yPosition, 160, 40);
        button.setBackground(new Color(150, 130, 200));
        button.setForeground(Color.BLACK);
        button.setFocusPainted(false);
        return button;
    }

    private void resetButtonColors() {
        bookingsButton.setBackground(new Color(150, 130, 200));
        parkingStatusButton.setBackground(new Color(150, 130, 200));
        statisticsButton.setBackground(new Color(150, 130, 200));
        enterLeaveButton.setBackground(new Color(150, 130, 200));
        settingsButton.setBackground(new Color(150, 130, 200));
    }

    private void registerListeners() {
        Runnable resetMainPanel = () -> {
            mainPanel.removeAll();
            mainPanel.add(menuPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        };

        parkingStatusButton.addActionListener(e -> {
            resetButtonColors();
            parkingStatusButton.setBackground(Color.YELLOW);
            ParkingStatusView.show(mainPanel, menuPanel, resetMainPanel, false);
        });

        statisticsButton.addActionListener(e -> {
            resetButtonColors();
            resetMainPanel.run();
            statisticsButton.setBackground(Color.YELLOW);

            initializeStatisticsView();
        });

        settingsButton.addActionListener(e -> {
            settingsButton.setBackground(Color.YELLOW);
            setVisible(true);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.setContentPane(new SettingsView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        enterLeaveButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.setContentPane(new EntryParkingView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        });

        bookingsButton.addActionListener(e -> {
            setVisible(false);
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.setContentPane(new BookMenuView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        });
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

    public void cleanup() {
        parkingOccupancyService.removeOccupancyChangeListener(this);
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
