package presentation.views;

import business.model.User;
import presentation.components.RoundButton;
import presentation.components.RoundPasswordField;
import presentation.components.RoundTextField;
import presentation.controllers.LoginController;

import javax.swing.*;
import java.awt.*;

public class LoginView extends JPanel {
    private LoginController loginController;

    public LoginView() {
        loginController = new LoginController();
        // Configuración del panel principal
        setLayout(null);
        setPreferredSize(new Dimension(900, 500));  // Tamaño del panel

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
        mainPanel.setBounds(0, 0, 900, 500);  // Ajustamos el tamaño del panel al tamaño deseado

        // Flecha de regreso
        JLabel backArrow = new JLabel("←");
        backArrow.setFont(new Font("Arial", Font.BOLD, 20));
        backArrow.setForeground(Color.BLACK);
        backArrow.setBounds(20, 20, 30, 30);
        mainPanel.add(backArrow);

        // Título
        JLabel title = new JLabel("Log In");
        title.setFont(new Font("Arial", Font.BOLD, 30));
        title.setForeground(Color.BLACK);
        title.setBounds(100, 50, 200, 40);
        mainPanel.add(title);

        // Etiquetas de texto
        JLabel userLabel = new JLabel("User name or email");
        userLabel.setForeground(Color.BLACK);
        userLabel.setBounds(100, 100, 200, 20);
        mainPanel.add(userLabel);

        // Campos de texto con bordes redondeados
        JTextField nameOrEmail = new RoundTextField(20);
        nameOrEmail.setBounds(100, 120, 250, 35);
        mainPanel.add(nameOrEmail);

        JLabel passLabel = new JLabel("Password");
        passLabel.setForeground(Color.BLACK);
        passLabel.setBounds(100, 160, 200, 20);
        mainPanel.add(passLabel);

        JPasswordField password = new RoundPasswordField(20);
        password.setBounds(100, 180, 250, 35);
        mainPanel.add(password);

        // Botón de inicio de sesión
        JButton loginButton = new RoundButton("Log In");
        loginButton.setBounds(100, 250, 250, 40);
        loginButton.setBackground(new Color(66, 133, 244));
        loginButton.setForeground(Color.WHITE);
        loginButton.setFont(new Font("Arial", Font.BOLD, 14));
        mainPanel.add(loginButton);

        loginButton.addActionListener(e -> {
            String user = nameOrEmail.getText();
            String pass = new String(password.getPassword());

            if (user.isEmpty() || pass.isEmpty()) {
                JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos.", "Error", JOptionPane.ERROR_MESSAGE);
            } else {
                String result = loginController.login(user, pass);

                if ("success".equals(result)) {
                    User loggedUser = loginController.getUser(user);
                    JOptionPane.showMessageDialog(this, "Inicio de sesión exitoso", "Bienvenido " + loggedUser.getUserName(), JOptionPane.INFORMATION_MESSAGE);

                    // Reemplazar la vista en lugar de abrir una nueva ventana
                    // Aquí pasamos el panel de usuario después de login
                    setVisible(false);
                    JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
                    parentFrame.setContentPane(new UserMenuView(loggedUser));  // Suponiendo que UserMenuView es un JPanel
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
