package presentation.views;

import presentation.components.RoundButton;
import presentation.controllers.DeleteAccountController;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    private JPanel mainPanel;

    public SettingsView() {
        setLayout(new BorderLayout()); // Cambiamos a BorderLayout para mejor manejo

        // Panel principal con degradado
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(44, 37, 80),
                        getWidth(), getHeight(), new Color(161, 141, 204));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(new BorderLayout());

        // Flecha de regreso
        JLabel backArrow = new JLabel("←");
        backArrow.setFont(new Font("Arial", Font.BOLD, 20));
        backArrow.setForeground(Color.BLACK);
        backArrow.setBounds(20, 20, 30, 30);
        mainPanel.add(backArrow);

        // Menú lateral
        JPanel menuPanel = createMenuPanel();
        mainPanel.add(menuPanel, BorderLayout.WEST);

        // Panel de configuración (parte derecha)
        JPanel settingsContentPanel = createSettingsContentPanel();
        mainPanel.add(settingsContentPanel, BorderLayout.CENTER);

        add(mainPanel);
    }

    private JPanel createMenuPanel() {
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(new BoxLayout(menuPanel, BoxLayout.Y_AXIS));
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setPreferredSize(new Dimension(200, 500));

        // Título del menú
        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setAlignmentX(Component.CENTER_ALIGNMENT);
        menuPanel.add(Box.createVerticalStrut(20));
        menuPanel.add(menuTitle);
        menuPanel.add(Box.createVerticalStrut(30));

        // Botón de Delete Account
        RoundButton deleteButton = new RoundButton("Delete Account");
        deleteButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        deleteButton.setMaximumSize(new Dimension(160, 40));
        deleteButton.setBackground(new Color(150, 130, 200));
        deleteButton.setForeground(Color.BLACK);
        deleteButton.setFocusPainted(false);

        deleteButton.addActionListener(e -> {
            DeleteAccountController deleteAccountController = new DeleteAccountController();
            deleteAccountController.deleteAccount("jan", "1234");
            System.out.println("delete account");
        });

        menuPanel.add(deleteButton);
        menuPanel.add(Box.createVerticalStrut(20));

        return menuPanel;
    }

    private JPanel createSettingsContentPanel() {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false); // Para que se vea el degradado
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título de configuración
        JLabel title = new JLabel("CONFIGURACIÓN", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(40));

        // Opciones de configuración
        addSettingOption(contentPanel, "Tema:", new JComboBox<>(new String[]{"Claro", "Oscuro", "Azul"}));
        addSettingOption(contentPanel, "Notificaciones:", new JCheckBox("Activar"));
        addSettingOption(contentPanel, "Idioma:", new JComboBox<>(new String[]{"Español", "Inglés", "Catalán"}));

        return contentPanel;
    }

    private void addSettingOption(JPanel panel, String label, JComponent component) {
        JPanel optionPanel = new JPanel(new FlowLayout(FlowLayout.LEFT, 20, 0));
        optionPanel.setOpaque(false);

        JLabel optionLabel = new JLabel(label);
        optionLabel.setForeground(Color.WHITE);
        optionLabel.setFont(new Font("Arial", Font.BOLD, 16));
        optionLabel.setPreferredSize(new Dimension(150, 30));

        component.setPreferredSize(new Dimension(200, 30));
        if (component instanceof JComboBox) {
            ((JComboBox<?>) component).setBackground(Color.WHITE);
        }

        optionPanel.add(optionLabel);
        optionPanel.add(component);

        panel.add(optionPanel);
        panel.add(Box.createVerticalStrut(20));
    }

    public void setBackAction(ActionListener action) {
        // Buscar botones en el menú para asignar la acción de volver
        for (Component comp : mainPanel.getComponents()) {
            if (comp instanceof JPanel ) {
                for (Component subComp : ((JPanel) comp).getComponents()) {
                    if (subComp instanceof JButton) {
                        ((JButton) subComp).addActionListener(action);
                    }
                }
            }
        }
    }
}