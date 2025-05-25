package xtremvaders.Utilities;

public class ManagedAudio implements Runnable {
    private final AudioV2 audio;
    private volatile boolean stopped = false;
    private final Thread thread;
    private Runnable onEnd; // callback appelé quand le son est fini

    public ManagedAudio(String name) {
        this.audio = new AudioV2(name);
        this.thread = new Thread(this);
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

    public void play() {
        thread.start();
    }

    public void stop() {
        stopped = true;
        audio.stop(); // supposé que cette méthode existe
    }

    public boolean isStopped() {
        return stopped;
    }

    @Override
    public void run() {
        if (!stopped) {
            audio.run(); // joue le son
        }
        if (onEnd != null) {
            onEnd.run(); // notification de fin
        }
    }
}
