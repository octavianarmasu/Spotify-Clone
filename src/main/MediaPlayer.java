package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class MediaPlayer {
    String song;
    private int play;
    private int timeLeft;
    private Playlist playlist = new Playlist();
    private int songNumber;
    private Podcasts podcast;
    private String episode;
    private int episodeNumber;
    private int timeEpisode;
    private int repeat;
    private int shuffle;
    private Playlist oldPlaylist = new Playlist();
    private int timestamp;

    public MediaPlayer() {

    }

    public final int getPlay() {
        return this.play;
    }

    public final void setPlay(final int play) {
        this.play = play;
    }

    /**
     * Decreases the time of the song.
     * @param time the time to be decreased
     */
    public final void delTime(final int time) {
        this.timeLeft -= time;
    }

    public final int getTimeLeft() {
        return this.timeLeft;
    }

    public final void setTimeLeft(final int timeLeft) {
        this.timeLeft = timeLeft;
    }

    public final String getSong() {
        return this.song;
    }

    public final void setSong(final String song) {
        this.song = song;
    }

    public final Playlist getPlaylist() {
        return this.playlist;
    }

    public final void setPlaylist(final Playlist playlist) {
        this.playlist = playlist;
    }

    public final String getEpisode() {
        return this.episode;
    }

    public final void setEpisode(final String episode) {
        this.episode = episode;
    }

    public final int getTimeEpisode() {
        return this.timeEpisode;
    }

    public final void setTimeEpisode(final int timeEpisode) {
        this.timeEpisode = timeEpisode;
    }

    /**
     * Decreases the time of the episode.
     * @param time the time to be decreased
     */
    public final void delTimeEpisode(final int time) {
        this.timeEpisode -= time;
    }

    public final Podcasts getPodcast() {
        return this.podcast;
    }

    public final void setPodcast(final Podcasts podcast) {
        this.podcast = podcast;
    }

    public final int getEpisodeNumber() {
        return this.episodeNumber;
    }

    public final void setEpisodeNumber(final int episodeNumber) {
        this.episodeNumber = episodeNumber;
    }

    /**
     * Changes the episode number to the next one.
     */
    public final void nextEpisode() {
        this.episodeNumber++;
    }

    /**
     * Changes the song number to the next one.
     */
    public final void nextSong() {
        this.songNumber++;
    }

    /**
     * Changes the song number to the previous one.
     */
    public final void prevSong() {
        this.songNumber--;
    }

    public final int getSongNumber() {
        return this.songNumber;
    }

    public final void setSongNumber(final int songNumber) {
        this.songNumber = songNumber;
    }

    /**
     * Changes the repeat status.
     */
    public final void addDelRepeat() {
        if (this.repeat == 0) {
            this.repeat = 1;
            return;
        }
        if (this.repeat == 1) {
            this.repeat = 2;
            return;
        }
        if (this.repeat == 2) {
            this.repeat = 0;
        }
    }

    public final int getRepeat() {
        return this.repeat;
    }

    public final void setRepeat(final int repeat) {
        this.repeat = repeat;
    }

    /**
     * Changes the shuffle status.
     */
    public final void addDelShuffle() {
        if (this.song == null || this.playlist == null) {
            return;
        }
        if (this.shuffle == 0) {
            this.shuffle = 1;
            return;
        }
        if (this.shuffle == 1) {
            this.shuffle = 0;
        }
    }

    public final int getShuffle() {
        return this.shuffle;
    }

    public final void setShuffle(final int shuffle) {
        this.shuffle = shuffle;
    }

    public final Playlist getOldPlaylist() {
        return this.oldPlaylist;
    }

    public final void setOldPlaylist(final Playlist oldPlaylist) {
        this.oldPlaylist = oldPlaylist;
    }

    /**
     * Saves the un-shuffled playlist.
     * @param playlistChange the playlist to be saved
     */
    public final ArrayList<String> changeOldPlaylist(final Playlist playlistChange) {
        if (playlistChange != null && playlistChange.getSongs() != null) {
            List<String> songs = Collections.unmodifiableList(playlistChange.getSongs());
            return new ArrayList<String>(songs);
        }
        return null;
    }

    public final int getTimestamp() {
        return this.timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    /**
     * Changes the episode number to the previous one.
     */
    public final void prevEpisode() {
        this.episodeNumber--;
    }
}
