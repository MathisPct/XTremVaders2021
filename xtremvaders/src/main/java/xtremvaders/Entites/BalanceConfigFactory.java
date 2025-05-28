package xtremvaders.Entites;

public class BalanceConfigFactory {

    private BalanceConfig difficulty;
    private static DifficultyLevel currentDifficulty = DifficultyLevel.MEDIUM;

    public static void applyDifficulty(DifficultyLevel newDifficulty) {
        System.out.println("Nouveau niveau de difficulté sélectionné : " + newDifficulty);
        //TODO should notify the other (observe)
       currentDifficulty = newDifficulty;
    }

    public static DifficultyLevel getCurrentDifficulty() {
        return currentDifficulty;
    }

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
                    .timeBeforeNextShotMs(140)
                    .build();

            case MEDIUM:
                return new BalanceConfig.Builder()
                    .enemyHealth(100)
                    .enemyDamage(15)
                    .spawnRate(1.0f)
                    .timeBeforeNextShotMs(300)
                    .build();

            case HARD:
                return new BalanceConfig.Builder()
                    .enemyHealth(200)
                    .enemyDamage(30)
                    .spawnRate(1.5f)
                    .timeBeforeNextShotMs(1000)
                    .build();

            default:
                throw new IllegalArgumentException("Niveau de difficulté inconnu : " + level);
        }
    }
}
