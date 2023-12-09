package printcurrentpage;

import artist.Artist;
import host.Host;
import playlist.Playlist;
import podcast.Podcasts;
import songs.Song;
import users.User;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public final class Print implements Visitor {

    static final int MAGICNUM = 5;
    @Override
    public void visit(final HomePage homePage) {
        User user = homePage.getUser();
        StringBuilder message = new StringBuilder();
        List<Song> likedCopy = new ArrayList<>(Collections.unmodifiableList(user.getLikedSongs()));
        likedCopy.sort(new Comparator<Song>() {
            @Override
            public int compare(final Song o1, final Song o2) {
                return o2.getLikes() - o1.getLikes();
            }
        });
        int likedSize = likedCopy.size();
        if (likedCopy.size() > MAGICNUM) {
            likedSize = MAGICNUM;
        }
        message.append("Liked songs:\n\t[");
        for (int i = 0; i < likedSize; i++) {
            if (i == likedSize - 1) {
                message.append(likedCopy.get(i).getName());
                break;
            }
            message.append(likedCopy.get(i).getName()).append(", ");
        }
        message.append("]\n\nFollowed playlists:\n\t[");
        for (int i = 0; i < user.getFollowedPlaylists().size(); i++) {
            if (i == user.getFollowedPlaylists().size() - 1) {
               message.append(user.getFollowedPlaylists().get(i).getName()).append("]");
                break;
            }
            message.append(user.getFollowedPlaylists().get(i).getName()).append(", ");
        }
        if (user.getFollowedPlaylists().isEmpty()) {
            message.append("]");
        }
        homePage.setMessage(message.toString());
    }

    @Override
    public void visit(final LikedContent likedContent) {
        User user = likedContent.getUser();
        ArrayList<User> users = likedContent.getUsers();
        StringBuilder message = new StringBuilder();
        message.append("Liked songs:\n\t[");
        for (int i = 0; i < user.getLikedSongs().size(); i++) {
            if (i == user.getLikedSongs().size() - 1) {
                message.append(user.getLikedSongs().get(i).getName()).append(" - ");
                message.append(user.getLikedSongs().get(i).getArtist()).append("]\n\n");
                break;
            }
            message.append(user.getLikedSongs().get(i).getName()).append(" - ");
            message.append(user.getLikedSongs().get(i).getArtist()).append(", ");

        }
        if (user.getLikedSongs().isEmpty()) {
            message.append("]\n\n");
        }
        message.append("Followed playlists:\n\t[");
        for (int i = 0; i < user.getFollowedPlaylists().size(); i++) {
            if (i == user.getFollowedPlaylists().size() - 1) {
                message.append(user.getFollowedPlaylists().get(i).getName()).append(" - ");

                for (User user1 : users) {
                    for (Playlist playlist : user1.getPlaylists()) {
                        String aux = user.getFollowedPlaylists().get(i).getName();
                        if (playlist.getName().equals(aux)) {
                            message.append(user1.getUsername()).append("]");
                        }
                    }
                }
                break;
            }
            message.append(user.getFollowedPlaylists().get(i).getName()).append(" - ");
            for (User user1 : users) {
                for (Playlist playlist : user1.getPlaylists()) {
                    if (playlist.getName().equals(user.getFollowedPlaylists().get(i).getName())) {
                        message.append(user1.getUsername()).append(", ");
                    }
                }
            }
        }
        if (user.getFollowedPlaylists().isEmpty()) {
            message.append("]");
        }
        likedContent.setMessage(message.toString());
    }

    @Override
    public void visit(final ArtistPage artistPage) {
        Artist artist = artistPage.getArtist();
        StringBuilder message = new StringBuilder();
        message.append("Albums:\n\t[");
        for (int i = 0; i < artist.getAlbum().size(); i++) {
            if (i == artist.getAlbum().size() - 1) {
                message.append(artist.getAlbum().get(i).getName()).append("]\n\n");
                break;
            }
            message.append(artist.getAlbum().get(i).getName()).append(", ");
        }

        if (artist.getMerch().isEmpty()) {
            message.append("Merch:\n\t[]\n\n");
        } else {
            message.append("Merch:\n\t[");
            for (int i = 0; i < artist.getMerch().size(); i++) {
                if (i == artist.getMerch().size() - 1) {
                    message.append(artist.getMerch().get(i).getName()).append(" - ");
                    message.append(artist.getMerch().get(i).getPrice()).append(":\n\t");
                    message.append(artist.getMerch().get(i).getDescription()).append("]\n\n");
                    break;
                }
                message.append(artist.getMerch().get(i).getName()).append(" - ");
                message.append(artist.getMerch().get(i).getPrice()).append(":\n\t");
                message.append(artist.getMerch().get(i).getDescription()).append(", ");
            }
        }
        message.append("Events:\n\t[");
        if (artist.getEvents().isEmpty()) {
            message.append("]");
        }
        for (int i = 0; i < artist.getEvents().size(); i++) {
            if (i == artist.getEvents().size() - 1) {
                message.append(artist.getEvents().get(i).getName()).append(" - ");
                message.append(artist.getEvents().get(i).getDate()).append(":\n\t");
                message.append(artist.getEvents().get(i).getDescription()).append("]");
                break;
            }
            message.append(artist.getEvents().get(i).getName()).append(" - ");
            message.append(artist.getEvents().get(i).getDate()).append(":\n\t");
            message.append(artist.getEvents().get(i).getDescription()).append(", ");
        }

        artistPage.setMessage(message.toString());
    }

    @Override
    public void visit(final HostPage hostPage) {
        Host host = hostPage.getHost();
        StringBuilder message = new StringBuilder();
        message.append("Podcasts:\n\t[");
        for (int i = 0; i < host.getPodcasts().size(); i++) {
            message.append(host.getPodcasts().get(i).getName()).append(":\n\t[");
            for (Podcasts podcast : host.getPodcasts()) {
                if (podcast.getName().equals(host.getPodcasts().get(i).getName())) {
                    for (int j = 0; j < podcast.getEpisodes().size(); j++) {
                        if (j == podcast.getEpisodes().size() - 1) {
                            message.append(podcast.getEpisodes().get(j).getName()).append(" - ");
                            message.append(podcast.getEpisodes().get(j).getDescription());
                            message.append("]\n");
                            break;
                        }
                        message.append(podcast.getEpisodes().get(j).getName()).append(" - ");
                        message.append(podcast.getEpisodes().get(j).getDescription()).append(", ");
                    }
                }
            }
            if (i != host.getPodcasts().size() - 1) {
                message.append(", ");
            } else {
                message.append("]\n\n");
            }
        }
        message.append("Announcements:\n\t[");
        for (int i = 0; i < host.getAnnouncements().size(); i++) {
            message.append(host.getAnnouncements().get(i).getName()).append(":\n\t");
            message.append(host.getAnnouncements().get(i).getDescription()).append("\n");

            if (i != host.getAnnouncements().size() - 1) {
                message.append(", ");
            } else {
                message.append("]");
            }
        }
        hostPage.setMessage(message.toString());
    }
}
