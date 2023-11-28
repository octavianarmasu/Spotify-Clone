package main;

import com.fasterxml.jackson.annotation.JsonProperty;


public class SelectOutput {
    @JsonProperty("command")
    private String command = "select";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;
    int successfulSelect;

    public SelectOutput(final String user, final int timestamp, final String message) {
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
    }

    public SelectOutput() {

    }

    public final void setUser(final String user) {
        this.user = user;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Set the successfulSelect value.
     * @param value true if the select was successful, false otherwise
     */
    public final void setSuccessfulSelect(final boolean value) {
        if (value) {
            this.successfulSelect = 1;
        } else {
            this.successfulSelect = 0;
        }
    }

}
