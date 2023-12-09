package outputsandmediaplayer;

import announcements.Announcements;
import artist.Album;
import artist.Artist;
import events.Event;
import fileio.input.CommandInput;
import host.Host;
import podcast.Episode;
import podcast.Podcasts;
import songs.Song;
import users.User;

import java.text.ParseException;
import java.util.ArrayList;

public final class Check {
    static final int MAGICNUM = 5;
    static final int PARSEMONTHSTART = 3;
    static final int PARSEYEAR = 6;
    static final int NUMMOTHS = 12;
    static final int FEBRUARY = 28;
    static final int NUMDAYS = 31;
    static final int MAXYEAR = 2023;
    static final int MINYEAR = 1900;

    private Check() {
    }

    /**
     * verify if a host has already the podcast in his list of podcasts
     *
     * @param host    the host to be verified
     * @param command the command to be verified
     * @return 0 if the podcast is already in the list, 1 otherwise
     */
    public static int checkDuplicatePodcast(final Host host, final CommandInput command) {
        if (host.getPodcasts() == null) {
            return 1;
        }
        for (Podcasts podcast : host.getPodcasts()) {
            if (podcast.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * verify if an episode was added twice in the list of episodes
     *
     * @param episodesToAdd the list of episodes to be verified
     * @return 0 if the episode is already in the list, 1 otherwise
     */
    public static int checkDuplicateEpisode(final ArrayList<Episode> episodesToAdd) {
        for (int i = 0; i < episodesToAdd.size() - 1; i++) {
            for (int j = i + 1; j < episodesToAdd.size(); j++) {
                if (episodesToAdd.get(i).getName().equals(episodesToAdd.get(j).getName())) {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * verify if an announcement was added twice in the list of announcements of a host
     *
     * @param host    the host to be verified
     * @param command the command to be verified
     * @return 0 if the announcement is already in the list, 1 otherwise
     */
    public static int checkDuplicateAnnouncement(final Host host, final CommandInput command) {
        for (Announcements announcement : host.getAnnouncements()) {
            if (announcement.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * verify if there is a duplicate announcement in the list of announcements of a host
     *
     * @param host    the host to be verified
     * @param command the command to be verified
     * @return 1 if the announcement is not in the list, 0 otherwise
     */
    public static int checkAnnouncement(final Host host, final CommandInput command) {
        for (Announcements announcement : host.getAnnouncements()) {
            if (announcement.getName().equals(command.getName())) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * verify if there is already an event with the same name in the list of events of an artist
     *
     * @param artist  the artist to be verified
     * @param command the command to be verified
     * @return 0 if the event is not in the list, 1 otherwise
     */
    public static int checkEvent(final Artist artist, final CommandInput command) {
        for (Event event : artist.getEvents()) {
            if (event.getName().equals(command.getName())) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * verify if there is a user on the page of a user
     *
     * @param users        array of users
     * @param userToDelete the user to be deleted
     * @return 1 if the user is on the page, 0 otherwise
     */
    public static int checkPage(final ArrayList<User> users, final User userToDelete) {
        for (User user : users) {
            if (user.getCurrentPage().equals(userToDelete.getUsername())) {
                return 1;
            }
        }
        return 0;
    }

    /**
     * check if the date is valid
     *
     * @param command the command that contains the event
     * @return 1 if is valid, 0 otherwise
     */
    public static int checkDate(final CommandInput command) throws ParseException {
        int year = Integer.parseInt(command.getDate().substring(PARSEYEAR));
        int month = Integer.parseInt(command.getDate().substring(PARSEMONTHSTART, MAGICNUM));
        int day = Integer.parseInt(command.getDate().substring(0, 2));
        if (year > MAXYEAR || year < MINYEAR) {
            return 0;
        }
        if (month > NUMMOTHS || month < 1) {
            return 0;
        }
        if (month == 2) {
            if (day > FEBRUARY || day < 1) {
                return 0;
            }
        } else {
            if (day > NUMDAYS || day < 1) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * check for duplicate events
     *
     * @param artist  the artist that wants to add the event
     * @param command the command that contains the event
     * @return 1 if the event is not duplicate, 0 otherwise
     */
    public static int checkDuplicateEvent(final Artist artist, final CommandInput command) {
        for (Event event : artist.getEvents()) {
            if (event.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * check if there is a duplicate song in the list of songs of an album
     *
     * @param songForAlbum the list of songs of an album
     * return 1 if there is no duplicate, 0 otherwise
     */
    public static int checkSongsForAlbum(final ArrayList<Song> songForAlbum) {
        for (int i = 0; i < songForAlbum.size() - 1; i++) {
            for (int j = i + 1; j < songForAlbum.size(); j++) {
                if (songForAlbum.get(i).getName().equals(songForAlbum.get(j).getName())) {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * checks if the artist has already an album with the same name
     * @param artist the artist to be verified
     * @param command the command to be verified
     * @return 0 if the album is already in the list, 1 otherwise
     */
    public static int checkDuplicateAlbum(final Artist artist, final CommandInput command) {
        if (artist.getAlbum() == null) {
            return 1;
        }
        for (Album album : artist.getAlbum()) {
            if (album.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }

}
