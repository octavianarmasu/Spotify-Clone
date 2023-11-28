package main;

import com.fasterxml.jackson.annotation.JsonProperty;


public class Repeat {
    @JsonProperty("command")
    private String command = "repeat";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;

    public Repeat() {
    }

    public Repeat(final String command, final String user,
                  final int timestamp, final String message) {
        this.command = command;
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
    }

    public final String getUser() {
        return this.user;
    }

    public final int getTimestamp() {
        return this.timestamp;
    }

    public final String getMessage() {
        return this.message;
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
     * Change the message of the repeat command.
     * @param repeat the repeat mode
     *               (0 - no repeat, 1 - repeat once, 2 - repeat infinite for songs)
     *               (0 - no repeat, 1 - repeat all, 2 - repeat current song for playlists)
     * @param loadpodcast if the podcast is loaded
     * @param loadsong if the song is loaded
     * @param loadplaylist if the playlist is loaded
     */
    public final void changeMessage(final int repeat, final int loadpodcast,
                                    final int loadsong, final int loadplaylist) {
        this.message = "Repeat mode changed to ";
        if (loadpodcast == 1 || loadsong == 1) {
            if (repeat == 0) {
                this.message += "no repeat.";

            }
            if (repeat == 1) {
                this.message += "repeat once.";
            }
            if (repeat == 2) {
                this.message += "repeat infinite.";
            }
        }
        if (loadplaylist == 1) {
            if (repeat == 0) {
                this.message += "no repeat.";

            }
            if (repeat == 1) {
                this.message += "repeat all.";
            }
            if (repeat == 2) {
                this.message += "repeat current song.";
            }
        }
    }
}

