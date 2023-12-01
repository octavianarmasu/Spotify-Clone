package Artist;

import Events.Event;
import com.sun.jdi.ArrayReference;
import main.User;

import java.util.ArrayList;

public class Artist extends User {
    private final ArrayList<Album> album = new ArrayList<>();
    private final ArrayList<Event> events = new ArrayList<>();
    private final ArrayList<Merch> merch = new ArrayList<>();
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


}
