package Album;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ShowAlbums {
    @JsonProperty("command")
    private String command = "showAlbums";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<ResultForAlbum> result = new ArrayList<>();

    public ShowAlbums() {

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

    public final ArrayList<ResultForAlbum> getResult() {
        return result;
    }

    public final void setCommand(final String command) {
        this.command = command;
    }

    public final void setUser(final String user) {
        this.user = user;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final void setResult(final ArrayList<ResultForAlbum> result) {
        this.result = result;
    }

    /**
     * Add a result to the result list.
     * @param result the result to be added
     */

    public final void addResult(final ResultForAlbum result) {
        this.result.add(result);

    }
}
