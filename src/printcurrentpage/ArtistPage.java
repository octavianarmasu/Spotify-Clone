package printcurrentpage;

import artist.Artist;


public class ArtistPage implements Visitable {
    private final Artist artist;
    private String message;

    public ArtistPage(final Artist artist, final String message) {
        this.artist = artist;
        this.message = message;
    }

    @Override
    public final void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    public final Artist getArtist() {
        return artist;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }

}
