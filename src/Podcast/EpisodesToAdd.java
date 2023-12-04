package Podcast;

import com.fasterxml.jackson.annotation.JsonProperty;

public class EpisodesToAdd {
    @JsonProperty("name")
    private String name;
    @JsonProperty("duration")
    private Integer duration;
    @JsonProperty("description")
    private String description;

    public EpisodesToAdd(final String name, final Integer duration, final String description) {
        this.name = name;
        this.duration = duration;
        this.description = description;
    }
    public EpisodesToAdd() {
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final Integer getDuration() {
        return duration;
    }

    public final void setDuration(final Integer duration) {
        this.duration = duration;
    }

    public final String getDescription() {
        return description;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }

}
