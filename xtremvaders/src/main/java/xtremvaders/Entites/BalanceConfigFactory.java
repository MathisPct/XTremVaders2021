package xtremvaders.Entites;

public class BalanceConfigFactory {

    public enum DifficultyLevel {
        EASY,
        MEDIUM,
        HARD
    }

    public static BalanceConfig createConfig(DifficultyLevel level) {
        switch (level) {
            case EASY:
                return new BalanceConfig.Builder()
                    .enemyHealth(50)
                    .enemyDamage(5)
                    .spawnRate(0.5f)
                    .timeBeforeNextShotMs(160)
                    .build();

            case MEDIUM:
                return new BalanceConfig.Builder()
                    .enemyHealth(100)
                    .enemyDamage(15)
                    .spawnRate(1.0f)
                    .timeBeforeNextShotMs(200)
                    .build();

            case HARD:
                return new BalanceConfig.Builder()
                    .enemyHealth(200)
                    .enemyDamage(30)
                    .spawnRate(1.5f)
                    .timeBeforeNextShotMs(300)
                    .build();

            default:
                throw new IllegalArgumentException("Niveau de difficulté inconnu : " + level);
        }
    }
}
