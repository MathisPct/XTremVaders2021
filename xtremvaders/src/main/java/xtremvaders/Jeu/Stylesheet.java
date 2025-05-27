package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;

import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;

public class Stylesheet {

    public static JButton createStyledButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setContentAreaFilled(false);
        button.setBorder(BorderFactory.createEmptyBorder());

        button.setUI(new javax.swing.plaf.basic.BasicButtonUI() {
            @Override
            public void paint(Graphics g, JComponent c) {
                Graphics2D g2 = (Graphics2D) g.create();
                AbstractButton b = (AbstractButton) c;

                g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);
                Shape rounded = new RoundRectangle2D.Float(0, 0, b.getWidth(), b.getHeight(), 20, 20);
                g2.setColor(b.getModel().isRollover() ? new Color(60, 60, 60, 220) : new Color(30, 30, 30, 180));
                g2.fill(rounded);
                g2.setColor(Color.WHITE);
                g2.draw(rounded);

                g2.setFont(b.getFont());
                FontMetrics fm = g2.getFontMetrics();
                int textWidth = fm.stringWidth(b.getText());
                int textHeight = fm.getAscent();
                g2.drawString(b.getText(), (b.getWidth() - textWidth) / 2, (b.getHeight() + textHeight) / 2 - 4);
                g2.dispose();
            }
        });

        button.addActionListener(action);
        return button;
    }
}
