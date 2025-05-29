package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JButton;
import javax.swing.JPanel;
import javax.swing.Timer;

import xtremvaders.Entites.BalanceConfigFactory;

public class MainMenuPanel extends JPanel {

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

    private int width;
    private int height;


private SettingsOverlayPanel settingsDialog;

    public MainMenuPanel(
        int verticalOffset, 
        int width, int height
    ) {
        this.width = width;
        this.height = height;
        this.verticalOffset = verticalOffset;

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(width, height));

        loadBackgroundImages();
        startBackgroundAnimation();

        initSettingsDialog();  // instanciation et ajout du settingsOverlay
        initButtons();

        this.setFocusable(false);
        this.setRequestFocusEnabled(false);
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

    private void initSettingsDialog() {
        settingsDialog = new SettingsOverlayPanel(
            width,
            height,
            difficultyLevel -> {
            BalanceConfigFactory.applyDifficulty(difficultyLevel);
        });

        settingsDialog.setVisible(false);
        add(settingsDialog);
    }


    private void initButtons() {
        int buttonWidth = 250;
        int buttonHeight = 50;
        int spacing = 20;

        int totalHeight = (buttonHeight * 3) + spacing * 2;
        int startY = (getPreferredSize().height - totalHeight) / 2 + verticalOffset;
        int centerX = (getPreferredSize().width - buttonWidth) / 2;

        startButton = Stylesheet.createStyledButton("Start New Game", e -> {
            if (startGameCallback != null) {
                setVisible(false);  // On masque le panneau pause 
                startGameCallback.run(); //on appelle la callback qui va lancer la game
            }
        });
        startButton.setBounds(centerX, startY, buttonWidth, buttonHeight);

        settingsButton = Stylesheet.createStyledButton("Settings", e -> settingsDialog.setVisible(true));
        settingsButton.setBounds(centerX, startY + buttonHeight + spacing, buttonWidth, buttonHeight);

        quitButton = Stylesheet.createStyledButton("Quit Game", e -> System.exit(0));
        quitButton.setBounds(centerX, startY + 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);

        add(startButton);
        add(settingsButton);
        add(quitButton);
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
