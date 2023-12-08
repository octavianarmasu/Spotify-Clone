package artist;

import events.Event;
import fileio.input.CommandInput;
import songs.Song;
import users.User;

import java.util.ArrayList;

public class Artist extends User {
    private final ArrayList<Album> album = new ArrayList<>();
    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<Merch> merch = new ArrayList<>();
    private int nrOfLikes = 0;
    public Artist () {

    }

    /**
     * Adds an album to the user's list of albums.
     * @param album the album to be added
     */
    public final void addAlbum(final Album album) {
        this.album.add(album);
    }

    public final ArrayList<Album> getAlbum() {
        return this.album;
    }

    /**
     * Adds an event to the user's list of events.
     * @param event the event to be added
     */
    public final void addEvent(final Event event) {
        this.events.add(event);
    }

    public final ArrayList<Event> getEvents() {
        return this.events;
    }

    /**
     * Adds a merch to the user's list of merch.
     * @param merch the merch to be added
     */
    public final void addMerch(final Merch merch) {
        this.merch.add(merch);
    }
    public final ArrayList<Merch> getMerch() {
        return this.merch;
    }


    public final void verifyAll(final CommandInput command, final ArrayList<String> results,
                                final int numFilters) {
        int checkPassed;
        String nameFilter = command.getFilters().getName();
        String ownerFilter = command.getFilters().getOwner();
        String descriptionFilter = command.getFilters().getDescription();
        for (Album album : this.album) {
            checkPassed = 0;
            if (nameFilter != null) {
                if (album.getName().startsWith(nameFilter)) {
                    checkPassed ++;
                }
            }
            if (ownerFilter != null) {
                if (this.getUsername().equals(ownerFilter)) {
                    checkPassed ++;
                }
            }
            if (descriptionFilter != null) {
                if (album.getDescription().equals(descriptionFilter)) {
                    checkPassed ++;
                }
            }
            if (checkPassed == numFilters) {
                results.add(album.getName());
            }
        }
    }

    public final void removeAlbum(final String name) {
        for (Album album : this.album) {
            if (album.getName().equals(name)) {
                this.album.remove(album);
                break;
            }
        }
    }

    public final void removeEvent(final String name) {
        for (Event event : this.events) {
            if (event.getName().equals(name)) {
                this.events.remove(event);
                break;
            }
        }
    }
    public final void setNrOfLikes() {
        for (Album album : this.album) {
            for (Song song : album.getSongs()) {
                this.nrOfLikes += song.getLikes();
            }
        }
    }
    public final int getNrOfLikes() {
        return this.nrOfLikes;
    }
}
