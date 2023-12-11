package host;

import podcast.Podcasts;
import users.User;

import java.util.ArrayList;

public class Host extends User {

    private ArrayList<Podcasts> podcasts = new ArrayList<>();

    private ArrayList<Announcements> announcements = new ArrayList<>();

    public Host() {

    }

    /**
     * adds a podcast to the host's list of podcasts
     *
     * @param podcast the podcast to be added
     */
    public final void addPodcast(final Podcasts podcast) {
        podcasts.add(podcast);
    }

    public final ArrayList<Podcasts> getPodcasts() {
        return podcasts;
    }

    /**
     * adds an announcement to the host's list of announcements
     *
     * @param announcement the announcement to be added
     */
    public final void addAnnouncement(final Announcements announcement) {
        announcements.add(announcement);
    }

    public final ArrayList<Announcements> getAnnouncements() {
        return announcements;
    }

    /**
     * removes an announcement from the host's list of announcements
     *
     * @param name the name of the announcement to be removed
     */
    public final void removeAnnouncement(final String name) {
        for (int i = 0; i < announcements.size(); i++) {
            if (announcements.get(i).getName().equals(name)) {
                announcements.remove(i);
                break;
            }
        }
    }

    /**
     * removes a podcast from the host's list of podcasts
     *
     * @param name the name of the podcast to be removed
     */
    public final void removePodcast(final String name) {
        for (int i = 0; i < podcasts.size(); i++) {
            if (podcasts.get(i).getName().equals(name)) {
                podcasts.remove(i);
                break;
            }
        }

    }
}
