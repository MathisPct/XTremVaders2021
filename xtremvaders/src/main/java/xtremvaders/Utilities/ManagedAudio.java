package xtremvaders.Utilities;

import xtremvaders.Audio.Player.AudioV2;

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
        System.out.println("Managing audio killing " + audio.getName());
        
        thread.interrupt(); // si le thread est bloqué, on le réveille proprement
        thread.stop();
        audio.stop();
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
