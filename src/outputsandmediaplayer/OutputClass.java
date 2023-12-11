package outputsandmediaplayer;

import host.Announcements;
import artist.Artist;
import artist.Merch;
import com.fasterxml.jackson.annotation.JsonProperty;
import artist.Event;
import fileio.input.CommandInput;
import host.Host;
import podcast.Episode;
import podcast.EpisodesToAdd;
import podcast.Podcasts;
import songs.Song;
import users.User;

import java.text.ParseException;
import java.util.ArrayList;

public class OutputClass {
    @JsonProperty("command")
    private String command;
    @JsonProperty("user")
    private String user;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message;

    public OutputClass() {
    }


    public final String getCommand() {
        return command;
    }

    public final void setCommand(final String command) {
        this.command = command;
    }

    public final String getUser() {
        return user;
    }

    public final void setUser(final String user) {
        this.user = user;
    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }

    /**
     * Method that adds a song to the favorite list of a user or removes it
     *
     * @param commandInput the command that was given
     *                     (ex: "like")
     * @param users        the list of users in the database
     * @param songs        the list of songs in the database
     * @param player       the media player
     */
    public final void addLikedSongs(final CommandInput commandInput, final ArrayList<User> users,
                                    final ArrayList<Song> songs, final MediaPlayer player) {
        int found = 0;
        for (User userSearch : users) {
            if (userSearch.getUsername().equals(commandInput.getUsername())) {
                for (Song song : userSearch.getLikedSongs()) {
                    if (song.getName().equals(player.getSong())
                            && player.getArtist().equals(song.getArtist())) {
                        song.removeLike();
                        userSearch.getLikedSongs().remove(song);
                        this.setMessage("Unlike registered successfully.");
                        found = 1;
                        break;
                    }
                }
            }
        }
        if (found == 0) {
            for (User userSearch : users) {
                if (userSearch.getUsername().equals(commandInput.getUsername())) {
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())
                                && song.getArtist().equals(player.getArtist())) {
                            userSearch.addLikedSong(song);
                            this.setMessage("Like registered successfully.");
                            song.addLike();
                            break;
                        }
                    }
                }
            }
        }
    }

    /**
     * Changes the connection status of a user
     *
     * @param commandInput the command that was given
     * @param users        the list of users in the database
     */
    public final void changeConnectionStatus(final CommandInput commandInput,
                                             final ArrayList<User> users) {

        int found = 0;
        for (User userSearched : users) {
            if (userSearched.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                if (userSearched.getUserType().equals("user")) {
                    userSearched.changeConnection();
                    String auxUser = userSearched.getUsername();
                    this.setMessage(auxUser + " has changed status successfully.");
                } else {
                    this.setMessage(userSearched.getUsername() + " is not a normal user.");
                }
            }
        }
        if (found == 0) {
            this.setMessage("The username " + commandInput.getUsername() + " doesn't exist.");
        }
    }

    /**
     * Method that adds an announcement to a host's page
     *
     * @param commandInput the command that was given
     * @param hosts        the list of hosts in the database
     * @param users        the list of users in the database
     */
    public final void addAnnouncementFunc(final CommandInput commandInput,
                                          final ArrayList<Host> hosts,
                                          final ArrayList<User> users) {
        int found = 0;
        for (User userSearched : users) {
            if (userSearched.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                if (!userSearched.getUserType().equals("host")) {
                    this.setMessage(commandInput.getUsername() + " is not a host.");
                    return;
                }
            }
        }
        for (Host host : hosts) {
            if (host.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                if (Check.checkDuplicateAnnouncement(host, commandInput) == 0) {
                    this.setMessage(commandInput.getUsername()
                            + " has already added an announcement with this name.");
                    break;
                }
                Announcements announcement = new Announcements(commandInput.getName(),
                        commandInput.getDescription());
                host.addAnnouncement(announcement);
                this.setMessage(commandInput.getUsername()
                        + " has successfully added new announcement.");
            }
        }
        if (found == 0) {
            this.setMessage("User " + commandInput.getUsername() + " doesn't exist.");
        }
    }

    /**
     * adds merch to an artist's list of merch
     *
     * @param commandInput the command that was given
     * @param artists      the list of artists in the database
     * @param users        the list of users in the database
     */
    public final void addMerchFunc(final CommandInput commandInput,
                                   final ArrayList<Artist> artists,
                                   final ArrayList<User> users) {
        int found = 0;
        for (User userSearched : users) {
            if (userSearched.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                if (!userSearched.getUserType().equals("artist")) {
                    this.setMessage(userSearched.getUsername() + " is not an artist.");
                    break;
                }
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                int checkMerch = Check.checkDuplicateMerch(artist, commandInput);
                if (checkMerch == 0) {
                    this.setMessage(artist.getUsername()
                            + " has merchandise with the same name.");
                    break;
                }
                if (commandInput.getPrice() < 0) {
                    this.setMessage("Price for merchandise can not be negative.");
                    break;
                }
                Merch merch = new Merch(commandInput.getName(), commandInput.getPrice(),
                        commandInput.getDescription());
                artist.addMerch(merch);
                this.setMessage(commandInput.getUsername()
                        + " has added new merchandise successfully.");
            }
        }
        if (found == 0) {
            this.setMessage("The username " + commandInput.getUsername() + " doesn't exist.");
        }
    }

    /**
     * adds an envent to an artist's list of events
     *
     * @param commandInput the command that was given
     * @param artists      the list of artists in the database
     * @param users        the list of users in the database
     * @throws ParseException
     */
    public final void addEventFunc(final CommandInput commandInput,
                                   final ArrayList<Artist> artists, final ArrayList<User> users)
            throws ParseException {
        int found = 0;
        for (User userSearched : users) {
            if (userSearched.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                if (!userSearched.getUserType().equals("artist")) {
                    this.setMessage(userSearched.getUsername() + " is not an artist.");
                    break;
                }
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                int checkEvent = Check.checkDuplicateEvent(artist, commandInput);
                if (checkEvent == 0) {
                    this.setMessage(artist.getUsername()
                            + " has another event with the same name.");
                    break;
                }
                int checkDate = Check.checkDate(commandInput);
                if (checkDate == 0) {
                    this.setMessage("Event for " + commandInput.getUsername()
                            + " does not have a valid date.");
                    break;
                }
                Event event = new Event(commandInput.getName(), commandInput.getDescription(),
                        commandInput.getDate());
                artist.addEvent(event);
                this.setMessage(commandInput.getUsername()
                        + " has added new event successfully.");
            }
        }
        if (found == 0) {
            this.setMessage("The username " + commandInput.getUsername() + " doesn't exist.");
        }
    }

    /**
     * removes an announcement from a host's list of announcements
     *
     * @param commandInput the command that was given
     * @param hosts        the list of hosts in the database
     * @param users        the list of users in the database
     */
    public final void removeAnnouncementFunc(final CommandInput commandInput,
                                             final ArrayList<Host> hosts,
                                             final ArrayList<User> users) {
        int found = 0;
        for (User userSearched : users) {
            if (userSearched.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                if (!userSearched.getUserType().equals("host")) {
                    this.setMessage(commandInput.getUsername() + " is not a host.");
                    return;
                }
            }
        }
        for (Host host : hosts) {
            if (host.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                if (Check.checkAnnouncement(host, commandInput) == 0) {
                    this.setMessage(commandInput.getUsername()
                            + " has no announcement with the given name.");
                    break;
                }
                host.removeAnnouncement(commandInput.getName());
                this.setMessage(commandInput.getUsername()
                        + " has successfully deleted the announcement.");
            }
        }
        if (found == 0) {
            this.setMessage("The username " + commandInput.getUsername()
                    + " doesn't exist.");
        }
    }

    /**
     * adds a podcast to a host's list of podcasts
     * @param commandInput the command that was given
     * @param hosts the list of hosts in the database
     * @param users the list of users in the database
     * @param podcasts the list of podcasts in the database
     */
    public final void addPodcastFunc(final CommandInput commandInput, final ArrayList<Host> hosts,
                                       final ArrayList<User> users,
                                       final ArrayList<Podcasts> podcasts) {
        int found = 0;
        for (User userSearched : users) {
            if (commandInput.getUsername().equals(userSearched.getUsername())) {
                found = 1;
                if (!userSearched.getUserType().equals("host")) {
                    this.setMessage(commandInput.getUsername() + " is not a host.");
                    return;
                }
            }
        }
        for (Host host : hosts) {
            if (host.getUsername().equals(commandInput.getUsername())) {
                found = 1;
                ArrayList<Episode> episodesForPodcast = new ArrayList<>();
                for (EpisodesToAdd episode : commandInput.getEpisodes()) {
                    Episode episodeAux = new Episode(episode.getName(),
                            episode.getDuration(), episode.getDescription());
                    episodesForPodcast.add(episodeAux);
                }
                if (Check.checkDuplicatePodcast(host, commandInput) == 0) {
                    this.setMessage(commandInput.getUsername()
                            + " has another podcast with the same name.");
                    break;
                }
                if (Check.checkDuplicateEpisode(episodesForPodcast) == 0) {
                    this.setMessage(commandInput.getUsername()
                            + " has the same episode in this podcast.");
                    break;
                }
                Podcasts podcastsToAdd = new Podcasts(commandInput.getName(),
                        commandInput.getUsername(),
                        episodesForPodcast);
                host.addPodcast(podcastsToAdd);
                podcasts.add(podcastsToAdd);
                this.setMessage(commandInput.getUsername()
                        + " has added new podcast successfully.");
            }
        }
        if (found == 0) {
            this.setMessage("User " + commandInput.getUsername() + " does not exist.");
        }
    }

}
