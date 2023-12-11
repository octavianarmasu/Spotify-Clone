package songs;

import artist.Album;
import artist.Artist;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

public class GetTopSongs {
    static final int MAGICNUM = 5;
    @JsonProperty("command")
    private String command = "getTop5Songs";
    @JsonProperty("timestamp")
    private int timestamp;
    @JsonProperty("result")
    private ArrayList<String> result = new ArrayList<>();


    public GetTopSongs() {
    }


    public final void setCommand(final String command) {
        this.command = command;
    }

    public final int getTimestamp() {
        return timestamp;
    }

    public final void setTimestamp(final int timestamp) {
        this.timestamp = timestamp;
    }

    public final ArrayList<String> getResult() {
        return this.result;
    }

    /**
     * Show the top 5 songs.
     *
     * @param songs the list of songs
     */
    public final void showTop5Songs(final ArrayList<Song> songs) {
        List<Song> copySong = new ArrayList<>(songs);
        copySong.sort(Comparator.comparingInt(Song::getLikes).reversed());

        if (copySong.size() > MAGICNUM) {
            copySong = copySong.subList(0, MAGICNUM);
        }
        for (Song song : copySong) {
            this.result.add(song.getName());
        }
    }

    /**
     * Show the top 5 albums.
     *
     * @param artists the list of artists in the database
     */
    public final void showTop5Albums(final ArrayList<Artist> artists) {
        List<Album> albumsToSort = new ArrayList<>();
        for (Artist artist : artists) {
            for (Album album : artist.getAlbum()) {
                int likes = 0;
                for (Song song : album.getSongs()) {
                    if (song.getArtist().equals(album.getArtist())) {
                        likes += song.getLikes();
                    }
                }
                album.setTotalLikes(likes);
                albumsToSort.add(album);
            }
        }
        albumsToSort.sort(Comparator.comparing(Album::getTotalLikes).reversed()
                .thenComparing(Album::getName));

        int resultCount = Math.min(MAGICNUM, albumsToSort.size());
        for (int i = 0; i < resultCount; i++) {
            this.result.add(albumsToSort.get(i).getName());
        }
    }


}
