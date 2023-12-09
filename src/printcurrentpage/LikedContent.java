package printcurrentpage;

import songs.Song;
import users.User;

import java.util.ArrayList;

public class LikedContent implements Visitable {
    private User user;
    private ArrayList<User> users;
    private String message;

    public LikedContent(final User user, final ArrayList<User> users, final String message) {
        this.user = user;
        this.users = users;
        this.message = message;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public final User getUser() {
        return user;
    }

    public final ArrayList<User> getUsers() {
        return users;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String string) {
        this.message = string;
    }
}

