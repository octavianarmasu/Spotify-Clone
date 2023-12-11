package outputsandmediaplayer;

import artist.Album;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import podcast.Podcasts;
import playlist.Playlist;
import songs.Song;

public class MediaPlayer {
    static final int PODCASTNUM = 90;
    private final Playlist oldPlaylist = new Playlist();
    private final Album oldAlbum = new Album();
    String song;
    private int play;
    private int timeLeft;
    private Playlist playlist = new Playlist();
    private int songNumber;
    private Podcasts podcast;
    private String episode;
    private int episodeNumber;
    private int timeEpisode;
    private int repeat = 0;
    private int shuffle;
    private int timestamp;
    private Album album = new Album();
    private String artist;

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
     *
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
     *
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
        if (this.song == null) {
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

    /**
     * Saves the un-shuffled playlist.
     *
     * @param playlistChange the playlist to be saved
     */
    public final ArrayList<String> changeOldPlaylist(final Playlist playlistChange) {
        if (playlistChange != null && playlistChange.getSongs() != null) {
            List<String> songs = Collections.unmodifiableList(playlistChange.getSongs());
            return new ArrayList<>(songs);
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

    public final Album getAlbum() {
        return this.album;
    }

    public final void setAlbum(final Album album) {
        this.album = album;
    }

    /**
     * Saves the songs of the album before shuffling.
     *
     * @return the songs of the album
     */
    public final ArrayList<Song> changeOldAlbum(final Album albumChange) {
        if (albumChange != null && albumChange.getSongs() != null) {
            List<Song> songs = Collections.unmodifiableList(albumChange.getSongs());
            return new ArrayList<>(songs);
        }
        return null;
    }

    public final Album getOldAlbum() {
        return this.oldAlbum;
    }

    public final String getArtist() {
        return this.artist;
    }

    public final void setArtist(final String artist) {
        this.artist = artist;
    }

    /**
     * skips backward in a podcast(-90sec)
     *
     * @param backward output class
     */
    public final void skipBackward(final OutputClass backward) {
        int remainedTime = this.getTimeEpisode();
        int aux1 = this.getEpisodeNumber();
        int duration = this.getPodcast().getEpisodes().get(aux1).getDuration();
        int time = duration - remainedTime;
        if (time < PODCASTNUM) {
            backward.setMessage("Rewound successfully.");
            aux1 = this.getEpisodeNumber();
            this.setEpisode(this.getPodcast().getEpisodes().get(aux1).getName());
            this.setTimeEpisode(this.getPodcast().getEpisodes().get(aux1).getDuration());

        } else {
            backward.setMessage("Rewound successfully.");
            this.setTimeEpisode(remainedTime + PODCASTNUM);
        }
    }

    /**
     * skips forward in a podcast (+90sec)
     *
     * @param forward output class
     */

    public final void skipForward(final OutputClass forward) {
        int remainedTime = this.getTimeEpisode();
        if (remainedTime < PODCASTNUM) {
            this.nextEpisode();
            forward.setMessage("Skipped forward successfully.");
            if (this.getPodcast().getEpisodes().get(this.getEpisodeNumber()) != null) {
                int aux1 = this.getEpisodeNumber();
                String aux2 = this.getPodcast().getEpisodes().get(aux1).getName();
                this.setEpisode(aux2);
                this.setTimeEpisode(this.getPodcast().getEpisodes().get(aux1).getDuration());
            } else {
                if (this.getRepeat() == 0) {
                    this.setEpisode("");
                    this.setTimeEpisode(0);
                    this.setPlay(0);
                    this.setPodcast(null);
                }
                if (this.getRepeat() == 1) {
                    this.setRepeat(0);
                    this.setEpisodeNumber(0);
                    int aux1 = this.getEpisodeNumber();
                    String aux2 = this.getPodcast().getEpisodes().get(aux1).getName();
                    this.setEpisode(aux2);
                    int duration = this.getPodcast().getEpisodes().get(aux1).getDuration();
                    this.setTimeEpisode(duration);
                }
                if (this.getRepeat() == 2) {
                    this.setEpisodeNumber(0);
                    int aux1 = this.getEpisodeNumber();
                    String aux2 = this.getPodcast().getEpisodes().get(aux1).getName();
                    this.setEpisode(aux2);
                    int duration = this.getPodcast().getEpisodes().get(aux1).getDuration();
                    this.setTimeEpisode(duration);
                }
            }

        } else {
            forward.setMessage("Skipped forward successfully.");
            this.delTimeEpisode(PODCASTNUM);
        }

    }

    /**
     * skips to previous for song/album/playlist/podcast
     *
     * @param prev         output class to set the message
     * @param songs        the list of songs in the database
     * @param loadSong     the flag for song
     * @param loadPodcast  the flag for podcast
     * @param loadPlaylist the flag for playlist
     * @param podcasts     the list of podcasts in the database
     * @param loadAlbum    the flag for album
     */
    public final void skipToPrev(final OutputClass prev, final ArrayList<Song> songs,
                                 final int loadSong, final int loadPodcast,
                                 final int loadPlaylist, final ArrayList<Podcasts> podcasts,
                                 final int loadAlbum) {
        if (loadSong == 1) {
            int duration;
            for (Song songAux : songs) {
                if (songAux.getName().equals(this.getSong())) {
                    duration = songAux.getDuration();
                    if (this.getTimeLeft() < duration) {
                        this.setTimeLeft(duration);
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + this.getSong() + ".");
                    }
                }
            }
        }
        if (loadPlaylist == 1) {
            int duration;
            this.prevSong();
            for (Song songAux : songs) {
                if (songAux.getName().equals(this.getSong())) {
                    duration = songAux.getDuration();
                    if (this.getTimeLeft() < duration) {
                        this.setTimeLeft(duration);
                        this.nextSong();
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + this.getSong() + ".");
                    } else {
                        if (this.getSongNumber() < 0) {
                            this.setSongNumber(0);
                        }
                        int num = this.getSongNumber();
                        String songHelp = this.getPlaylist().getSongs().get(num);
                        this.setSong(songHelp);
                        for (Song song1 : songs) {
                            if (song1.getName().equals(this.getSong())) {
                                this.setTimeLeft(song1.getDuration());
                                this.setArtist(song1.getArtist());
                            }
                        }
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + this.getSong() + ".");
                    }
                }
            }
        }

        if (loadPodcast == 1) {
            int duration;
            for (Podcasts podcastAux : podcasts) {
                if (this.getPodcast().getName().equals(podcastAux.getName())) {
                    int num = this.getEpisodeNumber();
                    duration = this.getPodcast().getEpisodes().get(num).getDuration();
                    if (this.getTimeEpisode() < duration) {
                        this.setTimeEpisode(duration);
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + this.getEpisode() + ".");
                    } else {
                        if (duration == this.getTimeEpisode() && this.getEpisodeNumber() != 0) {
                            this.prevEpisode();
                            num = this.getEpisodeNumber();
                            String episodeAux = this.getPodcast().getEpisodes().get(num).getName();
                            this.setEpisode(episodeAux);
                            int aux = this.getPodcast().getEpisodes().get(num).getDuration();
                            this.setTimeEpisode(aux);
                            prev.setMessage("Returned to previous track successfully. "
                                    + "The current track is " + this.getEpisode() + ".");
                        } else {
                            if (duration == this.getTimeEpisode()) {
                                prev.setMessage("Returned to previous track successfully. "
                                        + "The current track is " + this.getEpisode() + ".");
                            }
                        }
                    }
                }
            }
        }
        if (loadAlbum == 1) {
            if (this.getTimeLeft()
                    < this.getAlbum().getSongs().get(this.getSongNumber()).getDuration()) {
                int num = this.getSongNumber();
                this.setTimeLeft(this.getAlbum().getSongs().get(num).getDuration());
                prev.setMessage("Returned to previous track successfully. "
                        + "The current track is " + this.getSong() + ".");
            } else {
                if (this.getSongNumber() != 0) {
                    this.prevSong();
                    int num = this.getSongNumber();
                    String songAux = this.getAlbum().getSongs().get(num).getName();
                    this.setSong(songAux);
                    this.setTimeLeft(this.getAlbum().getSongs().get(num).getDuration());
                    prev.setMessage("Returned to previous track successfully. "
                            + "The current track is " + this.getSong() + ".");
                } else {
                    prev.setMessage("Returned to previous track successfully. "
                            + "The current track is " + this.getSong() + ".");
                }
            }
        }
    }

    /**
     * skips to next for song/album/playlist/podcast
     *
     * @param next         output class to set the message
     * @param songs        the list of songs in the database
     * @param loadSong     the flag for song
     * @param loadPodcast  the flag for podcast
     * @param loadPlaylist the flag for playlist
     * @param loadAlbum    the flag for album
     */
    public final void skipToNext(final OutputClass next, final ArrayList<Song> songs,
                                 final int loadSong, final int loadPodcast,
                                 final int loadPlaylist, final int loadAlbum) {
        if (loadSong == 1) {
            if (this.getRepeat() == 0) {
                next.setMessage("Please load a source before skipping to the next track.");
                this.setPlay(0);
                this.setTimeLeft(0);
                this.setSong("");
            } else {
                if (this.getRepeat() == 1) {
                    this.setRepeat(0);
                }
                for (Song songAux : songs) {
                    if (songAux.getName().equals(this.getSong())) {
                        this.setTimeLeft(songAux.getDuration());
                    }
                }
                next.setMessage("Skipped to next track successfully. "
                        + "The current track is " + this.getSong() + ".");
            }
        }
        if (loadPlaylist == 1) {
            if (this.getRepeat() != 2) {
                this.nextSong();
                if (this.getPlaylist().getSongs().size() - 1 >= this.getSongNumber()) {
                    this.setSong(this.getPlaylist().getSongs().get(this.getSongNumber()));
                    for (Song songAux : songs) {
                        if (songAux.getName().equals(this.getSong())) {
                            this.setTimeLeft(songAux.getDuration());
                            this.setArtist(songAux.getArtist());
                        }
                    }
                    next.setMessage("Skipped to next track successfully. "
                            + "The current track is " + this.getSong() + ".");
                } else {
                    if (this.getRepeat() == 0) {
                        next.setMessage("Please load a source before skipping to the next track.");
                        this.setSong("");
                        this.setTimeLeft(0);
                        this.setPlay(0);

                    }
                    if (this.getRepeat() == 1) {
                        this.setSongNumber(0);
                        this.setSong(this.getPlaylist().getSongs().get(this.getSongNumber()));
                        for (Song songAux : songs) {
                            if (songAux.getName().equals(this.getSong())) {
                                this.setTimeLeft(songAux.getDuration());
                                this.setArtist(songAux.getArtist());
                            }
                        }
                        next.setMessage("Skipped to next track successfully."
                                + " The current track is " + this.getSong() + ".");
                    }
                }
            } else {
                for (Song songAux : songs) {
                    if (songAux.getName().equals(this.getSong())) {
                        this.setTimeLeft(songAux.getDuration());
                        this.setArtist(songAux.getArtist());
                    }
                }
                next.setMessage("Skipped to next track successfully. "
                        + "The current track is " + this.getSong() + ".");
            }
        }

        if (loadPodcast == 1) {
            this.nextEpisode();
            if (this.getPodcast().getEpisodes().get(this.getEpisodeNumber()) != null) {
                int aux1 = this.getEpisodeNumber();
                String aux2 = this.getPodcast().getEpisodes().get(aux1).getName();
                this.setEpisode(aux2);
                int num = this.getEpisodeNumber();
                this.setTimeEpisode(this.getPodcast().getEpisodes().get(num).getDuration());
                next.setMessage("Skipped to next track successfully."
                        + " The current track is " + this.getEpisode() + ".");
            } else {
                if (this.getRepeat() == 0) {
                    next.setMessage("Please load a source before skipping to the next track.");
                    this.setEpisode("");
                    this.setTimeEpisode(0);
                    this.setPlay(0);
                    this.setPodcast(null);
                }
                if (this.getRepeat() == 1) {
                    next.setMessage("Skipped to next track successfully."
                            + " The current track is " + this.getEpisode() + ".");
                    this.setRepeat(0);
                    this.setEpisodeNumber(0);
                    int aux1 = this.getEpisodeNumber();
                    String aux2 = this.getPodcast().getEpisodes().get(aux1).getName();
                    this.setEpisode(aux2);
                    int num = this.getEpisodeNumber();
                    this.setTimeEpisode(this.getPodcast().getEpisodes().get(num).getDuration());
                }
                if (this.getRepeat() == 2) {
                    next.setMessage("Skipped to next track successfully. "
                            + "The current track is " + this.getEpisode() + ".");
                    this.setEpisodeNumber(0);
                    int aux1 = this.getEpisodeNumber();
                    String aux2 = this.getPodcast().getEpisodes().get(aux1).getName();
                    this.setEpisode(aux2);
                    int duration = this.getPodcast().getEpisodes().get(aux1).getDuration();
                    this.setTimeEpisode(duration);
                }
            }
        }
        if (loadAlbum == 1) {
            if (this.getSongNumber() == this.getAlbum().getSongs().size() - 1) {
                next.setMessage("Please load a source before skipping to the next track.");
            } else {
                this.nextSong();
                if (this.getAlbum().getSongs().size() - 1 >= this.getSongNumber()) {
                    this.setSong(this.getAlbum().getSongs().get(this.getSongNumber()).getName());
                    int num = this.getSongNumber();
                    int duration = this.getAlbum().getSongs().get(num).getDuration();
                    this.setTimeLeft(duration);
                }
                next.setMessage("Skipped to next track successfully. "
                        + "The current track is " + this.getSong() + ".");
            }
        }
    }

}
