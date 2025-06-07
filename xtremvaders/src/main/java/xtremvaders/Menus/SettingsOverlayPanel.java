package xtremvaders.Menus;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.RoundRectangle2D;
import java.util.Timer;
import java.util.TimerTask;
import java.util.function.Consumer;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

import xtremvaders.Audio.Tracks.AudioTrack;
import xtremvaders.Audio.Tracks.TrackID;
import xtremvaders.Directors.AudioDirector;
import xtremvaders.Gameplay.Balance.BalanceConfigFactory;
import xtremvaders.Gameplay.Balance.BalanceConfigFactory.DifficultyLevel;

public class SettingsOverlayPanel extends JPanel {

    private JSlider volumeSlider;
    private JSlider musicSlider;
    private JComboBox<DifficultyLevel> difficultyCombo;
    private JComboBox<TrackID> musicTrackCombo;

    private JButton closeButton;

    private final Consumer<DifficultyLevel> onDifficultyChanged;

    public SettingsOverlayPanel(int width, int height, Consumer<DifficultyLevel> onDifficultyChanged) {
        this.onDifficultyChanged = onDifficultyChanged;
        setOpaque(false);
        setLayout(null);

        // Fond transparent foncé + bord arrondi
        setBackground(new Color(0, 0, 0, 250));
        setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));

        // Taille fixe pour le panneau (exemple)
        setBounds(0, 0, width, height);

        initComponents();
    }

    private Timer throttleTimer;
    private static final int THROTTLE_DELAY_MS = 200; // déclenche tous les 100ms au maximum

    private void initComponents() {
        Font labelFont = new Font("Orbitron", Font.BOLD, 18);

        int panelWidth = getWidth() > 0 ? getWidth() : 500;
        int panelHeight = getHeight() > 0 ? getHeight() : 350;

        int marginLeft = 50;
        int labelWidth = 200;
        int sliderWidth = panelWidth - marginLeft * 2;
        int controlHeight = 30;
        int sliderHeight = 50;
        int verticalSpacing = 40;
        int yPos = 30;

        yPos += controlHeight;

                // Volume musique
        JLabel musicLabel = new JLabel("Volume général");
        musicLabel.setForeground(Color.WHITE);
        musicLabel.setFont(labelFont);
        musicLabel.setBounds(marginLeft, yPos, labelWidth, controlHeight);
        
        add(musicLabel);
        yPos += verticalSpacing;
        volumeSlider = new JSlider(0, 100, 60);
        volumeSlider.setBounds(marginLeft, yPos, sliderWidth, sliderHeight);
        volumeSlider.setOpaque(false);

        // Dans ton constructeur ou méthode d'init :
        volumeSlider.addChangeListener(new ChangeListener() {
            @Override
            public void stateChanged(ChangeEvent e) {
                if (throttleTimer != null) {
                    throttleTimer.cancel(); // annule le précédent timer
                }

                throttleTimer = new Timer();
                throttleTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        int volume = volumeSlider.getValue();
                        float normalized = (float) (volume / 100.0);
                        AudioDirector.getInstance().changeVolume(normalized);
                        System.out.println("Volume changé (throttled) : " + volume + "%");
                    }
                }, THROTTLE_DELAY_MS);
            }
        });

        add(volumeSlider);

        yPos += controlHeight;
        yPos += sliderHeight + verticalSpacing;

        // Difficulté
        JLabel difficultyLabel = new JLabel("Difficulté");
        difficultyLabel.setForeground(Color.WHITE);
        difficultyLabel.setFont(labelFont);
        difficultyLabel.setBounds(marginLeft, yPos, labelWidth, controlHeight);
        add(difficultyLabel);

        difficultyCombo = new JComboBox<>(DifficultyLevel.values());
        int comboWidth = 350;
        int comboHeight = 40;
        int labelHeight = controlHeight;
        int comboY = yPos - (comboHeight - labelHeight) / 2;

        difficultyCombo.setBounds(marginLeft + labelWidth + 10, comboY, comboWidth, comboHeight);
        difficultyCombo.setFont(labelFont.deriveFont(Font.BOLD, 20f));
        difficultyCombo.setBackground(Color.BLACK);
        difficultyCombo.setForeground(Color.WHITE);

        // Custom renderer pour padding intérieur
        difficultyCombo.setRenderer(new javax.swing.DefaultListCellRenderer() {
            private final int padding = 10;  // padding à gauche et droite

            @Override
            public java.awt.Component getListCellRendererComponent(javax.swing.JList<?> list, Object value,
                    int index, boolean isSelected, boolean cellHasFocus) {
                JLabel label = (JLabel) super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);
                label.setBorder(javax.swing.BorderFactory.createEmptyBorder(0, padding, 0, padding));
                return label;
            }
        });

        add(difficultyCombo);

        DifficultyLevel current = BalanceConfigFactory.getCurrentDifficulty();
        difficultyCombo.setSelectedItem(current);

        difficultyCombo.addActionListener(e -> {
            DifficultyLevel selected = (DifficultyLevel) difficultyCombo.getSelectedItem();
            if (onDifficultyChanged != null && selected != null) {
                onDifficultyChanged.accept(selected);
            }
        });

        // Met à jour yPos après difficulté
        yPos += controlHeight + verticalSpacing;

        // Label pour la musique
        JLabel trackLabel = new JLabel("Choix Musique");
        trackLabel.setForeground(Color.WHITE);
        trackLabel.setFont(labelFont);
        trackLabel.setBounds(marginLeft, yPos, labelWidth, controlHeight);
        add(trackLabel);

        // ComboBox pour choisir une musique
        JComboBox<TrackID> trackCombo = new JComboBox<>(TrackID.values());
        int trackComboY = yPos - (comboHeight - labelHeight) / 2;
        trackCombo.setBounds(marginLeft + labelWidth + 10, trackComboY, comboWidth, comboHeight);
        trackCombo.setFont(labelFont.deriveFont(Font.BOLD, 20f));
        trackCombo.setBackground(Color.BLACK);
        trackCombo.setForeground(Color.WHITE);

        // Initialiser la sélection avec lastTrack de audioDirector
        AudioTrack lastTrack = AudioDirector.getInstance().getLastTrack(); // ou méthode qui retourne AudioTrack
        TrackID lastTrackID = TrackID.fromAudioTrack(lastTrack);
        if (lastTrackID != null) {
            trackCombo.setSelectedItem(lastTrackID);
        }

        // Ajout de l'action sur sélection
        trackCombo.addActionListener(e -> {
            TrackID selectedTrack = (TrackID) trackCombo.getSelectedItem();
            if (selectedTrack != null) {
                playSelectedTrack(selectedTrack);
            }
        });

        add(trackCombo);


        yPos += controlHeight + verticalSpacing;

        // Bouton Fermer placé en bas
        int buttonWidth = 100;
        int buttonHeight = 40;
        int bottomPadding = 30;

        closeButton = new JButton("Fermer");
        closeButton.setBounds(
            (panelWidth - buttonWidth) / 2,
            panelHeight - buttonHeight - bottomPadding,
            buttonWidth,
            buttonHeight
        );
        closeButton.setFont(labelFont);
        closeButton.setForeground(Color.WHITE);
        closeButton.setContentAreaFilled(false);
        closeButton.setBorder(BorderFactory.createLineBorder(Color.WHITE, 2, true));
        closeButton.setFocusPainted(false);
        closeButton.addActionListener(e -> setVisible(false));
        add(closeButton);
    }


    private void playSelectedTrack(TrackID trackID) {
        AudioDirector.getInstance().playSelectedTrack(trackID);
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

    public DifficultyLevel getSelectedDifficulty() {
        return (DifficultyLevel) difficultyCombo.getSelectedItem();
    }
}
