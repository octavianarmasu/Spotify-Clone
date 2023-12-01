package Artist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SongsToAdd {
    @JsonProperty("name")
    private String name;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("album")
    private String album;
    @JsonProperty("tags")
    private ArrayList<String> tags;
    @JsonProperty("lyrics")
    private String lyrics;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("releaseYear")
    private int releaseYear;
    @JsonProperty("artist")
    private String artist;

    public final String getName() {
        return name;
    }
    public final void setName(final String name) {
        this.name = name;
    }
    public final int getDuration() {
        return duration;
    }
    public final void setDuration(final int duration) {
        this.duration = duration;
    }
    public final String getAlbum() {
        return album;
    }
    public final void setAlbum(final String album) {
        this.album = album;
    }
    public final ArrayList<String> getTags() {
        return tags;
    }
    public final void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }
    public final String getLyrics() {
        return lyrics;
    }
    public final void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }
    public final String getGenre() {
        return genre;
    }
    public final void setGenre(final String genre) {
        this.genre = genre;
    }
    public final int getReleaseYear() {
        return releaseYear;
    }
    public final void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }
    public final String getArtist() {
        return artist;
    }
    public final void setArtist(final String artist) {
        this.artist = artist;
    }



}
