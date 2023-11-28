package main;

import fileio.input.EpisodeInput;

public class Episode {
    private String name;
    private Integer duration;
    private String description;

    public Episode(final EpisodeInput episodeInput) {
        this.name = episodeInput.getName();
        this.duration = episodeInput.getDuration();
        this.description = episodeInput.getDescription();
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

