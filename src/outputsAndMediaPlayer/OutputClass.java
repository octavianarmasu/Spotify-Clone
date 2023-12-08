package outputsAndMediaPlayer;

import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.input.CommandInput;
import songs.Song;
import users.User;

import java.util.ArrayList;

public class OutputClass {
    @JsonProperty("command")
    private String command;
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;

    public OutputClass() {
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

    /**
     * Method that adds a song to the favorite list of a user or removes it
     * @param command the command that was given
     *                (ex: "like")
     * @param users the list of users in the database
     * @param songs the list of songs in the database
     * @param player the media player
     */
    public final void addLikedSongs (final CommandInput command, final ArrayList<User> users,
                                     final ArrayList<Song> songs, final MediaPlayer player) {
        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                for (Song song : user.getLikedSongs()) {
                    if (song.getName().equals(player.getSong())
                            && player.getArtist().equals(song.getArtist())) {
                        song.removeLike();
                        user.getLikedSongs().remove(song);
                        this.setMessage("Unlike registered successfully.");
                        found = 1;
                        break;
                    }
                }
            }
        }
        if (found == 0) {
            for (User user : users) {
                if (user.getUsername().equals(command.getUsername())) {
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())
                                && song.getArtist().equals(player.getArtist())) {
                            user.addLikedSong(song);
                            this.setMessage("Like registered successfully.");
                            song.addLike();
                            break;
                        }
                    }
                }
            }
        }
    }
}
