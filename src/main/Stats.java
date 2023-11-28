package main;

import com.fasterxml.jackson.annotation.JsonProperty;

public class Stats {
    @JsonProperty("name")
    private String name;
    @JsonProperty("remainedTime")
    private int remainedTime;
    @JsonProperty("repeat")
    private String repeat = "No Repeat";
    @JsonProperty("shuffle")
    private boolean shuffle = false;
    @JsonProperty("paused")
    private boolean paused;

    public Stats() {
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setRemainedTime(final int remainedTime) {
        this.remainedTime = remainedTime;
    }

    public final void setRepeat(final String repeat) {
        this.repeat = repeat;
    }

    public final void setShuffle(final boolean shuffle) {
        this.shuffle = shuffle;
    }

    public final void setPaused(final boolean paused) {
        this.paused = paused;
    }

    /**
     * changes the repeat value, when repeat command is called
     * @param repeatCheck the value of repeat
     * @param loadsong the value of loadsong
     * @param loadpodcast the value of loadpodcast
     * @param loadplaylist the value of loadplaylist
     */
    public final void changeRepeat(final int repeatCheck, final int loadsong,
                                   final int loadpodcast, final int loadplaylist) {
        if (repeatCheck == 0) {
            this.repeat = "No Repeat";
        } else {
            if (loadsong == 1 || loadpodcast == 1) {
                if (repeatCheck == 1) {
                    this.repeat = "Repeat Once";
                }
                if (repeatCheck == 2) {
                    this.repeat = "Repeat Infinite";
                }
            }
            if (loadplaylist == 1) {
                if (repeatCheck == 1) {
                    this.repeat = "Repeat All";
                }
                if (repeatCheck == 2) {
                    this.repeat = "Repeat Current Song";
                }
            }
        }
    }

    /**
     * changes the shuffle value, when shuffle command is called
     * @param shuffleCheck the value of shuffle
     * @param loadsong the value of loadsong
     * @param loadpodcast the value of loadpodcast
     * @param loadplaylist the value of loadplaylist
     */

    public final void changeShuffle(final int shuffleCheck, final int loadsong,
                                    final int loadpodcast, final int loadplaylist) {
        if (shuffleCheck == 0) {
            this.shuffle = false;
        } else {
            if (loadsong == 1 || loadpodcast == 1) {
                this.shuffle = false;
            }
            this.shuffle = loadplaylist == 1;
        }
    }
}



