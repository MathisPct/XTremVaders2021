package xtremvaders.Directors;

public class GameState {
    public int enemiesOnScreen = 0;
    public int missilesOnScreen = 0;
    public boolean bossActive = false;

    public int timeSinceLastWave = 0; // en secondes
    public int timeSinceLastBoss = 0; // en secondes

    public int asteroidsOnScreen = 0;
    public int powerUpsOnScreen = 0;

    public int totalElapsedTime = 0; // Temps total depuis début partie, en secondes

    public void tick() {
        timeSinceLastWave++;
        timeSinceLastBoss++;
        totalElapsedTime++;
    }

    public void resetWaveTimer() {
        timeSinceLastWave = 0;
    }

    public void resetBossTimer() {
        timeSinceLastBoss = 0;
    }
}
