package presentation.views;

import presentation.components.RoundButton;

import javax.swing.*;
import java.awt.*;

public class MainView extends JFrame {

    public MainView() {
        // Configuración básica del JFrame
        setTitle("THE PARKING LS");
        setSize(900, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);  // Centra la ventana en la pantalla

        // Fondo con degradado
        JPanel backgroundPanel = new JPanel() {
            @Override
            protected void paintComponent(Graphics g) {
                super.paintComponent(g);
                Graphics2D g2d = (Graphics2D) g;
                GradientPaint gp = new GradientPaint(0, 0, new Color(44, 37, 80), getWidth(), getHeight(), new Color(161, 141, 204));
                g2d.setPaint(gp);
                g2d.fillRect(0, 0, getWidth(), getHeight());
            }
        };
        backgroundPanel.setLayout(null);

        // Título
        JLabel title = new JLabel("THE PARKING LS");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setBounds(30, 50, 300, 40);
        backgroundPanel.add(title);

        // Botón Log In
        JButton loginButton = new RoundButton("Log In");
        loginButton.setBounds(30, 150, 160, 40);
        loginButton.addActionListener(e -> switchToLogin());
        backgroundPanel.add(loginButton);

        // Botón Sign Up
        JButton signUpButton = new RoundButton("Sign Up");
        signUpButton.setBounds(30, 210, 160, 40);
        signUpButton.addActionListener(e -> switchToSignUp());
        backgroundPanel.add(signUpButton);

        // Añadir el panel de fondo al JFrame
        add(backgroundPanel);

        // Hacer visible el JFrame
        setVisible(true);
    }

    private void switchToLogin() {
        // Cambiar el contenido del JFrame a la vista de login
        setContentPane(new LoginView());  // Suponiendo que LoginView es otro JPanel
        revalidate();
        repaint();
    }

    private void switchToSignUp() {
        // Cambiar el contenido del JFrame a la vista de sign-up
        //setContentPane(new SignUpView());  // Suponiendo que SignUpView es otro JPanel
        revalidate();
        repaint();
    }
}