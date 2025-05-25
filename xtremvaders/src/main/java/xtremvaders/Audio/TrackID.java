package xtremvaders.Audio;

public enum TrackID {
    // Genre DnB
    SPACE_QUEST(new AudioTrack("space_quest", SoundType.MUSIC, 1, 175)),
    TECIDORS(new AudioTrack("tecidors", SoundType.MUSIC, 1, 174)),

    // Techno
    LOST_LANDS(new AudioTrack("lostLands", SoundType.MUSIC, 1, 130));

    private final AudioTrack track;

    TrackID(AudioTrack track) {
        this.track = track;
    }

    public AudioTrack get() {
        return track;
    }
}
