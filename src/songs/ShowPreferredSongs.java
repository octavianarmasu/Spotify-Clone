package songs;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ShowPreferredSongs {
    @JsonProperty("command")
    private String command = "showPreferredSongs";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<String> result = new ArrayList<>();

    public ShowPreferredSongs(final String user, final int timestamp,
                              final ArrayList<String> result) {
        this.user = user;
        this.timestamp = timestamp;
        this.result = result;
    }

    public ShowPreferredSongs() {
    }

    public final String getUser() {
        return user;
    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final ArrayList<String> getResult() {
        return result;
    }

    public final void setUser(final String user) {
        this.user = user;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final void setResult(final ArrayList<String> result) {
        this.result = result;
    }

    public void addResult(final String name) {
        this.result.add(name);
    }
}
