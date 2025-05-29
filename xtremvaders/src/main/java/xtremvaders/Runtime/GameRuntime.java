package xtremvaders.Runtime;

public class GameRuntime {
    private static GameSpeed gameSpeed;

    public static void init(GameSpeed gs) {
        gameSpeed = gs;
    }

    public static void pause() {
        if(gameSpeed.isPaused()) {
            System.err.println("Cannot pause game again, game already paused, watch your implementation");
            return;
        } 
        gameSpeed.pause();
    }

    public static void resume() {
        if(gameSpeed.isPaused()) {
            System.err.println("Cannot resume game again, game already resumed, watch your implementation");
            return;
        }
        gameSpeed.resume();
    }

    public static float getTimeSpeed() {
        return gameSpeed.getTimeSpeed();
    }

    public static long getScaledDt(long dt) {
        if (gameSpeed == null) return dt;
        return (long)(dt * gameSpeed.getTimeSpeed());
    }
}
