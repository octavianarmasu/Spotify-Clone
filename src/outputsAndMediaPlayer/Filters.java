package outputsAndMediaPlayer;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Filters {
    @JsonProperty("name")
    private String name;
    @JsonProperty("album")
    private String album;
    @JsonProperty("tags")
    private ArrayList<String> tags;
    @JsonProperty("lyrics")
    private String lyrics;
    @JsonProperty("genre")
    private String genre;
    @JsonProperty("releaseYear")
    private String releaseYear;
    @JsonProperty("artist")
    private String artist;
    @JsonProperty("owner")
    private String owner;
    @JsonProperty("description")
    private String description;

    public Filters() {
    }

    public final String getName() {
        return name;
    }

    public final String getAlbum() {
        return album;
    }

    public final ArrayList<String> getTags() {
        return tags;
    }

    public final String getLyrics() {
        return lyrics;
    }

    public final String getGenre() {
        return genre;
    }

    public final String getReleaseYear() {
        return releaseYear;
    }

    public final String getArtist() {
        return artist;
    }

    public final String getOwner() {
        return owner;
    }

    public final String getDescription() {
        return description;
    }


}
