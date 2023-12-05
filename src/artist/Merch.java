package artist;

public class Merch {
    private String name;
    private int price;
    private String description;

    public Merch(final String name, final int price, final String description) {
        this.name = name;
        this.price = price;
        this.description = description;
    }
    public final String getName() {
        return name;
    }
    public final int getPrice() {
        return price;
    }
    public final String getDescription() {
        return description;
    }
    public final void setName(final String name) {
        this.name = name;
    }
    public final void setPrice(final int price) {
        this.price = price;
    }
    public final void setDescription(final String description) {
        this.description = description;
    }

}
