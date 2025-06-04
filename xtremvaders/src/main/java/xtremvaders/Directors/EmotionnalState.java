package xtremvaders.Directors;

public class EmotionnalState {
    public int lives;
    public int score;
    public int timeSinceLastShot; // en secondes
    public int timeSinceLastHit;  // en secondes

    public double excitement;     // 0 à 1 (ex. : combos, explosions)
    public double wonder;         // 0 à 1 (visuels rares, surprises)
    public double boredom;        // 0 à 1 (inaction, répétition)

    public EmotionnalState(int lives) {
        this.lives = lives;
        this.score = 0;
        this.timeSinceLastShot = 0;
        this.timeSinceLastHit = 0;
        this.excitement = 0;
        this.wonder = 0;
        this.boredom = 0;
    }
}
