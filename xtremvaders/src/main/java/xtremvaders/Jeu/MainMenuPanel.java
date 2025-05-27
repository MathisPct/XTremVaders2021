package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.RenderingHints;
import java.awt.Shape;
import java.awt.event.ActionListener;
import java.awt.geom.RoundRectangle2D;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.AbstractButton;
import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.Timer;

public class MainMenuPanel extends JPanel {

    private Image backgroundImage;
    private Font customFont;

    private JButton startButton;
    private JButton settingsButton;
    private JButton quitButton;


    private final int verticalOffset; // ⭐ Paramètre personnalisable
    private Runnable startGameCallback; // ⭐ Injecté depuis l'extérieur


    private static final int TOTAL_FRAMES = 50; // adapte selon ton nombre d’images
    private static final int ANIMATION_DELAY_MS = 10;

    private int currentFrame = 0;
    private Timer animationTimer;
    private Image[] backgroundFrames;


private SettingsOverlayPanel settingsDialog;

    public MainMenuPanel(int verticalOffset, int width, int height) {
        this.verticalOffset = verticalOffset;

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(width, height));

        loadBackgroundImages();
        startBackgroundAnimation();

        loadCustomFont();

        initSettingsDialog();  // instanciation et ajout du settingsOverlay
        initButtons();
    }

    public void setStartGameCallback(Runnable callback) {
        this.startGameCallback = callback;
    }

    private void startBackgroundAnimation() {
        animationTimer = new Timer(ANIMATION_DELAY_MS, e -> {
            currentFrame = (currentFrame + 1) % TOTAL_FRAMES;
            repaint();
        });
        animationTimer.start();
    }


    private void loadBackgroundImages() {
        backgroundFrames = new Image[TOTAL_FRAMES];
        for (int i = 0; i < TOTAL_FRAMES; i++) {
            String path = String.format("/menus/menu_principal_animation_compressed/menu_principal_animation_%05d_optimized.png", i);
            try {
                backgroundFrames[i] = ImageIO.read(getClass().getResource(path));
            } catch (IOException | IllegalArgumentException e) {
                System.err.println("Erreur chargement image: " + path);
                backgroundFrames[i] = null;
            }
        }
    }


    private void loadCustomFont() {
        try {
            customFont = Font.createFont(Font.TRUETYPE_FONT, getClass().getResourceAsStream("/fonts/Orbitron-Bold.ttf")).deriveFont(20f);
            GraphicsEnvironment.getLocalGraphicsEnvironment().registerFont(customFont);
        } catch (Exception e) {
            e.printStackTrace();
            customFont = new Font("Arial", Font.BOLD, 20);
        }
    }

    private void initSettingsDialog() {
        settingsDialog = new SettingsOverlayPanel();
        settingsDialog.setVisible(false); // caché au départ
        add(settingsDialog);
        // Position déjà gérée par SettingsOverlayPanel via setBounds
    }


    private void initButtons() {
        int buttonWidth = 250;
        int buttonHeight = 50;
        int spacing = 20;

        int totalHeight = (buttonHeight * 3) + spacing * 2;
        int startY = (getPreferredSize().height - totalHeight) / 2 + verticalOffset;
        int centerX = (getPreferredSize().width - buttonWidth) / 2;

        startButton = createStyledButton("Start New Game", e -> {
            if (startGameCallback != null) {
                setVisible(false);  // On masque le panneau pause 
                startGameCallback.run(); //on appelle la callback qui va lancer la game
            }
        });
        startButton.setBounds(centerX, startY, buttonWidth, buttonHeight);

        settingsButton = createStyledButton("Settings", e -> settingsDialog.setVisible(true));
        settingsButton.setBounds(centerX, startY + buttonHeight + spacing, buttonWidth, buttonHeight);

        quitButton = createStyledButton("Quit Game", e -> System.exit(0));
        quitButton.setBounds(centerX, startY + 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);

        add(startButton);
        add(settingsButton);
        add(quitButton);
    }

    private JButton createStyledButton(String text, ActionListener action) {
        JButton button = new JButton(text);
        button.setFont(customFont);
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

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Dessine le fond animé si disponible
        if (backgroundFrames != null && backgroundFrames[currentFrame] != null) {
            g.drawImage(backgroundFrames[currentFrame], 0, 0, getWidth(), getHeight(), this);
        }

        // Voile semi-transparent
        g.setColor(new Color(0, 0, 0, 0));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Texte
        g.setColor(Color.WHITE);
        String text = "PAUSE";
        int textWidth = g.getFontMetrics().stringWidth(text);
        g.drawString(text, (getWidth() - textWidth) / 2, 100);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        if (animationTimer != null) {
            if (aFlag) animationTimer.start();
            else animationTimer.stop();
        }
    }


}
