package playlist;


import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ShowPlaylists {
    @JsonProperty("command")
    private String command = "showPlaylists";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<Result> result = new ArrayList<Result>();

    public final void setUser(final String user) {
        this.user = user;
    }

    public ShowPlaylists(final String user, final int timestamp,
                         final ArrayList<Result> result) {
        this.user = user;
        this.timestamp = timestamp;
        this.result = result;
    }

    public ShowPlaylists() {
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final void setResult(final ArrayList<Result> result) {
        this.result = result;
    }

    public final String getUser() {
        return this.user;
    }

    public final int getTimestamp() {
        return this.timestamp;
    }

    public final ArrayList<Result> getResult() {
        return this.result;
    }

    /**
     * Add a result to the result list.
     * @param results the result to be added
     */
    public final void addResult(final Result results) {
        this.result.add(results);
    }
}
