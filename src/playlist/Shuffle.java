package playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import outputsAndMediaPlayer.MediaPlayer;

public class Shuffle {
    @JsonProperty("command")
    private String command = "shuffle";
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;

    public Shuffle(final String user, final int timestamp, final String message) {
        this.user = user;
        this.timestamp = timestamp;
        this.message = message;
    }

    public Shuffle() {

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

    public final String getMessage() {
        return message;
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
     * Change the message based on the input.
     * @param shuffle the shuffle value
     *                (0 if shuffle is deactivated, 1 if shuffle is activated)
     * @param loadCheck the loadCheck value
     * @param loadPlaylist the loadPlaylist value
     * @param mediaPlayer the mediaPlayer
     */
    public final void changeMessage(final int shuffle, final int loadCheck,
                                    final int loadPlaylist, final MediaPlayer mediaPlayer,
                                    final int loadAlbum) {

        if (loadCheck == 0) {
            this.message = "Please load a source before using the shuffle function.";
        } else {
            if (loadPlaylist == 0 && loadAlbum == 0) {
                this.message = "The loaded source is not a playlist or an album.";

            } else {
                if (mediaPlayer.getTimeLeft() == 0) {
                    this.message = "Please load a source before using the shuffle function.";
                    mediaPlayer.setShuffle(0);
                    return;
                }
                if (shuffle == 0) {
                    this.message = "Shuffle function deactivated successfully.";
                } else {
                    this.message = "Shuffle function activated successfully.";

                }
            }
        }
    }
}
