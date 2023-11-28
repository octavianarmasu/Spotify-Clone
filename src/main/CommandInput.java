package main;

import Album.SongsToAdd;
import Podcast.EpisodesToAdd;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class CommandInput {
    private String command;
    @JsonProperty("username")
    private String username;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("type")
    private String type;
    @JsonProperty("itemNumber")
    private int itemNumber;
    @JsonProperty("filters")
    private Filters filters;
    @JsonProperty("playlistId")
    private int playlistId;
    @JsonProperty("playlistName")
    private String playlistName;
    @JsonProperty("seed")
    private int seed;
    @JsonProperty("age")
    private int age;
    @JsonProperty("city")
    private String city;
    @JsonProperty("name")
    private String name;
    @JsonProperty("releaseYear")
    private int releaseYear;
    @JsonProperty("description")
    private String description;
    @JsonProperty("songs")
    private SongsToAdd[] songs;
    @JsonProperty("episodes")
    private EpisodesToAdd[] episodes;
    @JsonProperty("nextPage")
    private String nextPage;
    @JsonProperty("date")
    private String date;
    @JsonProperty("price")
    private int price;
    public CommandInput() {
    }

    public final String getCommand() {
        return command;
    }

    public final String getUsername() {
        return username;
    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final String getType() {
        return type;
    }

    public final int getItemNumber() {
        return itemNumber;
    }

    public final Filters getFilters() {
        return filters;
    }

    public final int getPlaylistId() {
        return playlistId;
    }

    public final String getPlaylistName() {
        return playlistName;
    }

    public final int getSeed() {
        return seed;
    }
}
