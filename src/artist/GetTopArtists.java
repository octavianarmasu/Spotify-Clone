package artist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GetTopArtists {
    @JsonProperty("command")
    private String command = "getTop5Artists";
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<String> result = new ArrayList<>();

    public GetTopArtists() {

    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final ArrayList<String> getResult() {
        return result;
    }

    public final void setResult(final ArrayList<String> result) {
        this.result = result;
    }

    public final void addResult(final String resultToAdd) {
        this.result.add(resultToAdd);
    }

}
