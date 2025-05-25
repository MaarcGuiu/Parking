package presentation.views;

import business.model.CancelledReservation;
import business.model.Slot;
import business.model.User;
import presentation.components.RoundButton;
import presentation.components.RoundPasswordField;
import presentation.components.RoundTextField;
import presentation.controllers.LoginController;

import javax.swing.*;
import java.awt.*;
import java.sql.SQLException;
import java.util.List;

/**
 * The type Login view.
 */
public class LoginView extends JPanel {
    private JPanel mainPanel;
    private LoginController loginController;

    private JTextField nameOrEmail;
    private JPasswordField password;

    /**
     * Instantiates a new Login view.
     */
    public LoginView() {
        initController();
        initLayout();
        initComponents();
    }

    private void initController() {
        try {
            loginController = new LoginController();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, e.getMessage(),"Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void initLayout() {
        setLayout(null);
        setPreferredSize(new Dimension(900, 500));

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
        add(mainPanel);
    }

    private void initComponents() {
        JLabel backArrow = new JLabel("←");
        backArrow.setFont(new Font("Arial", Font.BOLD, 20));
        backArrow.setForeground(Color.BLACK);
        backArrow.setBounds(20, 20, 30, 30);
        mainPanel.add(backArrow);

        JLabel title = new JLabel("Log In");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setBounds(100, 50, 200, 40);
        mainPanel.add(title);

        JLabel userLabel = new JLabel("User name or email");
        userLabel.setForeground(Color.BLACK);
        userLabel.setBounds(100, 100, 200, 20);
        mainPanel.add(userLabel);

        nameOrEmail = new RoundTextField(20);
        nameOrEmail.setBounds(100, 120, 250, 35);
        mainPanel.add(nameOrEmail);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.BLACK);
        passLabel.setBounds(100, 160, 200, 20);
        mainPanel.add(passLabel);

        password = new RoundPasswordField(20);
        password.setBounds(100, 180, 250, 35);
        mainPanel.add(password);

        JButton loginButton = new RoundButton("Log In");
        loginButton.setBounds(100, 250, 250, 40);
        loginButton.setBackground(new Color(66, 133, 244));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(loginButton);

        backArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                backToMainView();
            }
        });

        loginButton.addActionListener(e -> checkLogin());
    }

    private void backToMainView() {
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(LoginView.this);
        parentFrame.setContentPane(new MainView());
        parentFrame.revalidate();
        parentFrame.repaint();
    }

    private void checkLogin() {
        String user = nameOrEmail.getText();
        String pass = new String(password.getPassword());

        if (user.isEmpty() || pass.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Please complete all fields.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (loginController == null) {
            JOptionPane.showMessageDialog(this, "Could not connect to database. Please check your configuration.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            String result = loginController.login(user, pass);
            if (result == null) {
                JOptionPane.showMessageDialog(this, "Unknown error during login.", "Error", JOptionPane.ERROR_MESSAGE);
                return;
            }

            switch (result) {
                case "success" -> {
                    User loggedUser = loginController.getUser(user);
                    JOptionPane.showMessageDialog(this, "Successful login", "Welcome " + loggedUser.getUserName(), JOptionPane.INFORMATION_MESSAGE);
                    mostrarReservasCanceladas(loggedUser.getId());

                    setVisible(false);
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    parentFrame.setContentPane(new UserMenuView(loggedUser));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
                case "admin_success" -> {
                    JOptionPane.showMessageDialog(this, "Successful login.", "Bienvenido Admin", JOptionPane.INFORMATION_MESSAGE);

                    setVisible(false);
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    parentFrame.setContentPane(new AdminMenuView());
                    parentFrame.revalidate();
                    parentFrame.repaint();
                }
                default -> JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
            }

        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Database error: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void mostrarReservasCanceladas(int userId) {
        try {
            List<CancelledReservation> canceladas = loginController.getCancelledReservationsByUserId(userId);

            if (canceladas != null && !canceladas.isEmpty()) {
                StringBuilder mensaje = new StringBuilder("The admin has canceled your following reservations:\n\n");
                Slot newSlot;
                for (CancelledReservation reservas : canceladas) {
                    mensaje.append("Previous Slot Code: ").append(reservas.getSlot().getIdSlot()).append("\n");
                    mensaje.append("Previous Slot Plant: ").append(reservas.getSlot().getFloor()).append("\n");
                    newSlot = loginController.getSlotByPlate(reservas.getVehicle().getPlate());
                    if (newSlot != null) {
                        mensaje.append("New Slot Code: ").append(newSlot.getIdSlot()).append("\n");
                        mensaje.append("New Slot Plant: ").append(newSlot.getFloor()).append("\n");
                    }
                }
                JOptionPane.showMessageDialog(null, mensaje.toString(), "Cancelled reservations", JOptionPane.WARNING_MESSAGE);

                for (CancelledReservation c : canceladas) {
                    loginController.deleteCancelledReservationById(c.getId());
                }
            }
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(this, "Error loading canceled reservations: " + ex.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
