package xtremvaders.Audio.Player;

public class ManagedAudio implements Runnable {
    private final AudioV2 audio;
    private volatile boolean stopped = false;
    private final Thread thread;
    private Runnable onEnd;
    private Float pendingVolume = null;

    public ManagedAudio(String name) {
        this.audio = new AudioV2(name);
        this.thread = new Thread(this);
    }

    public void setOnEnd(Runnable onEnd) {
        this.onEnd = onEnd;
    }

    public void play() {
        if (pendingVolume != null) {
            audio.setVolume(pendingVolume);
            pendingVolume = null;
        }
        thread.start();
    }

    public void stop() {
        stopped = true;
        thread.interrupt(); // pour interrompre un éventuel sleep dans AudioV2
        audio.stopReimplemented(); // méthode qui interrompt proprement le son
    }

    public boolean isStopped() {
        return stopped;
    }

    public void setVolume(float volume) {
        volume = Math.max(0f, Math.min(1f, volume)); // Clamp entre 0 et 1

        if (audio != null) {
            audio.setVolume(volume);
        } else {
            pendingVolume = volume;
        }
    }

    @Override
    public void run() {
        if (!stopped) {
            audio.run(); // Attention : ce run() doit gérer le "stop" proprement !
        }
        if (onEnd != null) {
            onEnd.run();
        }
    }
}
