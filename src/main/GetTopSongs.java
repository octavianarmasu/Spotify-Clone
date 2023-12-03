package main;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GetTopSongs {
    @JsonProperty("command")
    private String command = "getTop5Songs";
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<String> result = new ArrayList<>();


    public GetTopSongs() {
    }


    public final void setCommand(final String command) {
        this.command = command;
    }
    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Add a result to the result list.
     * @param results the result to be added
     */
    public final void addResult(final String results) {
        this.result.add(results);
    }

    public final ArrayList<String> getResult() {
        return this.result;
    }

}
