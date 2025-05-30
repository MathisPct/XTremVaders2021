package xtremvaders.Runtime;

public class GameRuntime {
    private static GameSpeed gameSpeed;

    public static void init(GameSpeed gs) {
        gameSpeed = gs;
    }

    public static boolean isPaused() {
       return gameSpeed.isPaused();
    }

    public static void pause() {
        if(gameSpeed.isPaused() == true) {
            System.err.println("GameRuntime: Cannot pause game again, game already paused, watch your implementation");
            return;
        }
        System.out.println("GameRuntime: Mise en pause de la partie");
        gameSpeed.pause();
    }

    public static void resume() {
        if(gameSpeed.isPaused() == false) {
            System.err.println("GameRuntime: Cannot resume game again, game already playing, watch your implementation");
            return;
        }
        System.out.println("GameRuntime: Reprise de la partie");
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
