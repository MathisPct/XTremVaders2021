package xtremvaders.Audio;

import java.util.Arrays;
import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import xtremvaders.Jeu.XtremVaders2021;

public class AudioDirector {

    private static AudioDirector instance;

    private AudioTrack lastTrack = null;


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

    public AudioTrack getLastTrack() {
        return lastTrack;
    }

    public void onLaunchGame() {
        //TODO EMPECHER DE FAIRE CA DEUX FOIS
        System.out.println("On launch game");
        AudioDirector.getInstance().playRandomTrackInRange(0, 300);
    }

    public void onResumeGame() {
        //TODO EMPECHER DE FAIRE CA DEUX FOIS
        System.out.println("MUSIC - onResumeGame");
    }

    

    // Joue une musique (interrompt l'ancienne si nécessaire)
    public void playMusic(String musicToPlay) {
        if(XtremVaders2021.kDisableMusic == true) return;
        
        if (currentMusic != null && !currentMusic.equals(musicToPlay)) {
            System.out.println("MUSIC - Stopping: " + currentMusic + "& Playing " + musicToPlay);
            stopMusic();
        }

        currentMusic = musicToPlay;

        System.out.println("MUSIC - Playing " + musicToPlay);
        AudioPlayer.play(musicToPlay); // Ex : "musics/theme1"
    }

    // Joue un effet sonore (ne coupe pas la musique)
    public void playSFX(String sfxName) {
        if(XtremVaders2021.kDisableMusic == true) return;
        //System.out.println("🔊 Playing " + sfxName);
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


    public AudioTrack playRandomTrackInRange(int minBPM, int maxBPM) {
        List<AudioTrack> candidates = getTracksForBPMRange(minBPM, maxBPM)
            .stream()
            .filter(track -> !track.equals(lastTrack))
            .collect(Collectors.toList());

        if (!candidates.isEmpty()) {
            AudioTrack selected = candidates.get(new Random().nextInt(candidates.size()));
            playMusic(selected.getPath());
            lastTrack = selected;
            return selected;
        }
        return null;
    }

    public void playSelectedTrack(TrackID trackID) {
        stopMusic();
        AudioTrack track = trackID.get();
        playMusic(track.getPath());
        lastTrack = track;
    }

    public List<AudioTrack> getTracksForBPMRange(int min, int max) {
    return Arrays.stream(TrackID.values())
        .map(TrackID::get)
        .filter(t -> t.bpm >= min && t.bpm <= max)
        .collect(Collectors.toList());
}

}
