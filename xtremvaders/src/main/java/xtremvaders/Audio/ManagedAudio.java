package xtremvaders.Audio;

import iut.Audio;

public class ManagedAudio {
    private String name;
    private Audio audio;
    private Thread thread;
    private boolean isRunning = false;

    public void play() {
        if (isRunning) return;
        isRunning = true;
        thread = new Thread(() -> {
            audio = new Audio(name);
            audio.run();
            isRunning = false;
        });
        thread.start();
    }

    public void stop() {
        isRunning = false;
        if (thread != null && thread.isAlive()) {
            thread.interrupt(); // méthode moderne
        }
    }
}
