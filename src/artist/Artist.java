package artist;

import events.Event;
import fileio.input.CommandInput;
import users.User;

import java.util.ArrayList;

public class Artist extends User {
    private final ArrayList<Album> album = new ArrayList<>();
    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<Merch> merch = new ArrayList<>();
    private int nrOfLikes = 0;

    public Artist() {

    }

    /**
     * Adds an album to the user's list of albums.
     *
     * @param albumToBeAdded the album to be added
     */
    public final void addAlbum(final Album albumToBeAdded) {
        this.album.add(albumToBeAdded);
    }

    public final ArrayList<Album> getAlbum() {
        return this.album;
    }

    /**
     * Adds an event to the user's list of events.
     *
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
     *
     * @param merchToBeAdded the merch to be added
     */
    public final void addMerch(final Merch merchToBeAdded) {
        this.merch.add(merchToBeAdded);
    }

    public final ArrayList<Merch> getMerch() {
        return this.merch;
    }

    /**
     * Searches for an album in the user's list of albums.
     *
     * @param command    the command that contains the album's name
     * @param results    the list of results
     * @param numFilters the number of filters
     */

    public final void verifyAll(final CommandInput command, final ArrayList<String> results,
                                final int numFilters) {
        int checkPassed;
        String nameFilter = command.getFilters().getName();
        String ownerFilter = command.getFilters().getOwner();
        String descriptionFilter = command.getFilters().getDescription();
        for (Album album1 : this.album) {
            checkPassed = 0;
            if (nameFilter != null) {
                if (album1.getName().startsWith(nameFilter)) {
                    checkPassed++;
                }
            }
            if (ownerFilter != null) {
                if (this.getUsername().equals(ownerFilter)) {
                    checkPassed++;
                }
            }
            if (descriptionFilter != null) {
                if (album1.getDescription().equals(descriptionFilter)) {
                    checkPassed++;
                }
            }
            if (checkPassed == numFilters) {
                results.add(album1.getName());
            }
        }
    }

    /**
     * Removes an album from the user's list of albums.
     *
     * @param name the name of the album to be removed
     */
    public final void removeAlbum(final String name) {
        for (Album album1 : this.album) {
            if (album1.getName().equals(name)) {
                this.album.remove(album);
                break;
            }
        }
    }

    /**
     * Removes an enevnt from the user's list of events.
     *
     * @param name the name of the event to be removed
     */
    public final void removeEvent(final String name) {
        for (Event event : this.events) {
            if (event.getName().equals(name)) {
                this.events.remove(event);
                break;
            }
        }
    }

    public final int getNrOfLikes() {
        return this.nrOfLikes;
    }

    public final void setNrOfLikes(final int nrOfLikes) {
        this.nrOfLikes = nrOfLikes;
    }
}
