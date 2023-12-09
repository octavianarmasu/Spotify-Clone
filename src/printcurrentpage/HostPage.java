package printcurrentpage;

import host.Host;

public final class HostPage implements Visitable {
    private Host host;
    private String message;

    public HostPage(final Host host, final String message) {
        this.host = host;
        this.message = message;
    }

    @Override
    public void accept(final Visitor visitor) {
        visitor.visit(this);
    }

    public  Host getHost() {
        return host;
    }

    public  void setHost(final Host host) {
        this.host = host;
    }

    public  String getMessage() {
        return message;
    }

    public void setMessage(final String message) {
        this.message = message;
    }
}
