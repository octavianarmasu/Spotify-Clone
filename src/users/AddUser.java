package users;

import com.fasterxml.jackson.annotation.JsonProperty;

public class AddUser {
    @JsonProperty("command")
    private String command = "addUser";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;

    public AddUser() {

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
