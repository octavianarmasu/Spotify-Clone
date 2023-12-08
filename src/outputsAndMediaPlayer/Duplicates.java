package outputsAndMediaPlayer;

import announcements.Announcements;
import fileio.input.CommandInput;
import host.Host;
import podcast.Episode;
import podcast.Podcasts;

import java.util.ArrayList;

public class Duplicates {

    public Duplicates() {
    }

    /**
     * verify if a host has already the podcast in his list of podcasts
     *
     * @param host    the host to be verified
     * @param command the command to be verified
     * @return 0 if the podcast is already in the list, 1 otherwise
     */
    public static int checkDuplicatePodcast(final Host host, final CommandInput command) {
        if (host.getPodcasts() == null) return 1;
        for (Podcasts podcast : host.getPodcasts()) {
            if (podcast.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * verify if an episode was added twice in the list of episodes
     *
     * @param episodesToAdd the list of episodes to be verified
     * @return 0 if the episode is already in the list, 1 otherwise
     */
    public static int checkDuplicateEpisode(final ArrayList<Episode> episodesToAdd) {
        for (int i = 0; i < episodesToAdd.size() - 1; i++) {
            for (int j = i + 1; j < episodesToAdd.size(); j++) {
                if (episodesToAdd.get(i).getName().equals(episodesToAdd.get(j).getName())) {
                    return 0;
                }
            }
        }
        return 1;
    }

    /**
     * verify if an announcement was added twice in the list of announcements of a host
     *
     * @param host    the host to be verified
     * @param command the command to be verified
     * @return 0 if the announcement is already in the list, 1 otherwise
     */
    public static int checkDuplicateAnnouncement(final Host host, final CommandInput command) {
        for (Announcements announcement : host.getAnnouncements()) {
            if (announcement.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }
}
