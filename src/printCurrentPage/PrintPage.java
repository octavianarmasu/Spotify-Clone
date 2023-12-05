package printCurrentPage;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PrintPage {
    @JsonProperty("user")
    private String user;
    @JsonProperty("command")
    private String command;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;

    public PrintPage() {
    }
    public final String getCommand() {
        return command;
    }
    public final void setCommand(final String command) {
        this.command = command;
    }
    public final String getUser() {
        return user;
    }
    public final void setUser(final String user) {
        this.user = user;
    }
    public final int getTimestamp() {
        return timestamp;
    }
    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }
    public final String getMessage() {
        return message;
    }
    public final void setMessage(final String message) {
        this.message = message;
    }
}
