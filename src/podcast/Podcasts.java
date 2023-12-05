package podcast;

import java.util.ArrayList;
import fileio.input.CommandInput;

public class Podcasts {
    private String name;
    private String owner;
    private ArrayList<Episode> episodes;

    public Podcasts(final String name, final String owner, final ArrayList<Episode> episodes) {
        this.name = name;
        this.owner = owner;
        this.episodes = episodes;
    }

    public final String getName() {
        return name;
    }

    public final void setName(final String name) {
        this.name = name;
    }

    public final String getOwner() {
        return owner;
    }

    public final void setOwner(final String owner) {
        this.owner = owner;
    }

    public final ArrayList<Episode> getEpisodes() {
        return episodes;
    }

    public final void setEpisodes(final ArrayList<Episode> episodes) {
        this.episodes = episodes;
    }

    /**
     * Verify the owner of the podcast.
     * @param command the command input
     * @param results the results
     */
    public final void verifyOwner(final CommandInput command,
                                  final ArrayList<String> results) {
        String ownerFilter = command.getFilters().getOwner();
        if (owner != null) {
            if (this.owner.equals(ownerFilter)) {
                results.add(this.name);
            }
        }
    }

    /**
     * Verify the name of the podcast.
     * @param command the command input
     * @param results the results
     */
    public final void verifyName(final CommandInput command,
                                 final ArrayList<String> results) {
        String nameFilter = command.getFilters().getName();
        if (nameFilter != null) {
            if (this.name.startsWith(nameFilter)) {
                if (!results.contains(this.name)) {
                    results.add(this.name);
                }
            }
        }
    }
}

