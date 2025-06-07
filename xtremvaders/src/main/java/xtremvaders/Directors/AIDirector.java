package xtremvaders.Directors;

import xtremvaders.Runtime.GameRuntime;

public class AIDirector {
    private int calmCooldown = 0;
    private double stressLevel = 0.0;

    private GameState gameState;
    private EmotionnalState emotionalState;
    private IDirectorCommands directorCommands;

    public AIDirector(GameState gameState,
                      EmotionnalState emotionalState,
                      IDirectorCommands directorCommands) {
        this.gameState = gameState;
        this.emotionalState = emotionalState;
        this.directorCommands = directorCommands;
    }

    public void update(long dt) {
        long scaledDt = GameRuntime.getScaledDt(dt);
        gameState.timeSinceLastWave =  gameState.timeSinceLastWave + scaledDt;
        gameState.timeSinceLastBoss =  gameState.timeSinceLastBoss + scaledDt;
        gameState.totalElapsedTime =  gameState.totalElapsedTime + dt;
        gameState.bossActive = directorCommands.getIsBossActive();
        gameState.enemiesOnScreen = directorCommands.getEnemiesOnScreen();

        // Calcul du stress (pression)
        /*
        stressLevel = gameState.enemiesOnScreen * 0.4
                    + gameState.missilesOnScreen * 0.3
                    + (gameState.bossActive ? 2 : 0)
                    - emotionalState.lives * 0.5;
        */

        // Si le stress est bas, on peut remettre une vague
        if (gameState.timeSinceLastWave > 20 * 1000 || gameState.enemiesOnScreen == 0) {
            directorCommands.spawnWave();
            gameState.timeSinceLastWave = 0;
        }

        // Boss logique
        if (gameState.timeSinceLastBoss > 45 * 1000 &&  gameState.bossActive == false) {
            directorCommands.spawnBoss();
            gameState.timeSinceLastBoss = 0;
        }

        if (calmCooldown > 0) {
            calmCooldown--;
        }
    }
}
