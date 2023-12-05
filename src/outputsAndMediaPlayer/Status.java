package outputsAndMediaPlayer;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Status {
    @JsonProperty("command")
    private String command = "status";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("stats")
    private Stats stats;

    public Status() {

    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final void setUser(final String user) {
        this.user = user;
    }

    public final void setStats(final Stats stats) {
        this.stats = stats;
    }
}
