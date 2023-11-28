package main;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class SearchFile {
    @JsonProperty("command")
    private String command = "search";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;
    @JsonProperty("results")
    private ArrayList<String> results;

    public SearchFile(final String user, final int timestamp,
                      final String message, final ArrayList<String> results) {
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
        this.results = results;
    }

    public SearchFile() {
    }

}
