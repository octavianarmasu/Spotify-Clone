package artist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ResultForAlbum {
    @JsonProperty("name")
    private String name;
    @JsonProperty("songs")
    private ArrayList<String> songs = new ArrayList<>();

    public ResultForAlbum() {

    }
    public final String getName() {
        return name;
    }

    public final ArrayList<String> getSongs() {
        return songs;
    }

    public final void setName(final String name) {
        this.name = name;
    }
    public final void setSongs(final ArrayList<String> songs) {
        this.songs = songs;
    }

    /**
     * Add a song to the result list.
     * @param song the song to be added
     */
    public final void addSong(final String song) {
        songs.add(song);
    }

}
