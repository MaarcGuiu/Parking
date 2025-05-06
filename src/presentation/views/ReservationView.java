package presentation.views;

import business.model.User;
import java.awt.*;
import javax.swing.*;
import presentation.components.RoundButton;

public class ReservationView extends JPanel {

    private JPanel mainPanel;
    private User loggedUser;
    private RoundButton bookButton;
    private RoundButton removeBookButton;
    private RoundButton backButton; // Changed to RoundButton for consistency
    private JLabel closeButton;

    public ReservationView(User loggedUser) {
        this.loggedUser = loggedUser;
        setLayout(null);

        // Panel principal
        mainPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(
                    0,
                    0,
                    new Color(44, 37, 80),
                    getWidth(),
                    getHeight(),
                    new Color(161, 141, 204)
                );
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        mainPanel.setLayout(null);
        mainPanel.setBounds(0, 0, 900, 500);
        add(mainPanel); // Add mainPanel to the ReservationView itself

        JLabel titleLabel = new JLabel(
            "RESERVATION MENU",
            SwingConstants.CENTER
        );
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Arial", Font.BOLD, 24));
        titleLabel.setBounds(200 + (900 - 200 - 300) / 2, 50, 300, 40);
        mainPanel.add(titleLabel);

        closeButton = new JLabel("\u2716");
        closeButton.setFont(new Font("Dialog", Font.BOLD, 22));
        closeButton.setForeground(Color.WHITE);
        closeButton.setBounds(840, 20, 30, 30);
        closeButton.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        mainPanel.add(closeButton);

        // Menu lateral
        JPanel menuPanel = new JPanel();
        menuPanel.setLayout(null);
        menuPanel.setBackground(new Color(70, 60, 130));
        menuPanel.setBounds(0, 0, 200, 500);
        mainPanel.add(menuPanel);

        JLabel menuTitle = new JLabel("MENU", SwingConstants.CENTER);
        menuTitle.setForeground(Color.WHITE);
        menuTitle.setFont(new Font("Arial", Font.BOLD, 20));
        menuTitle.setBounds(0, 20, 200, 30);
        menuPanel.add(menuTitle);

        bookButton = new RoundButton("Book");
        bookButton.setBounds(20, 210, 160, 40);
        bookButton.setFont(new Font("Arial", Font.BOLD, 16));
        bookButton.setBackground(new Color(150, 130, 200));
        bookButton.setForeground(Color.BLACK);
        bookButton.setFocusPainted(false);
        menuPanel.add(bookButton);

        removeBookButton = new RoundButton("Remove book");
        removeBookButton.setBounds(20, 270, 160, 40);
        removeBookButton.setFont(new Font("Arial", Font.BOLD, 16));
        removeBookButton.setBackground(new Color(150, 130, 200));
        removeBookButton.setForeground(Color.BLACK);
        removeBookButton.setFocusPainted(false);
        menuPanel.add(removeBookButton);

        backButton = new RoundButton("Back");
        backButton.setBounds(250, 420, 100, 40);
        backButton.setFont(new Font("Arial", Font.PLAIN, 14));
        mainPanel.add(backButton);

        bookButton.addActionListener(e -> {
            JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(
                this
            );
            if (parentFrame != null) {
                parentFrame.setContentPane(new BookMenuView(this.loggedUser));
                parentFrame.revalidate();
                parentFrame.repaint();
            } else {
                System.err.println(
                    "Error: Could not find parent frame for ReservationView."
                );
            }
        });

        removeBookButton.addActionListener(e -> {
            JOptionPane.showMessageDialog(
                this,
                "Remove Book View not implemented yet.",
                "Info",
                JOptionPane.INFORMATION_MESSAGE
            );
            bookButton.setBackground(new Color(150, 130, 200));
            removeBookButton.setBackground(new Color(255, 200, 0));
        });

        backButton.addActionListener(e -> {
            goBackToUserMenu();
        });

        closeButton.addMouseListener(
            new java.awt.event.MouseAdapter() {
                @Override
                public void mouseClicked(java.awt.event.MouseEvent e) {
                    goBackToUserMenu();
                }

                @Override
                public void mouseEntered(java.awt.event.MouseEvent e) {
                    closeButton.setForeground(Color.RED);
                }

                @Override
                public void mouseExited(java.awt.event.MouseEvent e) {
                    closeButton.setForeground(Color.WHITE);
                }
            }
        );
    }

    private void goBackToUserMenu() {
        setVisible(false);
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        if (parentFrame != null) {
            parentFrame.setContentPane(new UserMenuView(loggedUser));
            parentFrame.revalidate();
            parentFrame.repaint();
        }
    }

    public JPanel getMainPanel() {
        return mainPanel;
    }
}
