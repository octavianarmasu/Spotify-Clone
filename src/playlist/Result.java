package playlist;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class Result {
    @JsonProperty("name")
    private String name;
    @JsonProperty("songs")
    private ArrayList<String> songs;
    @JsonProperty("visibility")
    private String visibility = "public";
    @JsonProperty("followers")
    private int followers = 0;

    public Result(final String name, final ArrayList<String> songs,
                  final String visibility, final int followers) {
        this.name = name;
        this.songs = songs;
        this.visibility = visibility;
        this.followers = followers;
    }

    public Result() {
    }

    public final String getName() {
        return name;
    }

    public final ArrayList<String> getSongs() {
        return songs;
    }

    public final String getVisibility() {
        return visibility;
    }

    public final int getFollowers() {
        return followers;
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

    /**
     * Add a follower.
     */
    public final void addFollowers() {
        this.followers++;
    }


}
