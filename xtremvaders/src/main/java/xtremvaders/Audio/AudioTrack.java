package xtremvaders.Audio;

public enum AudioTrack {
    LOST_LANDS("lostLands"),
    MYSTERIOUS("tracklist/Mysterious v6");

    private final String path;

    AudioTrack(String path) {
        this.path = path;
    }

    public String getPath() {
        return path;
    }
}
