package xtremvaders.Directors;

public interface IDirectorCommands {
    void spawnWave();
    void spawnBoss();
    boolean getIsBossActive();
    int getEnemiesOnScreen();
}


