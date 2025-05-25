package presentation.views;

import presentation.components.RoundButton;

import javax.swing.*;
import java.awt.*;

/**
 * The type Main view.
 */
public class MainView extends JPanel {

    private JPanel backgroundPanel;
    private JLabel title;
    private JButton loginButton;
    private JButton signUpButton;

    /**
     * Constructor for MainView.
     * Initializes the main view with a background, title, and buttons for login and sign-up.
     */
    public MainView() {
        setLayout(null);
        setPreferredSize(new Dimension(900, 500));

        initBackgroundPanel();
        initTitle();
        initButtons();

        add(backgroundPanel);
    }
    /**
     * Initializes the background panel with a gradient color.
     * The panel covers the entire view and provides a visually appealing background.
     */
    private void initBackgroundPanel() {
        backgroundPanel = new JPanel() {
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
        backgroundPanel.setBounds(0, 0, 900, 500);
    }
    /**
     * Initializes the title label with a specific font and color.
     * The title is displayed at the top of the main view.
     */
    private void initTitle() {
        title = new JLabel("THE PARKING LS");
        title.setFont(new Font("Arial", Font.BOLD, 24));
        title.setForeground(Color.BLACK);
        title.setBounds(30, 50, 300, 40);
        backgroundPanel.add(title);
    }
    /**
     * Initializes the login and sign-up buttons.
     * Each button has an action listener that switches to the respective view when clicked.
     */
    private void initButtons() {
        loginButton = new RoundButton("Log In");
        loginButton.setBounds(30, 150, 160, 40);
        loginButton.addActionListener(e -> switchToLogin());
        backgroundPanel.add(loginButton);

        signUpButton = new RoundButton("Sign Up");
        signUpButton.setBounds(30, 210, 160, 40);
        signUpButton.addActionListener(e -> switchToSignUp());
        backgroundPanel.add(signUpButton);
    }

    /**
     * Switches the content of the JFrame to the login view.
     * This method is called when the login button is clicked.
     */
    private void switchToLogin() {
        // Cambiar el contenido del JFrame a la vista de login
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new LoginView());  // Suponiendo que LoginView es otro JPanel
        parentFrame.revalidate();
        parentFrame.repaint();
    }
    /**
     * Switches the content of the JFrame to the sign-up view.
     * This method is called when the sign-up button is clicked.
     */
    private void switchToSignUp() {
        // Cambiar el contenido del JFrame a la vista de sign-up
        JFrame parentFrame = (JFrame) SwingUtilities.getWindowAncestor(this);
        parentFrame.setContentPane(new RegisterView());  // Suponiendo que RegisterView es otro JPanel
        parentFrame.revalidate();
        parentFrame.repaint();
    }
}
