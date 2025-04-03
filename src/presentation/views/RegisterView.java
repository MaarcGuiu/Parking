package presentation.views;

import business.model.User;
import presentation.components.RoundButton;
import presentation.components.RoundPasswordField;
import presentation.components.RoundTextField;
import presentation.controllers.RegisterController;

import javax.swing.*;
import java.awt.*;

public class RegisterView extends JPanel {
    private RegisterController registerController;

    public RegisterView() {
        registerController = new RegisterController();

        setLayout(null);
        setPreferredSize(new Dimension(900, 500));

        // Panel de fondo con degradado
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

        // Flecha de regreso
        JLabel backArrow = new JLabel("←");
        backArrow.setFont(new Font("Arial", Font.BOLD, 20));
        backArrow.setForeground(Color.BLACK);
        backArrow.setBounds(20, 20, 30, 30);
        mainPanel.add(backArrow);

        // Acción para volver atrás sin abrir una nueva instancia
        backArrow.addMouseListener(new java.awt.event.MouseAdapter() {
            public void mouseClicked(java.awt.event.MouseEvent evt) {
                JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(RegisterView.this);
                parentFrame.setContentPane(new MainView());  // Cambia el contenido por MainView
                parentFrame.revalidate();
                parentFrame.repaint();
            }
        });

        // Título
        JLabel title = new JLabel("Register");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setBounds(100, 50, 200, 40);
        mainPanel.add(title);

        // Etiqueta y campo Username
        JLabel userLabel = new JLabel("Username");
        userLabel.setForeground(Color.BLACK);
        userLabel.setBounds(100, 100, 200, 20);
        mainPanel.add(userLabel);

        JTextField usernameField = new RoundTextField(20);
        usernameField.setBounds(100, 120, 250, 35);
        mainPanel.add(usernameField);

        // Etiqueta y campo Email
        JLabel emailLabel = new JLabel("Email");
        emailLabel.setForeground(Color.BLACK);
        emailLabel.setBounds(100, 160, 200, 20);
        mainPanel.add(emailLabel);

        JTextField emailField = new RoundTextField(20);
        emailField.setBounds(100, 180, 250, 35);
        mainPanel.add(emailField);

        // Etiqueta y campo Password
        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.BLACK);
        passLabel.setBounds(100, 220, 200, 20);
        mainPanel.add(passLabel);

        JPasswordField passwordField = new RoundPasswordField(20);
        passwordField.setBounds(100, 240, 250, 35);
        mainPanel.add(passwordField);

        // Botón de registro
        JButton registerButton = new RoundButton("Register");
        registerButton.setBounds(100, 300, 250, 40);
        registerButton.setBackground(new Color(66, 133, 244));
        registerButton.setForeground(Color.WHITE);
        registerButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(registerButton);

        // Acción del botón
        registerButton.addActionListener(e -> {
            String username = usernameField.getText();
            String email = emailField.getText();
            String password = new String(passwordField.getPassword());

            if (username.isEmpty() || email.isEmpty() || password.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                // Llamamos al controlador para registrar al usuario
                String result = registerController.register(username, password, email);

                if ("success".equals(result)) {
                    JOptionPane.showMessageDialog(this, "Registro exitoso\nVolviendo al menú principal...", "Registro Completado", JOptionPane.INFORMATION_MESSAGE);

                    // Volver a la vista anterior (LoginView)
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(RegisterView.this);
                    parentFrame.setContentPane(new MainView());  // Cambia el contenido por MainView
                    parentFrame.revalidate();
                    parentFrame.repaint();
                } else {
                    JOptionPane.showMessageDialog(this, result, "Error", JOptionPane.ERROR_MESSAGE);
                }
            }
        });

        add(mainPanel);
    }
}
