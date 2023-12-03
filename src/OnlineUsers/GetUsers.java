package OnlineUsers;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class GetUsers {
    @JsonProperty ("command")
    private String command = "getOnlineUsers";
    @JsonProperty ("timestamp")
    private int timestamp;
    @JsonProperty ("result")
    private ArrayList<String> result = new ArrayList<>();

    public GetUsers() {
    }

    public final String getCommand() {
        return command;
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

    public final ArrayList<String> getResult() {
        return result;
    }

    public final void setResult(final ArrayList<String> result) {
        this.result = result;
    }

    /**
     * Add a result to the result list
     * @param result is the result to be added
     */
    public final void addResult(final String result) {
        this.result.add(result);
    }

}
