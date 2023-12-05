package host;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ShowPodcasts {
    @JsonProperty("command")
    private String command = "showPodcasts";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<ResultForPodcast> result = new ArrayList<>();

    public ShowPodcasts() {

    }
    public final void setUser(final String user) {
        this.user = user;
    }
    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
    public final void setResult(final ArrayList<ResultForPodcast> result) {
        this.result = result;
    }
    public final String getCommand() {
        return command;
    }
    public final String getUser() {
        return user;
    }
    public final int getTimestamp() {
        return timestamp;
    }
    public final ArrayList<ResultForPodcast> getResult() {
        return result;
    }

    /**
     * Add a result to the result list.
     * @param result the result to be added
     */
    public final void addResult(final ResultForPodcast result) {
        this.result.add(result);
    }

}
