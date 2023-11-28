package Album;

import main.Song;

import java.util.ArrayList;

public class Album {
    private String name;
    private int releaseYear;
    private String description;
    private final ArrayList<Song> songs = new ArrayList<>();

    public Album() {
    }

    public Album(final String name, final int releaseYear, final String description,
                 final ArrayList<Song> songs) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs.addAll(songs);
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final int getReleaseYear() {
        return releaseYear;
    }

    public final void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

    public final ArrayList<Song> getSongs() {
        return songs;
    }

    /**
     * Adds a song to the album's list of songs.
     * @param song the song to be added
     */
    public final void addSong(final Song song) {
        this.songs.add(song);
    }

    public final void setSongs(final ArrayList<Song> songs) {
        this.songs.addAll(songs);
    }
}
