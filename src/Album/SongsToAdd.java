package Album;

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

}
