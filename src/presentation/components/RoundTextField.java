package presentation.components;

import javax.swing.*;
import java.awt.*;

public class RoundTextField extends JTextField {
    private int arc = 20; // Radio de redondeo

    public RoundTextField(int columns) {
        super(columns);
        setOpaque(false); // Hace que el fondo del componente sea transparente
        setBorder(BorderFactory.createEmptyBorder(5, 10, 5, 10)); // Espaciado interno
    }

    @Override
    protected void paintComponent(Graphics g) {
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        // Fondo del campo de texto
        g2.setColor(Color.WHITE);
        g2.fillRoundRect(0, 0, getWidth(), getHeight(), arc, arc);

        // Dibujar el borde
        g2.setColor(Color.GRAY);
        g2.drawRoundRect(0, 0, getWidth() - 1, getHeight() - 1, arc, arc);

        g2.dispose();
        super.paintComponent(g);
    }
}
