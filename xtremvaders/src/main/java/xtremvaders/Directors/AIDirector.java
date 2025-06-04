package xtremvaders.Directors;

public class AIDirector {
    private int calmCooldown = 0;
    private double stressLevel = 0.0;

    private GameState gameState;
    private EmotionnalState emotionalState;
    private DirectorCommands directorCommands;

    public AIDirector(GameState gameState,
                      EmotionnalState emotionalState,
                      DirectorCommands directorCommands) {
        this.gameState = gameState;
        this.emotionalState = emotionalState;
        this.directorCommands = directorCommands;
    }

    public void update() {

        // Calcul du stress (pression)
        stressLevel = gameState.enemiesOnScreen * 0.4
                    + gameState.missilesOnScreen * 0.3
                    + (gameState.bossActive ? 2 : 0)
                    - emotionalState.lives * 0.5;

        // Réduction de la pression si le joueur est trop sous l'eau
        if (stressLevel > 5 && calmCooldown <= 0) {
            directorCommands.reducePressure();
            directorCommands.giveBonus();
            calmCooldown = 10;
        }

        // Si le stress est bas, on peut remettre une vague
        if (stressLevel < 2 && gameState.timeSinceLastWave > 10) {
            directorCommands.spawnWave();
        }

        // Boss logique
        if (gameState.timeSinceLastBoss > 30 && emotionalState.score > 1000) {
            directorCommands.spawnBoss();
        }

        // Inactivité → ennemi surprise
        if (emotionalState.timeSinceLastShot > 20) {
            directorCommands.spawnSurpriseEnemy();
        }

        // Émerveillement si le joueur s’ennuie
        if (emotionalState.boredom > 0.7) {
            directorCommands.showSpectacle();
        }

        // Combo chance si le joueur est en flow
        if (emotionalState.excitement > 0.8 && emotionalState.timeSinceLastHit > 15) {
            directorCommands.createComboMoment();
        }

        if (calmCooldown > 0) {
            calmCooldown--;
        }
    }
}
