package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;

public class SettingsOverlayPanel extends JPanel {

    private JSlider volumeSlider;
    private JSlider musicSlider;
    private JButton closeButton;

    public SettingsOverlayPanel() {
        setOpaque(false);
        setLayout(null);

        // Fond transparent foncé + bord arrondi
        setBackground(new Color(0, 0, 0, 220));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        // Taille fixe pour le panneau (exemple)
        setBounds(150, 100, 500, 300);

        initComponents();
    }

    private void initComponents() {
        Font labelFont = new Font("Orbitron", Font.BOLD, 18);

        JLabel volumeLabel = new JLabel("Volume Général");
        volumeLabel.setForeground(Color.WHITE);
        volumeLabel.setFont(labelFont);
        volumeLabel.setBounds(50, 30, 200, 30);
        add(volumeLabel);

        volumeSlider = new JSlider(0, 100, 75);
        volumeSlider.setBounds(50, 70, 400, 50);
        volumeSlider.setOpaque(false);
        add(volumeSlider);

        JLabel musicLabel = new JLabel("Volume Musique");
        musicLabel.setForeground(Color.WHITE);
        musicLabel.setFont(labelFont);
        musicLabel.setBounds(50, 130, 200, 30);
        add(musicLabel);

        musicSlider = new JSlider(0, 100, 60);
        musicSlider.setBounds(50, 170, 400, 50);
        musicSlider.setOpaque(false);
        add(musicSlider);

        closeButton = new JButton("Fermer");
        closeButton.setBounds(200, 230, 100, 40);
        closeButton.setFont(labelFont);
        closeButton.setForeground(Color.WHITE);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        closeButton.setFocusPainted(false);

        closeButton.addActionListener(e -> setVisible(false));
        add(closeButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        // Fond semi-transparent arrondi
        Graphics2D g2 = (Graphics2D) g.create();
        g2.setRenderingHint(RenderingHints.KEY_ANTIALIASING, RenderingHints.VALUE_ANTIALIAS_ON);

        g2.setColor(getBackground());
        g2.fill(new RoundRectangle2D.Double(0, 0, getWidth(), getHeight(), 30, 30));

        g2.dispose();

        super.paintComponent(g);
    }

    public int getVolume() {
        return volumeSlider.getValue();
    }

    public int getMusicVolume() {
        return musicSlider.getValue();
    }
}
