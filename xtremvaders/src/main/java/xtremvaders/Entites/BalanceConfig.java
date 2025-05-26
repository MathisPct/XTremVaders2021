package xtremvaders.Entites;

public final class BalanceConfig {
    private final int enemyHealth; //TODO to map to enemies
    private final int enemyDamage; //TODO to map to enemies
    private final float spawnRate; //TODO to map to enemies
    private final int timeBeforeNextShotMs; // temps avant le prochain tir du joueur en millisecondes

    private BalanceConfig(Builder builder) {
        this.enemyHealth = builder.enemyHealth;
        this.enemyDamage = builder.enemyDamage;
        this.spawnRate = builder.spawnRate;
        this.timeBeforeNextShotMs = builder.timeBeforeNextShotMs;
    }

    public int getEnemyHealth() {
        return enemyHealth;
    }

    public int getEnemyDamage() {
        return enemyDamage;
    }

    public float getSpawnRate() {
        return spawnRate;
    }

    public int getTimeBeforeNextShotMs() {
        return timeBeforeNextShotMs;
    }

    @Override
    public String toString() {
        return "BalanceConfig{" +
               "enemyHealth=" + enemyHealth +
               ", enemyDamage=" + enemyDamage +
               ", spawnRate=" + spawnRate +
               ", timeBeforeNextShotMs=" + timeBeforeNextShotMs +
               '}';
    }

    public static class Builder {
        private int enemyHealth = 100;
        private int enemyDamage = 10;
        private float spawnRate = 1.0f;
        private int timeBeforeNextShotMs = 500; // 500ms par défaut

        public Builder enemyHealth(int enemyHealth) {
            this.enemyHealth = enemyHealth;
            return this;
        }

        public Builder enemyDamage(int enemyDamage) {
            this.enemyDamage = enemyDamage;
            return this;
        }

        public Builder spawnRate(float spawnRate) {
            this.spawnRate = spawnRate;
            return this;
        }

        public Builder timeBeforeNextShotMs(int timeBeforeNextShotMs) {
            this.timeBeforeNextShotMs = timeBeforeNextShotMs;
            return this;
        }

        public BalanceConfig build() {
            return new BalanceConfig(this);
        }
    }
}
