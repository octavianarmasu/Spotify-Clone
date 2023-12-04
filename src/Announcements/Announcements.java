package Announcements;

public class Announcements {
    private String name;
    private String description;

    public Announcements(final String name, final String description) {
        this.name = name;
        this.description = description;
    }

    public Announcements() {

    }

    public final String getName() {
        return this.name;
    }

    public final String getDescription() {
        return this.description;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final void setDescription(final String description) {
        this.description = description;
    }
}
