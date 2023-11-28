package Podcast;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EpisodesToAdd {
    @JsonProperty("name")
    private String name;
    @JsonProperty("duration")
    private int duration;
    @JsonProperty("description")
    private String description;

}
