package xtremvaders.Jeu;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;

import javax.swing.JButton;
import javax.swing.JPanel;

import xtremvaders.Entites.BalanceConfigFactory;

public class PausePanel extends JPanel {


    private JButton startButton;
    private JButton settingsButton;
    private JButton quitButton;

    private final int verticalOffset; // ⭐ Paramètre personnalisable
    private Runnable setResumeGameCallback; // ⭐ Injecté depuis l'extérieur



private SettingsOverlayPanel settingsDialog;

    public PausePanel(
        int verticalOffset,
        int width, 
        int height
        ) {
        this.verticalOffset = verticalOffset;

        setOpaque(false);
        setLayout(null);
        setPreferredSize(new Dimension(width, height));


        initSettingsDialog();  // instanciation et ajout du settingsOverlay
        initButtons();

        this.setFocusable(false);
        this.setRequestFocusEnabled(false);
    }

    public void setResumeGameCallback(Runnable callback) {
        this.setResumeGameCallback = callback;
    }


    private void initSettingsDialog() {
        settingsDialog = new SettingsOverlayPanel(
            getWidth(),
            getHeight(),
            (difficultyLevel) -> {
                System.out.println("Nouveau niveau de difficulté sélectionné : " + difficultyLevel);
                BalanceConfigFactory.applyDifficulty(difficultyLevel);
            }
        );
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

        startButton = Stylesheet.createStyledButton("Resume", e -> {
            if (setResumeGameCallback != null) {
                setVisible(false);  // On masque le panneau pause 
                setResumeGameCallback.run(); //on appelle la callback qui va lancer la game
            }
        });
        startButton.setBounds(centerX, startY, buttonWidth, buttonHeight);

        settingsButton = Stylesheet.createStyledButton("Settings", e -> settingsDialog.setVisible(true));
        settingsButton.setBounds(centerX, startY + buttonHeight + spacing, buttonWidth, buttonHeight);

        quitButton = Stylesheet.createStyledButton("Quit", e -> System.exit(0));
        quitButton.setBounds(centerX, startY + 2 * (buttonHeight + spacing), buttonWidth, buttonHeight);

        add(startButton);
        add(settingsButton);
        add(quitButton);
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        // Voile semi-transparent
        g.setColor(new Color(0, 0, 0, 255));
        g.fillRect(0, 0, getWidth(), getHeight());

        // Texte
        g.setColor(Color.WHITE);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
    }


}
