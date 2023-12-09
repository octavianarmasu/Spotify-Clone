package printcurrentpage;

import artist.Artist;
import com.fasterxml.jackson.annotation.JsonProperty;
import fileio.input.CommandInput;
import host.Host;
import users.User;

import java.util.ArrayList;

public class PrintPage {
    @JsonProperty("user")
    private String user;
    @JsonProperty("command")
    private String command;
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("message")
    private String message = null;

    public PrintPage() {
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
     * prints the current page of the user using the visitor pattern
     *
     * @param artists the list of artists in the database
     * @param hosts the list of hosts in the database
     * @param commandInput the command that was given as input
     * @param users the list of users in the database
     */
    public final void printPageFunc(final ArrayList<Artist> artists, final ArrayList<Host> hosts,
                                    final CommandInput commandInput,
                                    final ArrayList<User> users) {
        Visitor visitor = new Print();
        for (User userSearched : users) {
            if (userSearched.getUsername().equals(commandInput.getUsername())) {
                if (userSearched.getCurrentPage().equals("Home")
                        || userSearched.getCurrentPage().equals("home")) {
                    HomePage homePage = new HomePage(userSearched, message);
                    homePage.accept(visitor);
                    this.message = homePage.getMessage();
                }
                if (userSearched.getCurrentPage().equals("LikedContent")) {
                    LikedContent likedContent =
                            new LikedContent(userSearched, users, message);
                    likedContent.accept(visitor);
                    this.message = likedContent.getMessage();
                }
                for (Artist artist : artists) {
                    if (userSearched.getCurrentPage().equals(artist.getUsername())) {
                        ArtistPage artistPage = new ArtistPage(artist, message);
                        artistPage.accept(visitor);
                        this.message = artistPage.getMessage();
                    }
                }
                for (Host host : hosts) {
                    if (userSearched.getCurrentPage().equals(host.getUsername())) {
                        HostPage hostPage = new HostPage(host, message);
                        hostPage.accept(visitor);
                        this.message = hostPage.getMessage();
                    }
                }
            }
        }
    }
}
