package printcurrentpage;

import users.User;


public class HomePage implements Visitable {
    private final User user;
    private String message;

    public HomePage(final User user, final String message) {
        this.user = user;
        this.message = message;
    }

    @Override
    public final void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    public final User getUser() {
        return user;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String string) {
        this.message = string;
    }
}

