package main;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import Artist.Album;
import Events.Event;

public class User {
    private String username;
    private int age;
    private String city;
    private final ArrayList<Playlist> playlists = new ArrayList<>();
    private final ArrayList<Playlist> oldPlaylists = new ArrayList<>();
    private final ArrayList<String> likedSongs = new ArrayList<>();
    private final ArrayList<Playlist> follwedPlaylist = new ArrayList<>();
    private int select;
    private int searchCount;
    private int searchSong;
    private int searchPlaylist;
    private int searchPodcast;
    private int loadCheck;
    private int loadPlaylist;
    private int loadPodcast;
    private int loadSong;
    private int selectPlaylist;
    private int searchArtist;
    private int searchAlbum;
    private MediaPlayer mediaPlayer = new MediaPlayer();
    private String connection = "online";
    private String userType = "user";
    private String currentPage = "home";
    private int loadAlbum;


    public User() {
    }

    public User(final String username, final int age, final String city) {
        this.username = username;
        this.age = age;
        this.city = city;
    }
    public User(final User user) {
        this.username = user.getUsername();
        this.age = user.getAge();
        this.city = user.getCity();
        this.playlists.addAll(user.getPlaylists());
        this.likedSongs.addAll(user.getLikedSongs());
        this.follwedPlaylist.addAll(user.getFollowedPlaylists());
        this.select = user.getSelect();
        this.searchCount = user.getSearchCount();
        this.searchSong = user.getSearchSong();
        this.searchPlaylist = user.getSearchPlaylist();
        this.searchPodcast = user.getSearchPodcast();
        this.loadCheck = user.getLoadCheck();
        this.loadPlaylist = user.getLoadPlaylist();
        this.loadPodcast = user.getLoadPodcast();
        this.loadSong = user.getLoadSong();
        this.selectPlaylist = user.getSelectPlaylist();
        this.mediaPlayer = user.getMediaPlayer();
        this.connection = user.getConnection();
        this.userType = user.getUserType();
        this.currentPage = user.getCurrentPage();
        this.searchAlbum = user.getSearchAlbum();
        this.searchArtist = user.getSearchArtist();
        this.loadAlbum = user.getLoadAlbum();

    }

    public final String getUsername() {
        return username;
    }

    public final void setUsername(final String username) {
        this.username = username;
    }

    public final int getAge() {
        return age;
    }

    public final void setAge(final int age) {
        this.age = age;
    }

    public final String getCity() {
        return city;
    }

    public final void setCity(final String city) {
        this.city = city;
    }

    /**
     * Adds a playlist to the user's list of playlists.
     *
     * @param playlist the playlist to be added
     */
    public final void addPlaylist(final Playlist playlist) {
        this.playlists.add(playlist);
    }

    public final ArrayList<Playlist> getPlaylists() {
        return this.playlists;
    }

    /**
     * Adds a song to the user's list of liked songs.
     *
     */
    public final void addLikedSong(final String song) {
        this.likedSongs.add(song);
    }

    /**
     * Returns the list of liked songs.
     */
    public final ArrayList<String> getLikedSongs() {
        return this.likedSongs;
    }

    /**
     * Adds a playlist to the user's list of followed playlists.
     */
    public final void addFollowedPlaylist(final Playlist playlist) {
        this.follwedPlaylist.add(playlist);
    }

    public final ArrayList<Playlist> getFollowedPlaylists() {
        return this.follwedPlaylist;
    }

    public final int getSelect() {
        return this.select;
    }

    public final void setSelect(final int select) {
        this.select = select;
    }

    public final int getSearchCount() {
        return this.searchCount;
    }

    public final void setSearchCount(final int searchCount) {
        this.searchCount = searchCount;
    }

    public final int getSearchSong() {
        return this.searchSong;
    }

    public final void setSearchSong(final int searchSong) {
        this.searchSong = searchSong;
    }

    public final int getSearchPlaylist() {
        return this.searchPlaylist;
    }

    public final void setSearchPlaylist(final int searchPlaylist) {
        this.searchPlaylist = searchPlaylist;
    }

    public final int getSearchPodcast() {
        return this.searchPodcast;
    }

    public final void setSearchPodcast(final int searchPodcast) {
        this.searchPodcast = searchPodcast;
    }

    public final int getLoadCheck() {
        return this.loadCheck;
    }

    public final void setLoadCheck(final int loadCheck) {
        this.loadCheck = loadCheck;
    }

    public final int getLoadPlaylist() {
        return this.loadPlaylist;
    }

    public final void setLoadPlaylist(final int loadPlaylist) {
        this.loadPlaylist = loadPlaylist;
    }

    public final int getLoadPodcast() {
        return this.loadPodcast;
    }

    public final void setLoadPodcast(final int loadPodcast) {
        this.loadPodcast = loadPodcast;
    }

    public final int getLoadSong() {
        return this.loadSong;
    }

    public final void setLoadSong(final int loadSong) {
        this.loadSong = loadSong;
    }

    public final int getSelectPlaylist() {
        return this.selectPlaylist;
    }

    public final void setSelectPlaylist(final int selectPlaylist) {
        this.selectPlaylist = selectPlaylist;
    }

    public final MediaPlayer getMediaPlayer() {
        return this.mediaPlayer;
    }

    public final void setMediaPlayer(final MediaPlayer mediaPlayer) {
        this.mediaPlayer = mediaPlayer;
    }

    /**
     * Clears the list of liked songs.
     */
    public final void clearLikedSongs() {
        this.likedSongs.clear();
    }

    /**
     * Adds a playlist to the list of old playlists.
     */
    public final void addOldPlaylist(final Playlist playlist) {
        this.oldPlaylists.add(playlist);
    }

    public final ArrayList<Playlist> getOldPlaylists() {
        return this.oldPlaylists;
    }

    /**
     * Clears the list of old playlists.
     */
    public final void clearOldPlaylists() {
        this.oldPlaylists.clear();
    }

    /**
     * Saves the un-shuffled playlist.
     */
    public final ArrayList<String> changeOldPlaylist(final Playlist playlistChange) {
        if (playlistChange != null && playlistChange.getSongs() != null) {
            List<String> songs = Collections.unmodifiableList(playlistChange.getSongs());
            return new ArrayList<String>(songs);
        }
        return null;
    }

    /**
     * Add to the OldPlaylist.
     */
    public final void addOldPlaylistUser(final Playlist playlistChange) {
        this.oldPlaylists.add(playlistChange);

    }

    public final String getConnection() {
        return this.connection;
    }

    public final void setConnection(final String connection) {
        this.connection = connection;
    }

    /**
     * Changes the connection status.
     */
    public final void changeConnection() {
        if (this.connection.equals("online")) {
            this.connection = "offline";
        } else {
            this.connection = "online";
        }
    }

    public final String getUserType() {
        return this.userType;
    }

    public final void setUserType(final String userType) {
        this.userType = userType;
    }

    public final String getCurrentPage() {
        return this.currentPage;
    }
    public final void setCurrentPage(final String currentPage) {
        this.currentPage = currentPage;
    }
    public final int getSearchArtist() {
        return this.searchArtist;
    }
    public final int getSearchAlbum() {
        return this.searchAlbum;
    }
    public final void setSearchArtist(final int searchArtist) {
        this.searchArtist = searchArtist;
    }
    public final void setSearchAlbum(final int searchAlbum) {
        this.searchAlbum = searchAlbum;
    }

    public final int getLoadAlbum() {
        return this.loadAlbum;
    }
    public final void setLoadAlbum(final int loadAlbum) {
        this.loadAlbum = loadAlbum;
    }

}


