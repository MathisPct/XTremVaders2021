package xtremvaders.Jeu;

public class GameSpeed {
    private float timespeed = 1f;
    private float lastNonZeroSpeed = 1f;

    // Getter
    public float getTimeSpeed() {
        return timespeed;
    }

    // Setter avec validation (>= 0)
    public void setTimeSpeed(float timespeed) {
        if (timespeed >= 0) {
            this.timespeed = timespeed;
        }
    }

    // Augmenter la vitesse (multiplier)
    public void increaseSpeed(float factor) {
        if (factor > 0) {
            timespeed *= factor;
        }
    }

    // Réduire la vitesse (diviser)
    public void decreaseSpeed(float factor) {
        if (factor > 0) {
            timespeed /= factor;
        }
    }

    // Vérifie si la vitesse est à 0
    public boolean isPaused() {
        return timespeed == 0;
    }

    // Met en pause (vitesse à 0 mais sauvegarde l'ancienne vitesse)
    public void pause() {
        System.out.println("PAUSED GAME");
        if (timespeed > 0) {
            lastNonZeroSpeed = timespeed;
            timespeed = 0;
        }
    }

    // Reprend depuis la dernière vitesse non nulle
    public void resume() {
        if (timespeed == 0) {
            timespeed = lastNonZeroSpeed;
        }
    }
}
