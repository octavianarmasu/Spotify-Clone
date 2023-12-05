package playlist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GetTopPlaylist {
    @JsonProperty("command")
    private final String command = "getTop5Playlists";
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<String> result = new ArrayList<>();

    public GetTopPlaylist() {

    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final ArrayList<String> getResult() {
        return result;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final void setResult(final ArrayList<String> results) {
        this.result = results;
    }

    /**
     * Add a result to the result list.
     * @param results the result to be added
     */
    public final void addResult(final String results) {
        this.result.add(results);
    }
}
