package xtremvaders.Audio;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import xtremvaders.Utilities.AudioPlayer;

public class AudioDirector {

    private static AudioDirector instance;

    private String currentMusic;

    private AudioDirector() {
        currentMusic = null;
    }

    // Accès global au singleton
    public static synchronized AudioDirector getInstance() {
        if (instance == null) {
            instance = new AudioDirector();
        }
        return instance;
    }

   
    public void onPauseMenuOpened() {
        System.out.println("try to stop all sounds ...");
        AudioPlayer.stopAll();
    }

    // Joue une musique (interrompt l'ancienne si nécessaire)
    public void playMusic(String musicToPlay) {
        
        // if (currentMusic != null && !currentMusic.equals(musicToPlay)) {
        //     stopMusic();
        // }
        

        currentMusic = musicToPlay;
        AudioPlayer.play(musicToPlay); // Ex : "musics/theme1"
    }

    // Joue un effet sonore (ne coupe pas la musique)
    public void playSFX(String sfxName) {
        AudioPlayer.play(sfxName); // Ex : "sfx/explosion1"
    }

    // Arrête la musique actuelle
    public void stopMusic() {
        if (currentMusic != null) {
            AudioPlayer.stop(currentMusic);
            currentMusic = null;
        }
    }

    // Vérifie si une musique est en cours
    public boolean isMusicPlaying() {
        return currentMusic != null;
    }

    // Accès à la musique actuelle (pour debug, logs...)
    public String getCurrentMusic() {
        return currentMusic;
    }

    private AudioTrack lastTrack = null;

    public void playRandomTrackInRange(int minBPM, int maxBPM) {
        List<AudioTrack> candidates = getTracksForBPMRange(minBPM, maxBPM)
            .stream()
            .filter(track -> !track.equals(lastTrack))
            .collect(Collectors.toList());

        if (!candidates.isEmpty()) {
            AudioTrack selected = candidates.get(new Random().nextInt(candidates.size()));
            playMusic(selected.getPath());
            lastTrack = selected;
        }
    }

    public List<AudioTrack> getTracksForBPMRange(int min, int max) {
    return Arrays.stream(TrackID.values())
        .map(TrackID::get)
        .filter(t -> t.bpm >= min && t.bpm <= max)
        .collect(Collectors.toList());
}

}
