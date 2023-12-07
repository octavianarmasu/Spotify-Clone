package playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.input.CommandInput;
import users.User;

import java.util.ArrayList;

public class Playlist {
    @JsonProperty("name")
    private String name;
    @JsonProperty("songs")
    private ArrayList<String> songs = new ArrayList<>();
    @JsonProperty("visibility")
    private String visibility = "public";
    @JsonProperty("followers")
    private int followers = 0;

    private int timestamp;
    private String owner;

    public Playlist() {
    }
    public Playlist(final String name, final ArrayList<String> songs, final String visibility,
                    final int followers) {
        this.name = name;
        this.songs.addAll(songs);
        this.visibility = visibility;
        this.followers = followers;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setSongs(final ArrayList<String> songs) {
        this.songs = songs;
    }

    public final void setVisibility(final String visibility) {
        this.visibility = visibility;
    }

    public final void setFollowers(final int followers) {
        this.followers = followers;
    }

    public final String getName() {
        return this.name;
    }

    public final ArrayList<String> getSongs() {
        return this.songs;
    }

    public final String getVisibility() {
        return this.visibility;
    }

    public final int getFollowers() {
        return this.followers;
    }

    /**
     * Add a follower to the playlist.
     */
    public final void plusFollower() {
        this.followers++;
    }

    /**
     * Remove a follower from the playlist.
     */
    public final void removeFollower() {
        int followersAux = this.followers;
        this.followers = followersAux - 1;
    }

    /**
     * Add a song to the playlist.
     */
    public final void addSong(final String song1) {
        this.songs.add(song1);
    }

    /**
     * Remove a song from the playlist.
     * @param song1 the song to be removed
     */
    public final void removeSong(final String song1) {
        this.songs.remove(song1);
    }

    /**
     * Verify if the playlist has the given song.
     * @param results the list of results
     * @param user the list of users
     * @param userWhoIsLoggedIn the user who is logged in
     *           (if the playlist is private, the user must be the owner)
     *
     */
    public final void verifyName(final CommandInput command, final ArrayList<String> results,
                                 final User user, final String userWhoIsLoggedIn) {
        String nameFilter = command.getFilters().getName();
        if (nameFilter != null) {
            if (!user.getUsername().equals(userWhoIsLoggedIn)) {
                if (this.visibility.equals("private")) {
                    return;
                }
            }
            if (this.name.startsWith(nameFilter)) {
                results.add(this.name);
            }
        }
    }

    /**
     * Switches the visibility of the playlist.
     */
    public final void switchVisibility() {
        if (this.visibility.equals("public")) {
            this.visibility = "private";
        } else {
            this.visibility = "public";
        }
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final int getTimestamp() {
        return this.timestamp;
    }

    public final void setOwner(final String owner) {
        this.owner = owner;
    }

    public final String getOwner() {
        return this.owner;
    }
}
