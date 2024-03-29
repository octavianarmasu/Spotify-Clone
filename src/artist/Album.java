package artist;

import songs.Song;

import java.util.ArrayList;

public class Album {
    private final ArrayList<Song> songs = new ArrayList<>();
    private String name;
    private int releaseYear;
    private String description;
    private int totalLikes = 0;

    private String artist;

    public Album() {
    }

    public Album(final String name, final int releaseYear, final String description,
                 final ArrayList<Song> songs, final String artist) {
        this.name = name;
        this.releaseYear = releaseYear;
        this.description = description;
        this.songs.addAll(songs);
        this.artist = artist;
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
     * Adds a list of songs to the album's list of songs.
     *
     * @param songs the list of songs to be added
     */
    public final void setSongs(final ArrayList<Song> songs) {
        this.songs.addAll(songs);
    }

    public final int getTotalLikes() {
        return totalLikes;
    }

    public final void setTotalLikes(final int totalLikes) {
        this.totalLikes = totalLikes;
    }

    public final String getArtist() {
        return artist;
    }

    public final void setArtist(final String artist) {
        this.artist = artist;
    }

}
