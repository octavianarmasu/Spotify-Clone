package host;

import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;

public class ResultForPodcast {
    @JsonProperty("name")
    private String name;
    @JsonProperty("episodes")
    private ArrayList<String> episodes = new ArrayList<>();

    public ResultForPodcast() {

    }

    public final String getName() {
        return name;
    }

    public final ArrayList<String> getEpisodes() {
        return episodes;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setEpisodes(final ArrayList<String> episodes) {
        this.episodes = episodes;
    }

    /**
     * Adds an episode to the list of episodes.
     * @param episode the episode to be added
     */
    public final void addEpisode(final String episode) {
        episodes.add(episode);
    }


}
