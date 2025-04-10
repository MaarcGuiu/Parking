package presentation.views;

import business.model.User;
import presentation.components.RoundButton;
import presentation.controllers.DeleteAccountController;

import javax.swing.*;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.JTableHeader;
import java.awt.*;
import java.awt.event.ActionListener;

public class SettingsView extends JPanel {
    private JPanel mainPanel;
    private JButton deleteAccountButton;
    private JButton logoutButton;

    public SettingsView(User loggedUser) {
        setLayout(new BorderLayout());

        // Panel principal con degradado
        mainPanel = new JPanel(new BorderLayout()) {
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

        // Panel superior con botón de cerrar
        JPanel topPanel = new JPanel(new FlowLayout(FlowLayout.RIGHT));
        topPanel.setOpaque(false);
        topPanel.setBorder(BorderFactory.createEmptyBorder(10, 10, 10, 10));

        JLabel closeButton = new JLabel("\u2716");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 22));
        closeButton.setForeground(Color.BLACK);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        topPanel.add(closeButton);
        mainPanel.add(topPanel, BorderLayout.NORTH);

        // Panel de configuración
        JPanel settingsContentPanel = createSettingsContentPanel(loggedUser);
        mainPanel.add(settingsContentPanel, BorderLayout.CENTER);

        add(mainPanel);

        // Listeners
        closeButton.addMouseListener(new java.awt.event.MouseAdapter() {
            @Override
            public void mouseClicked(java.awt.event.MouseEvent e) {
                setVisible(false);
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(SettingsView.this);
                parentFrame.setContentPane(new UserMenuView(loggedUser));
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });
    }

    private JPanel createSettingsContentPanel(User loggedUser) {
        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);
        contentPanel.setBorder(BorderFactory.createEmptyBorder(30, 30, 30, 30));

        // Título
        JLabel title = new JLabel("CONFIGURACIÓN", SwingConstants.CENTER);
        title.setForeground(Color.WHITE);
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setAlignmentX(Component.CENTER_ALIGNMENT);
        contentPanel.add(title);
        contentPanel.add(Box.createVerticalStrut(40));

        // Botones
        JPanel buttonsPanel = new JPanel();
        buttonsPanel.setLayout(new GridLayout(2, 1, 10, 20));
        buttonsPanel.setOpaque(false);
        buttonsPanel.setMaximumSize(new Dimension(300, 150));

        deleteAccountButton = new RoundButton("Delete Account");
        deleteAccountButton.setBackground(new Color(255, 100, 100));
        deleteAccountButton.setForeground(Color.WHITE);
        deleteAccountButton.setFont(new Font("Arial", Font.BOLD, 16));
        deleteAccountButton.setFocusPainted(false);

        logoutButton = new RoundButton("Log out");
        logoutButton.setBackground(new Color(100, 150, 255));
        logoutButton.setForeground(Color.WHITE);
        logoutButton.setFont(new Font("Arial", Font.BOLD, 16));
        logoutButton.setFocusPainted(false);

        buttonsPanel.add(deleteAccountButton);
        buttonsPanel.add(logoutButton);

        contentPanel.add(buttonsPanel);
        contentPanel.add(Box.createVerticalGlue());

        // Añadir listeners a los botones
        deleteAccountButton.addActionListener(e -> handleDeleteAccount(loggedUser));
        logoutButton.addActionListener(e -> handleLogout());

        return contentPanel;
    }

    private void handleDeleteAccount(User loggedUser) {
        int option = JOptionPane.showConfirmDialog(
                this,
                "¿Estás seguro de que quieres eliminar tu cuenta? Esta acción no se puede deshacer.",
                "Confirmar eliminación",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE
        );

        if (option == JOptionPane.YES_OPTION) {
            DeleteAccountController deleteAccountController = new DeleteAccountController();
            deleteAccountController.deleteAccount(loggedUser.getUserName(), loggedUser.getPassword());

            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
            parentFrame.setContentPane(new MainView());
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    private void handleLogout() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new MainView());
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}