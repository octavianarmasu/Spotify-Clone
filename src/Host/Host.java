package Host;

import main.Podcasts;
import main.User;
import Announcements.Announcements;

import java.util.ArrayList;

public class Host extends User {

    private ArrayList<Podcasts> podcasts = new ArrayList<>();

    private ArrayList<Announcements> announcements = new ArrayList<>();

    public Host(){

    }

    /**
     * adds a podcast to the host's list of podcasts
     * @param podcast the podcast to be added
     */
    public final void addPodcast(final Podcasts podcast){
        podcasts.add(podcast);
    }
    public final ArrayList<Podcasts> getPodcasts(){
        return podcasts;
    }

    public final void addAnnouncement(final Announcements announcement){
        announcements.add(announcement);
    }
    public final ArrayList<Announcements> getAnnouncements(){
        return announcements;
    }
}
