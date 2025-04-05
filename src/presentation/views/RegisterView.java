package presentation.views;

import business.model.User;
import presentation.components.RoundButton;
import presentation.components.RoundPasswordField;
import presentation.components.RoundTextField;
import presentation.controllers.LoginController;
import presentation.controllers.RegisterController;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {
    private RegisterController registerController;

    public RegisterView() {
        registerController = new RegisterController();

        setLayout(null);
        setPreferredSize(new Dimension(900, 500));

        JPanel mainPanel = new JPanel() {
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

        JLabel backArrow = new JLabel("←");
        backArrow.setFont(new Font("Arial", Font.BOLD, 20));
        backArrow.setForeground(Color.BLACK);
        backArrow.setBounds(20, 20, 30, 30);
        mainPanel.add(backArrow);

        backArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(RegisterView.this);
                parentFrame.setContentPane(new MainView());  // Cambia el contenido por MainView
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        JLabel title = new JLabel("Register");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setBounds(100, 50, 200, 40);
        mainPanel.add(title);

        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.BLACK);
        userLabel.setBounds(100, 100, 200, 20);
        mainPanel.add(userLabel);

        JTextField usernameField = new RoundTextField(20);
        usernameField.setBounds(100, 120, 250, 35);
        mainPanel.add(usernameField);

        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(100, 160, 200, 20);
        mainPanel.add(emailLabel);

        JTextField emailField = new RoundTextField(20);
        emailField.setBounds(100, 180, 250, 35);
        mainPanel.add(emailField);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.BLACK);
        passLabel.setBounds(100, 220, 200, 20);
        mainPanel.add(passLabel);

        JPasswordField passwordField = new RoundPasswordField(20);
        passwordField.setBounds(100, 240, 250, 35);
        mainPanel.add(passwordField);

        JLabel confirmPassLabel = new JLabel("Confirm Password");
        confirmPassLabel.setForeground(Color.BLACK);
        confirmPassLabel.setBounds(100, 280, 250, 20);
        mainPanel.add(confirmPassLabel);

        JPasswordField confirmPasswordField = new RoundPasswordField(20);
        confirmPasswordField.setBounds(100, 300, 250, 35);
        mainPanel.add(confirmPasswordField);

        JButton registerButton = new RoundButton("Register");
        registerButton.setBounds(100, 350, 250, 40);
        registerButton.setBackground(new Color(66, 133, 244));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(registerButton);

        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());
            String confirmPassword = new String(confirmPasswordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty() || confirmPassword.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else if (!password.equals(confirmPassword)) {
                JOptionPane.showMessageDialog(this,
                        "Les contrasenyes no coincideixen.\nEl camp 'Confirmació de contrasenya' ha de coincidir amb el camp 'Contrasenya'.",
                        "Error de contrasenya",
                        JOptionPane.ERROR_MESSAGE);
            } else if (!isPasswordSecure(password)) {
                JOptionPane.showMessageDialog(this,
                        "La contrasenya ha de tenir com a mínim 8 caràcters i incloure almenys:\n- una lletra majúscula\n- una lletra minúscula\n- un número",
                        "Contrasenya insegura",
                        JOptionPane.ERROR_MESSAGE);
            } else {
                String result = registerController.register(username, password, email);

                if ("success".equals(result)) {
                    LoginController loginController = new LoginController();

                    User user = loginController.getUser(email);
                    JOptionPane.showMessageDialog(this, "Registro exitoso\nEntrando al menú principal...", "Registro Completado", JOptionPane.INFORMATION_MESSAGE);

                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(RegisterView.this);
                    parentFrame.setContentPane(new UserMenuView(user));
                    parentFrame.revalidate();
                    parentFrame.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(mainPanel);
    }

    private boolean isPasswordSecure(String password) {
        if (password.length() < 8) return false;

        boolean hasUpper = false;
        boolean hasLower = false;
        boolean hasDigit = false;

        for (char c : password.toCharArray()) {
            if (Character.isUpperCase(c)) hasUpper = true;
            else if (Character.isLowerCase(c)) hasLower = true;
            else if (Character.isDigit(c)) hasDigit = true;
        }

        return hasUpper && hasLower && hasDigit;
    }
}
