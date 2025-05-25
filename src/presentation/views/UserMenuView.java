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
    private persistence.LogsSqlDao LogsSqlDao = new LogsSqlDao();
    private business.ParkingOccupancyManager ParkingOccupancyManager = new ParkingOccupancyManager(LogsSqlDao);
    private final ParkingOccupancyController parkingOccupancyService = new ParkingOccupancyController(ParkingOccupancyManager);

    private JPanel timeBarChartPanel;

    public UserMenuView(User loggedUser) {
        this.loggedUser = loggedUser;
        parkingOccupancyService.addOccupancyChangeListener(this); // Registrar como listener


        // Permitir posicionamiento absoluto
        setLayout(null);

        cardLayout = new CardLayout();
        cardPanel = new JPanel(cardLayout);

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

        Runnable resetMainPanel = () -> {
            mainPanel.removeAll();
            mainPanel.add(menuPanel);
            mainPanel.revalidate();
            mainPanel.repaint();
        };

        parkingStatusButton.addActionListener(e -> {
            boolean isAdmin = false;
            if (parkingStatusButton != null) {
                parkingStatusButton.setBackground(Color.YELLOW);
            }
            ParkingStatusView.show(mainPanel, menuPanel, resetMainPanel, false);
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


        // Botón 5 de Settings (Ajustes)
        JButton settingsButton = new RoundButton("Settings");
        settingsButton.setBounds(20, 310, 160, 40);
        settingsButton.setBackground(new Color(150, 130, 200));
        settingsButton.setForeground(Color.BLACK);
        settingsButton.setFocusPainted(false);
        menuPanel.add(settingsButton);

        mainPanel.add(menuPanel);
        add(mainPanel);

        statisticsButton.addActionListener(e -> {
            resetMainPanel.run();
            statisticsButton.setBackground(Color.YELLOW);
            initializeStatisticsView(); // Llamamos a la nueva función
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
        // Limpiar el panel principal
        mainPanel.removeAll();
        mainPanel.add(menuPanel);

        // Título
        JLabel titleLabel = new JLabel("PARKING STATISTICS");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 22));
        titleLabel.setForeground(Color.BLACK);
        titleLabel.setBounds(400, 30, 300, 30);
        mainPanel.add(titleLabel);

        // Contenedor del gráfico
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

        // Título del gráfico
        JLabel chartTitle = new JLabel("Vehicles per Minute (Last 60 Minutes)", SwingConstants.CENTER);
        chartTitle.setFont(new Font("Arial", Font.BOLD, 18));
        chartTitle.setBounds(0, 10, 640, 30);
        chartContainer.add(chartTitle);

        // Obtener datos actuales
        parkingOccupancyService.updateOccupancyData();
        occupancyDataRef.set(parkingOccupancyService.getCurrentOccupancy());

        // Panel del gráfico de barras
        timeBarChartPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

                // Configuración de márgenes y dimensiones
                int leftMargin = 60;
                int rightMargin = 20;
                int topMargin = 40;
                int bottomMargin = 60;
                int chartWidth = getWidth() - leftMargin - rightMargin;
                int chartHeight = getHeight() - topMargin - bottomMargin;

                // Dibujar ejes
                g2d.setColor(Color.BLACK);
                g2d.drawLine(leftMargin, getHeight() - bottomMargin, getWidth() - rightMargin, getHeight() - bottomMargin); // Eje X
                g2d.drawLine(leftMargin, topMargin, leftMargin, getHeight() - bottomMargin); // Eje Y

                // Etiquetas del eje Y
                g2d.setFont(new Font("Arial", Font.PLAIN, 12));
                int maxOccupancy = 60;
                int yStep = 10;

                for (int i = 0; i <= 5; i++) {
                    int value = i * yStep;
                    int y = getHeight() - bottomMargin - (i * chartHeight / 5);
                    g2d.drawString(String.valueOf(value), leftMargin - 30, y + 5);
                    g2d.drawLine(leftMargin - 5, y, leftMargin, y); // Marcas
                }

                // Título del eje Y
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                Graphics2D g2d2 = (Graphics2D) g.create();
                g2d2.translate(20, getHeight() / 2);
                g2d2.rotate(-Math.PI / 2);
                g2d2.drawString("Number of Vehicles", 0, 0);
                g2d2.dispose();

                // Etiquetas del eje X (minutos)
                g2d.setFont(new Font("Arial", Font.PLAIN, 10));
                for (int i = 0; i < 60; i += 10) {
                    int x = leftMargin + (i * chartWidth / 60);
                    g2d.drawString(i + " min ago", x - 15, getHeight() - bottomMargin + 20);
                    g2d.drawLine(x, getHeight() - bottomMargin, x, getHeight() - bottomMargin + 5);
                }
                g2d.drawString("now", getWidth() - rightMargin - 15, getHeight() - bottomMargin + 20);

                // Título del eje X
                g2d.setFont(new Font("Arial", Font.BOLD, 12));
                g2d.drawString("Time (minutes ago)", getWidth() / 2 - 200, getHeight() - 15);

                // Dibujar barras
                int barWidth = chartWidth / 65;
                int[] currentData = occupancyDataRef.get();

                for (int i = 0; i < 60; i++) {
                    int totalVehicles = currentData[i];
                    int x = leftMargin + (i * chartWidth / 60);
                    int y = getHeight() - bottomMargin - (totalVehicles * chartHeight / maxOccupancy);
                    int height = (totalVehicles * chartHeight / maxOccupancy);

                    // Gradiente para las barras
                    GradientPaint barGradient = new GradientPaint(
                            x, y, new Color(65, 105, 225),
                            x, y + height, new Color(30, 70, 180)
                    );
                    g2d.setPaint(barGradient);
                    g2d.fillRect(x, y, barWidth, height);

                    // Contorno de las barras
                    g2d.setColor(new Color(40, 40, 40, 120));
                    g2d.drawRect(x, y, barWidth, height);

                    // Mostrar valor si hay espacio
                    if (height > 20) {
                        g2d.setColor(Color.BLACK);
                        g2d.setFont(new Font("Arial", Font.PLAIN, 8));
                        String valueText = String.valueOf(totalVehicles);
                        int textWidth = g2d.getFontMetrics().stringWidth(valueText);
                        g2d.drawString(valueText, x + (barWidth - textWidth)/2, y + 10);
                    }
                }

                // Leyenda
                int legendX = leftMargin + 170;
                int legendY = topMargin + 225;
                g2d.setColor(new Color(65, 105, 225));
                g2d.fillRect(legendX, legendY, 15, 15);
                g2d.setColor(Color.BLACK);
                g2d.drawString("Vehicles", legendX + 20, legendY + 12);

                // Hora de actualización
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
