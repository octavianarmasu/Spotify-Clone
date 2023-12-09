package outputsandmediaplayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.input.CommandInput;

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

    /**
     * verify if the command has multiple filters
     *
     * @param command the command to be verified
     * @return the number of filters
     */
    public static int verifyMultipleForSongs(final CommandInput command) {
        int numFilters = 0;
        if (command.getFilters().getName() != null) {
            numFilters++;
        }
        if (command.getFilters().getArtist() != null) {
            numFilters++;
        }
        if (command.getFilters().getAlbum() != null) {
            numFilters++;
        }
        if (command.getFilters().getGenre() != null) {
            numFilters++;
        }
        if (command.getFilters().getLyrics() != null) {
            numFilters++;
        }
        if (command.getFilters().getReleaseYear() != null) {
            numFilters++;
        }
        if (command.getFilters().getTags() != null) {
            numFilters++;
        }
        return numFilters;
    }

    /**
     * verify if the command has multiple filters for albums
     *
     * @param command the command to be verified
     * @return the number of filters
     */
    public static int verifyFiltersForAlbum(final CommandInput command) {
        int numFilters = 0;
        if (command.getFilters().getOwner() != null) {
            numFilters++;
        }
        if (command.getFilters().getName() != null) {
            numFilters++;
        }
        if (command.getFilters().getDescription() != null) {
            numFilters++;
        }
        return numFilters;
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
