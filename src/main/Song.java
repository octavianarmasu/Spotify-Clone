package main;

import java.util.ArrayList;

import Artist.SongsToAdd;
import fileio.input.SongInput;

public class Song {
    private String name;
    private Integer duration;
    private String album;
    private ArrayList<String> tags;
    private String lyrics;
    private String genre;
    private Integer releaseYear;
    private String artist;
    private int nrOfLikes = 0;

    public Song(final SongInput song) {
        this.name = song.getName();
        this.duration = song.getDuration();
        this.album = song.getAlbum();
        this.tags = song.getTags();
        this.lyrics = song.getLyrics();
        this.genre = song.getGenre();
        this.releaseYear = song.getReleaseYear();
        this.artist = song.getArtist();
    }

    public Song(final SongsToAdd song) {
        this.name = song.getName();
        this.duration = song.getDuration();
        this.album = song.getAlbum();
        this.tags = song.getTags();
        this.lyrics = song.getLyrics();
        this.genre = song.getGenre();
        this.releaseYear = song.getReleaseYear();
        this.artist = song.getArtist();
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

    public final String getAlbum() {
        return album;
    }

    public final void setAlbum(final String album) {
        this.album = album;
    }

    public final ArrayList<String> getTags() {
        return tags;
    }

    public final void setTags(final ArrayList<String> tags) {
        this.tags = tags;
    }

    public final String getLyrics() {
        return lyrics;
    }

    public final void setLyrics(final String lyrics) {
        this.lyrics = lyrics;
    }

    public final String getGenre() {
        return genre;
    }

    public final void setGenre(final String genre) {
        this.genre = genre;
    }

    public final int getReleaseYear() {
        return releaseYear;
    }

    public final void setReleaseYear(final int releaseYear) {
        this.releaseYear = releaseYear;
    }

    public final String getArtist() {
        return artist;
    }

    public final void setArtist(final String artist) {
        this.artist = artist;
    }

    /**
     * Adds like to the song.
     */
    public final void addLike() {
        int nrOfLikesAux = this.nrOfLikes;
        this.nrOfLikes = nrOfLikesAux + 1;
    }

    /**
     * Removes like from the song.
     */
    public final void removeLike() {
        int nrOfLikesAux = this.nrOfLikes;
        this.nrOfLikes = nrOfLikesAux - 1;
    }

    /**
     * Gets the number of likes.
     */
    public final int getLikes() {
        return this.nrOfLikes;
    }

    /**
     * Verifies all filters.
     * @param command the command input
     * @param results the results
     * @param numFilters the number of filters
     */
    public final void verifyAll(final CommandInput command, final ArrayList<String> results,
                                final int numFilters) {
        String nameFilter = command.getFilters().getName();
        String albumFilter = command.getFilters().getAlbum();
        ArrayList<String> tagsFilter = command.getFilters().getTags();
        String lyricsFilter = command.getFilters().getLyrics();
        String genreFilter = command.getFilters().getGenre();
        String releaseYearFilter = command.getFilters().getReleaseYear();
        String artistFilter = command.getFilters().getArtist();
        int checkPassed = 0;
        if (nameFilter != null) {
            if (this.name.startsWith(nameFilter)) {
                checkPassed++;
            }
        }
        if (albumFilter != null) {
            if (this.album.equals(albumFilter)) {
                checkPassed++;
            }
        }
        if (tagsFilter != null) {
            int ok = 0;
            for (String tag : tagsFilter) {
                if (this.tags.contains(tag)) {
                    ok++;
                }
            }
            if (ok == tagsFilter.size()) {
                checkPassed++;
            }
        }
        if (lyricsFilter != null) {
            String checkedLyrics = this.lyrics.toLowerCase();
            lyricsFilter = lyricsFilter.toLowerCase();
            if (checkedLyrics.contains(lyricsFilter)) {
                checkPassed++;
            }
        }
        if (genreFilter != null) {
            genreFilter = genreFilter.toLowerCase();
            String compareGenre = this.genre.toLowerCase();
            if (compareGenre.equals(genreFilter)) {
                checkPassed++;
            }
        }
        if (releaseYearFilter != null) {
            if (releaseYearFilter.startsWith("<")) {
                int i = Integer.parseInt(releaseYearFilter.substring(1));
                if (this.releaseYear < i) {
                    checkPassed++;
                }
            } else {
                int i = Integer.parseInt(releaseYearFilter.substring(1));
                if (this.releaseYear > i) {
                    checkPassed++;
                }
            }
        }
        if (artistFilter != null) {
            if (this.artist.equals(artistFilter)) {
                checkPassed++;
            }
        }
        if (checkPassed == numFilters) {
            results.add(this.name);
        }

    }

}
