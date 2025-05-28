package xtremvaders.Audio;

public enum TrackID {
    SPACE_QUEST(new AudioTrack("space_quest", SoundType.MUSIC, 1, 175)),
    VESTA(new AudioTrack("Gemi_Kori_Vesta", SoundType.MUSIC, 1, 174)),
    CAREFUL(new AudioTrack("Thorjn-Careful", SoundType.MUSIC, 1, 174)),
    PORCELAIN(new AudioTrack("Ruckspin_Porcelain", SoundType.MUSIC, 1, 174)),
    LOST_LANDS(new AudioTrack("lostLands", SoundType.MUSIC, 1, 130));

    private final AudioTrack track;

    TrackID(AudioTrack track) {
        this.track = track;
    }

    public static TrackID fromAudioTrack(AudioTrack track) {
        for (TrackID tid : values()) {
            if (tid.get().equals(track)) {
                return tid;
            }
        }
        return null; // ou une valeur par défaut
    }


    public AudioTrack get() {
        return track;
    }
}
