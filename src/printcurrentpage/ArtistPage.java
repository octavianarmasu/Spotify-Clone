package printcurrentpage;

import artist.Artist;
import fileio.input.CommandInput;


public class ArtistPage implements Visitable {
    private final Artist artist;
    private String message;
    private CommandInput command;

    public ArtistPage(final Artist artist, final String message, CommandInput commandInput) {
        this.artist = artist;
        this.message = message;
        this.command = commandInput;
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

    public CommandInput getCommand() {
        return command;
    }
}
