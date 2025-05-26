package xtremvaders.Jeu;

public class GameRuntime {
    private static GameSpeed gameSpeed;

    public static void init(GameSpeed gs) {
        gameSpeed = gs;
    }

    public static GameSpeed getGameSpeed() {
        return gameSpeed;
    }

    public static long getScaledDt(long dt) {
        if (gameSpeed == null) return dt;
        return (long)(dt * gameSpeed.getTimeSpeed());
    }
}
