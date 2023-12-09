package printcurrentpage;

import host.Host;

public class HostPage implements Visitable {
    private Host host;
    private String message;

    public HostPage(final Host host, final String message) {
        this.host = host;
        this.message = message;
    }

    @Override
    public void accept(Visitor visitor) {
        visitor.visit(this);
    }

    public final Host getHost() {
        return host;
    }

    public final void setHost(final Host host) {
        this.host = host;
    }

    public final String getMessage() {
        return message;
    }

    public final void setMessage(final String message) {
        this.message = message;
    }
}
