package xtremvaders.Audio.Tracks;

import xtremvaders.Audio.Player.SoundType;

public class AudioTrack {
    public final String name;
    public final SoundType type;
    public final int priority;
    public final int bpm;

    public AudioTrack(String name, SoundType type, int priority, int bpm) {
        this.name = name;
        this.type = type;
        this.priority = priority;
        this.bpm = bpm;
    }

    public String getPath() {
        return name;
    }
}
