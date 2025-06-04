package xtremvaders.Directors;

public interface DirectorCommands {
    void spawnWave();
    void spawnBoss();
    void spawnSurpriseEnemy();
    void giveBonus();
    void reducePressure();

    void showSpectacle();   // Émerveillement visuel
    void createComboMoment(); // Opportunité de scoring fou
}


