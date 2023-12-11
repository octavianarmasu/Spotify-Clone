package playlist;

import com.fasterxml.jackson.annotation.JsonProperty;
import users.User;

import java.util.ArrayList;
import java.util.List;

public class GetTopPlaylist {
    static final int MAGICNUM = 5;
    @JsonProperty("command")
    private final String command = "getTop5Playlists";
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<String> result = new ArrayList<>();

    public GetTopPlaylist() {

    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final ArrayList<String> getResult() {
        return result;
    }

    public final void setResult(final ArrayList<String> results) {
        this.result = results;
    }

    /**
     * adds the top 5 playlists to the result list
     *
     * @param users all users in the database
     */
    public final void showTop5Playlist(final ArrayList<User> users) {

        ArrayList<Playlist> copyPlaylist = new ArrayList<>();
        for (User user : users) {
            copyPlaylist.addAll(user.getPlaylists());
        }
        for (int i = 0; i < copyPlaylist.size() - 1; i++) {
            for (int j = i + 1; j < copyPlaylist.size(); j++) {
                int followers1 = copyPlaylist.get(i).getFollowers();
                int followers2 = copyPlaylist.get(j).getFollowers();
                if (followers1 < followers2) {
                    Playlist aux = copyPlaylist.get(i);
                    copyPlaylist.set(i, copyPlaylist.get(j));
                    copyPlaylist.set(j, aux);
                } else {
                    if (followers1 == followers2) {
                        int timestamp1 = copyPlaylist.get(i).getTimestamp();
                        int timestamp2 = copyPlaylist.get(j).getTimestamp();
                        if (timestamp1 > timestamp2) {
                            Playlist aux = copyPlaylist.get(i);
                            copyPlaylist.set(i, copyPlaylist.get(j));
                            copyPlaylist.set(j, aux);
                        }
                    }
                }
            }
        }
        List<Playlist> copyPlaylist1 = copyPlaylist;
        if (copyPlaylist.size() > MAGICNUM) {
            copyPlaylist1 = copyPlaylist.subList(0, MAGICNUM);
        }
        for (Playlist playlist : copyPlaylist1) {
            this.result.add(playlist.getName());
        }

    }
}
