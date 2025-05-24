package xtremvaders.Audio;

import xtremvaders.Utilities.Sons;

public class AudioDirector {

    private String currentMusic;

    public AudioDirector() {
        currentMusic = null;
    }

    // Joue une musique (avec arrêt de la musique actuelle si besoin)
    public void playMusic(String musicToPlay) {
        if (currentMusic != null) {
            stopMusic();
        }

        this.currentMusic = musicToPlay;
        Sons.play(musicToPlay); // suppose que Sons.play() démarre la musique
    }

    // Joue un effet sonore sans arrêter la musique
    public void playSFX(String name) {
        Sons.play(name); // effets sonores courts (ex: "sfx/explosion1")
    }

    // Arrête la musique actuelle
    public void stopMusic() {
        if (currentMusic != null) {
            Sons.stop(currentMusic);
            currentMusic = null;
        }
    }

   
}
