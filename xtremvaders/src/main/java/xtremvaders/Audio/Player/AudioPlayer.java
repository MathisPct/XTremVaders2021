package xtremvaders.Audio.Player;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class AudioPlayer {
    private static final Map<String, ManagedAudio> playingSounds = new ConcurrentHashMap<>();

    public static void play(String name) {
        if (playingSounds.containsKey(name)) {
            return;
        }

        ManagedAudio managedAudio = new ManagedAudio(name);
        

        // 🔁 callback pour nettoyer à la fin du son
        managedAudio.setOnEnd(() -> playingSounds.remove(name));

        playingSounds.put(name, managedAudio);
        managedAudio.play();
    }

    public static void setVolume(float volume) {
        for (ManagedAudio ma : playingSounds.values()) {
            ma.setVolume(volume);
        }
    }


    ///TODO dont work atm
    public static void playAll() {
        for (ManagedAudio ma : playingSounds.values()) {
            ma.play();
        }
    }


    public static void stop(String name) {
        ManagedAudio managedAudio = playingSounds.get(name);
        if (managedAudio != null) {
            managedAudio.stop();
            playingSounds.remove(name);
        }
    }

    public static void stopAll() {
        for (ManagedAudio ma : playingSounds.values()) {
            ma.stop();
        }
       playingSounds.clear();
    }


}
