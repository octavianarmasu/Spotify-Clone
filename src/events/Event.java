package events;

public class Event {
    private String name;
    private String description;
    private String date;

    public Event(final String name, final String description, final String date) {
        this.name = name;
        this.description = description;
        this.date = date;
    }
    public Event(){

    }

    public final String getName() {
        return this.name;
    }
    public final String getDescription() {
        return this.description;
    }
    public final String getDate() {
        return this.date;
    }
    public final void setName(final String name) {
        this.name = name;
    }
    public final void setDescription(final String description) {
        this.description = description;
    }
    public final void setDate(final String date) {
        this.date = date;
    }
}
