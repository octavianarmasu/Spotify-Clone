package outputsandmediaplayer;

import artist.SongsToAdd;
import artist.ShowAlbums;
import users.AddUser;
import users.GetUsers;
import printcurrentpage.PrintPage;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.UserInput;
import fileio.input.EpisodeInput;
import fileio.input.LibraryInput;
import fileio.input.SongInput;
import fileio.input.PodcastInput;
import artist.Artist;
import host.ShowPodcasts;
import host.ResultForPodcast;
import podcast.Podcasts;
import podcast.Episode;
import playlist.Playlist;
import playlist.Result;
import playlist.Shuffle;
import playlist.GetTopPlaylist;
import playlist.ShowPlaylists;
import songs.ShowPreferredSongs;
import songs.GetTopSongs;
import songs.Song;
import artist.GetTopArtists;


import host.Host;

import artist.ResultForAlbum;
import artist.Album;
import fileio.input.CommandInput;
import users.User;

import java.text.ParseException;
import java.util.*;

public final class CommandRunner {
    static final int MAGICNUM = 5;
    static ArrayList<Artist> artists = new ArrayList<>();
    static ArrayList<Host> hosts = new ArrayList<>();
    static ArrayList<String> results = new ArrayList<>();
    static SelectOutput selectOutput = new SelectOutput();
    static MediaPlayer player = new MediaPlayer();

    private CommandRunner() {
    }

    /**
     * clears all the arrays
     */
    public static void setVariables() {
        artists.clear();
        hosts.clear();
        results.clear();
        player.setSong("");
        player.setEpisode("");
        player.setPlaylist(null);
        player.setTimeLeft(0);
    }

    /**
     * Solve the homework
     *
     * @param commands the list of commands
     * @param outputs  the list of outputs
     * @param library  the library
     * @throws ParseException in case of exceptions to parsing
     */
    public static void solution(final CommandInput[] commands, final ArrayNode outputs,
                                final LibraryInput library) throws ParseException {

        setVariables();
        ObjectMapper objectMapper = new ObjectMapper();
        ArrayList<Song> songs = new ArrayList<>();
        for (SongInput song : library.getSongs()) {
            songs.add(new Song(song));
        }

        ArrayList<User> users = new ArrayList<>();
        for (UserInput user : library.getUsers()) {
            users.add(new User(user.getUsername(), user.getAge(), user.getCity()));
        }
        for (User user : users) {
            user.clearLikedSongs();
        }

        ArrayList<Podcasts> podcasts = new ArrayList<>();
        for (PodcastInput podcast : library.getPodcasts()) {
            ArrayList<Episode> episodes = new ArrayList<>();
            for (EpisodeInput episode : podcast.getEpisodes()) {
                episodes.add(new Episode(episode));
            }
            podcasts.add(new Podcasts(podcast.getName(), podcast.getOwner(), episodes));
        }
        ArrayList<Playlist> playlists = new ArrayList<>();
        String currentPage;
        int searchArtist;
        int searchCount;
        int loadCheck;
        int loadSong;
        int searchPlaylist;
        int searchSong;
        int selectPlaylist;
        int searchPodcast;
        int loadPodcast;
        int loadPlaylist;
        int select;
        int searchAlbum;
        int loadAlbum;
        String connection;
        int searchHost;
        ArrayList<String> selectResult;
        User username = null;
        for (CommandInput command : commands) {
            if (command.getCommand().equals("addUser")) {
                AddUser addUser = new AddUser();
                addUser.setUser(command.getUsername());
                addUser.setTimestamp(command.getTimestamp());
                createUser(addUser, users, command);
                JsonNode addUserNode = objectMapper.valueToTree(addUser);
                outputs.add(addUserNode);
            }
            for (User user : users) {
                if (user.getUsername().equals(command.getUsername())) {
                    username = new User(user);
                }
            }
            if (username == null) {
                continue;
            }
            searchCount = username.getSearchCount();
            loadCheck = username.getLoadCheck();
            loadSong = username.getLoadSong();
            searchPlaylist = username.getSearchPlaylist();
            searchSong = username.getSearchSong();
            selectPlaylist = username.getSelectPlaylist();
            searchPodcast = username.getSearchPodcast();
            loadPodcast = username.getLoadPodcast();
            loadPlaylist = username.getLoadPlaylist();
            select = username.getSelect();
            connection = username.getConnection();
            currentPage = username.getCurrentPage();
            searchArtist = username.getSearchArtist();
            searchAlbum = username.getSearchAlbum();
            loadAlbum = username.getLoadAlbum();
            searchHost = username.getSearchHost();
            player = username.getMediaPlayer();
            selectResult = username.getResult();
            if (loadCheck == 1 && loadSong == 1 && player.getPlay() == 1
                    && connection.equals("online")) {
                Play.playSongs(songs, command.getTimestamp(), player);
                if (player.getSong() == null || player.getTimeLeft() == 0) {
                    loadCheck = 0;
                    loadSong = 0;
                }

            }
            if (loadPodcast == 1 && player.getPlay() == 1 && connection.equals("online")) {
                Play.playPodcasts(command.getTimestamp(), player);
            }
            if (loadCheck == 1 && loadPlaylist == 1
                    && player.getPlay() == 1 && connection.equals("online")) {
                Play.playPlaylist(songs, command.getTimestamp(), player);
                if (player.getPlaylist() == null || player.getTimeLeft() == 0) {
                    loadCheck = 0;
                    loadPlaylist = 0;
                }
            }
            if (loadAlbum == 1 && player.getPlay() == 1 && connection.equals("online")) {
                Play.playAlbum(command.getTimestamp(), player);
                if (player.getTimeLeft() == 0) {
                    loadCheck = 0;
                    loadAlbum = 0;
                    if (player.getShuffle() == 1) {
                        ArrayList<Song> aux = player.getOldAlbum().getSongs();
                        ArrayList<Song> songsCopy = new ArrayList<>(aux);
                        for (Artist artist : artists) {
                            for (Album album : artist.getAlbum()) {
                                if (album.getName().equals(player.getOldAlbum().getName())) {
                                    album.getSongs().clear();
                                    album.setSongs(songsCopy);
                                }
                            }
                        }
                    }
                }
            }
            if (command.getCommand().equals("search")) {
                results.clear();
                if (connection.equals("offline")) {
                    String message = command.getUsername() + " is offline.";
                    SearchFile searchFile = new SearchFile(command.getUsername(),
                            command.getTimestamp(), message, results);
                    JsonNode searchNode = objectMapper.valueToTree(searchFile);
                    outputs.add(searchNode);
                } else {
                    selectResult.clear();
                    searchArtist = 0;
                    searchAlbum = 0;
                    select = 0;
                    searchCount++;
                    searchPlaylist = 0;
                    selectPlaylist = 0;
                    searchPodcast = 0;
                    searchSong = 0;
                    loadCheck = 0;
                    loadSong = 0;
                    loadPodcast = 0;
                    loadAlbum = 0;
                    loadPlaylist = 0;
                    searchHost = 0;
                    player.setSong("");
                    if (command.getType().equals("song")) {
                        searchSong = 1;
                        searchSongs(songs, command);
                        results = checkSize();
                        String message = "Search returned " + results.size() + " results";
                        selectResult = results;
                        SearchFile searchFile = new SearchFile(command.getUsername(),
                                command.getTimestamp(), message, results);
                        JsonNode searchNode = objectMapper.valueToTree(searchFile);
                        outputs.add(searchNode);
                    }
                    if (command.getType().equals("podcast")) {
                        searchPodcast = 1;
                        for (Podcasts podcast : podcasts) {
                            podcast.verifyOwner(command, results);
                            podcast.verifyName(command, results);
                        }
                        results = checkSize();
                        String message = "Search returned " + results.size() + " results";
                        selectResult = results;
                        SearchFile searchFile = new SearchFile(command.getUsername(),
                                command.getTimestamp(), message, results);
                        JsonNode searchNode = objectMapper.valueToTree(searchFile);
                        outputs.add(searchNode);
                    }
                    if (command.getType().equals("playlist")) {
                        searchPlaylist = 1;
                        String owner = command.getFilters().getOwner();
                        for (User user : users) {
                            for (Playlist playlist : user.getPlaylists()) {
                                playlist.verifyName(command, results, user,
                                        command.getUsername());
                            }
                        }
                        if (owner != null) {
                            for (User user : users) {
                                if (user.getUsername().equals(owner)) {
                                    for (Playlist playlist : user.getPlaylists()) {
                                        if (!playlist.getVisibility().equals("private")) {
                                            results.add(playlist.getName());
                                        }
                                        if (playlist.getVisibility().equals("private")) {
                                            if (user.getUsername().equals(command.getUsername())) {
                                                results.add(playlist.getName());
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        results = checkSize();
                        String message = "Search returned " + results.size() + " results";
                        selectResult = results;
                        SearchFile searchFile = new SearchFile(command.getUsername(),
                                command.getTimestamp(), message, results);
                        JsonNode searchNode = objectMapper.valueToTree(searchFile);
                        outputs.add(searchNode);
                    }
                    if (command.getType().equals("artist")) {
                        searchArtist = 1;
                        for (User user : users) {
                            if (user.getUserType().equals("artist")) {
                                if (user.getUsername().startsWith(command.getFilters().getName())) {
                                    results.add(user.getUsername());
                                }
                            }
                        }
                        results = checkSize();
                        String message = "Search returned " + results.size() + " results";
                        selectResult = results;
                        SearchFile searchFile = new SearchFile(command.getUsername(),
                                command.getTimestamp(), message, results);
                        JsonNode searchNode = objectMapper.valueToTree(searchFile);
                        outputs.add(searchNode);
                    }
                    if (command.getType().equals("host")) {
                        searchHost = 1;
                        for (Host host : hosts) {
                            if (host.getUsername().startsWith(command.getFilters().getName())) {
                                results.add(host.getUsername());
                            }
                        }
                        results = checkSize();
                        String message = "Search returned " + results.size() + " results";
                        selectResult = results;
                        SearchFile searchFile = new SearchFile(command.getUsername(),
                                command.getTimestamp(), message, results);
                        JsonNode searchNode = objectMapper.valueToTree(searchFile);
                        outputs.add(searchNode);
                    }
                    if (command.getType().equals("album")) {
                        searchAlbum = 1;
                        conductSearchAlbum(command);
                        results = checkSize();
                        String message = "Search returned " + results.size() + " results";
                        selectResult = results;
                        SearchFile searchFile = new SearchFile(command.getUsername(),
                                command.getTimestamp(), message, results);
                        JsonNode searchNode = objectMapper.valueToTree(searchFile);
                        outputs.add(searchNode);
                    }
                }
            }

            if (command.getCommand().equals("select")) {
                int number = command.getItemNumber();
                selectOutput.setUser(command.getUsername());
                selectOutput.setTimestamp(command.getTimestamp());
                String message = "";
                if (searchCount == 0) {
                    message = "Please conduct a search before making a selection.";
                    selectOutput.setMessage(message);
                    selectOutput.setSuccessfulSelect(false);
                } else {
                    if (number > selectResult.size()) {
                        message = "The selected ID is too high.";
                        selectOutput.setMessage(message);
                        selectOutput.setSuccessfulSelect(false);

                    } else {
                        select = 1;
                        player.setPlay(0);
                        message = "Successfully selected " + selectResult.get(number - 1) + ".";
                        if (searchArtist == 1 || searchHost == 1) {
                            message = "Successfully selected " + selectResult.get(number - 1)
                                    + "'s page.";
                            currentPage = selectResult.get(number - 1);
                        }

                        selectOutput.setMessage(message);
                        selectOutput.setSuccessfulSelect(true);
                        if (searchSong == 1) {
                            chooseSong(number, songs, selectResult);
                        } else {
                            if (searchPlaylist == 1) {
                                choosePlaylist(number, users, songs, selectResult);
                                selectPlaylist = 1;
                            }
                            if (searchPodcast == 1) {
                                choosePodcast(number, podcasts, selectResult);
                            }
                            if (searchAlbum == 1) {
                                chooseAlbum(number, selectResult, users);
                            }
                        }
                        searchCount = 0;
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(selectOutput);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("load")) {
                String message = null;
                OutputClass loadOutput = new OutputClass();
                loadOutput.setCommand("load");
                loadOutput.setTimestamp(command.getTimestamp());
                loadOutput.setUser(command.getUsername());
                if (select == 0) {
                    message = "Please select a source before attempting to load.";
                    loadOutput.setMessage(message);
                } else {
                    if (selectOutput.successfulSelect == 0) {
                        message = "Please select a source before attempting to load.";
                        loadOutput.setMessage(message);
                    } else {
                        loadCheck = 1;
                        message = "Playback loaded successfully.";
                        select = 0;
                        if (searchSong == 1) {
                            loadSong = 1;
                        }
                        if (searchPodcast == 1) {
                            loadPodcast = 1;
                        }
                        if (searchPlaylist == 1) {
                            loadPlaylist = 1;
                        }
                        if (searchAlbum == 1) {
                            loadAlbum = 1;
                        }

                        loadOutput.setMessage(message);
                        player.setPlay(1);
                        player.setRepeat(0);
                        player.setShuffle(0);
                    }

                }
                JsonNode selectNode = objectMapper.valueToTree(loadOutput);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("playPause")) {
                OutputClass selectPlay = new OutputClass();
                String message = null;
                selectPlay.setCommand("playPause");
                selectPlay.setUser(command.getUsername());
                selectPlay.setTimestamp(command.getTimestamp());
                if (loadCheck == 1) {
                    if (player.getPlay() == 1) {
                        //must be paused
                        player.setPlay(0);
                        message = "Playback paused successfully.";
                        selectPlay.setMessage(message);

                    } else {
                        //must be played
                        player.setPlay(1);
                        message = "Playback resumed successfully.";
                        selectPlay.setMessage(message);
                    }
                }
                if (loadCheck == 0) {
                    message = "Please load a source before attempting to pause or resume playback";
                    selectPlay.setMessage(message);
                }
                if (player.getTimeLeft() == 0 && player.getTimeEpisode() == 0) {
                    message = "Please load a source before attempting"
                            + " to pause or resume playback.";
                    selectPlay.setMessage(message);
                }

                JsonNode selectNode = objectMapper.valueToTree(selectPlay);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("status")) {
                Status status = new Status();
                Stats stats = new Stats();
                status.setUser(command.getUsername());
                status.setTimestamp(command.getTimestamp());
                if (loadSong == 1) {
                    stats.setName(player.song);
                    stats.setRemainedTime(player.getTimeLeft());

                } else {
                    if (loadPodcast == 1) {
                        stats.setName(player.getEpisode());
                        stats.setRemainedTime(player.getTimeEpisode());
                    }
                    if (loadPlaylist == 1) {
                        stats.setName(player.song);
                        stats.setRemainedTime(player.getTimeLeft());
                    }
                    if (loadAlbum == 1) {
                        stats.setName(player.song);
                        stats.setRemainedTime(player.getTimeLeft());
                    }
                }
                if (player.getTimeLeft() == 0) {
                    player.setShuffle(0);
                }
                stats.changeRepeat(player.getRepeat(), loadSong, loadPodcast, loadPlaylist);
                stats.changeShuffle(player.getShuffle(), loadSong, loadPodcast,
                        loadPlaylist, loadAlbum);
                stats.setPaused(player.getPlay() != 1);
                if (loadCheck == 0) {
                    stats.setName("");
                    stats.setRemainedTime(0);
                    stats.setPaused(true);
                }
                status.setStats(stats);
                JsonNode selectNode = objectMapper.valueToTree(status);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("createPlaylist")) {
                int create = 1;
                OutputClass playlistOutput = new OutputClass();
                Playlist playlist = new Playlist();
                playlistOutput.setCommand("createPlaylist");
                playlistOutput.setUser(command.getUsername());
                playlistOutput.setTimestamp(command.getTimestamp());
                for (User user : users) {
                    if (user.getUsername().equals(command.getUsername())) {
                        playlist.setName(command.getPlaylistName());
                        for (Playlist userPlaylist : user.getPlaylists()) {
                            if (userPlaylist.getName().equals(playlist.getName())) {
                                playlistOutput.setMessage("A playlist with "
                                        + "the same name already exists.");
                                create = 0;
                            }
                        }
                        if (create == 1) {
                            playlist.setOwner(command.getUsername());
                            user.addPlaylist(playlist);
                            playlists.add(playlist);
                            playlistOutput.setMessage("Playlist created successfully.");
                            // oldPlaylist has the un-shuffled songs
                            ArrayList<String> changedSongs = user.changeOldPlaylist(playlist);
                            Playlist auxPlaylist = new Playlist(playlist.getName(),
                                    changedSongs, playlist.getVisibility(),
                                    playlist.getFollowers());
                            auxPlaylist.setOwner(playlist.getOwner());
                            user.addOldPlaylistUser(auxPlaylist);
                            user.getPlaylists().get(user.getPlaylists().size() - 1)
                                    .setTimestamp(command.getTimestamp());

                        }

                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(playlistOutput);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("addRemoveInPlaylist")) {
                OutputClass addRemovePlaylist = new OutputClass();
                addRemovePlaylist.setCommand("addRemoveInPlaylist");
                addRemovePlaylist.setUser(command.getUsername());
                addRemovePlaylist.setTimestamp(command.getTimestamp());
                if (loadCheck == 0) {
                    addRemovePlaylist.setMessage("Please load a source "
                            + "before adding to or removing from the playlist.");
                }
                if (loadSong == 0 && (loadPlaylist == 1 || loadPodcast == 1)) {
                    addRemovePlaylist.setMessage("The loaded source is not a song.");
                    break;
                }
                if (loadCheck == 1) {
                    for (User user : users) {
                        if (user.getUsername().equals(command.getUsername())) {
                            if (user.getPlaylists() != null
                                    && command.getPlaylistId() > user.getPlaylists().size()) {
                                addRemovePlaylist.setMessage("The specified "
                                        + "playlist does not exist.");
                            } else {

                                assert user.getPlaylists() != null;
                                int number = command.getPlaylistId() - 1;
                                Playlist playlist = user.getPlaylists().get(number);
                                Playlist oldPlaylist = user.getOldPlaylists().get(number);
                                //check if song is in playlist
                                if (playlist.getSongs().contains(player.getSong())) {
                                    playlist.removeSong(player.getSong());
                                    oldPlaylist.removeSong(player.getSong());
                                    addRemovePlaylist.setMessage("Successfully removed"
                                            + " from playlist.");
                                } else {
                                    playlist.addSong(player.getSong());
                                    oldPlaylist.addSong(player.getSong());
                                    addRemovePlaylist.setMessage("Successfully added "
                                            + "to playlist.");
                                }
                            }
                        }
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(addRemovePlaylist);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("like")) {
                OutputClass likedSongs = new OutputClass();
                likedSongs.setCommand("like");
                likedSongs.setUser(command.getUsername());
                likedSongs.setTimestamp(command.getTimestamp());
                if (connection.equals("offline")) {
                    likedSongs.setMessage(command.getUsername() + " is offline.");
                } else {
                    if (loadCheck == 0 || player.getSong().isEmpty()) {
                        likedSongs.setMessage("Please load a source before liking or unliking.");
                    } else {
                        if (player.getSong() == null) {
                            likedSongs.setMessage("Loaded source is not a song.");
                        }
                        if (loadCheck == 1) {
                            likedSongs.addLikedSongs(command, users, songs, player);
                        }
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(likedSongs);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("showPreferredSongs")) {
                ShowPreferredSongs preferredSongs = new ShowPreferredSongs();
                preferredSongs.setUser(command.getUsername());
                preferredSongs.setTimestamp(command.getTimestamp());
                for (User user : users) {
                    if (command.getUsername().equals(user.getUsername())) {
                        for (Song song : user.getLikedSongs()) {
                            preferredSongs.addResult(song.getName());
                        }
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(preferredSongs);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("showPlaylists")) {
                ShowPlaylists showPlaylist = new ShowPlaylists();
                showPlaylist.setUser(command.getUsername());
                showPlaylist.setTimestamp(command.getTimestamp());
                for (User user : users) {
                    if (user.getUsername().equals(command.getUsername())) {
                        for (Playlist playlist : user.getOldPlaylists()) {
                            Result result = new Result();
                            result.setName(playlist.getName());
                            result.setSongs(playlist.getSongs());
                            for (Playlist userPlaylist : user.getPlaylists()) {
                                if (userPlaylist.getName().equals(playlist.getName())) {
                                    result.setFollowers(userPlaylist.getFollowers());
                                }
                            }
                            result.setVisibility(playlist.getVisibility());
                            showPlaylist.addResult(result);

                        }
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(showPlaylist);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("repeat")) {
                Repeat repeat = new Repeat();
                repeat.setUser(command.getUsername());
                repeat.setTimestamp(command.getTimestamp());
                if (loadCheck == 0 || player.getSong().isEmpty()) {
                    repeat.setMessage("Please load a source before setting the repeat status.");
                } else {
                    player.addDelRepeat();
                    repeat.changeMessage(player.getRepeat(), loadPodcast, loadSong, loadPlaylist);
                }

                JsonNode selectNode = objectMapper.valueToTree(repeat);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("shuffle")) {
                Shuffle shuffle = new Shuffle();
                shuffle.setUser(command.getUsername());
                shuffle.setTimestamp(command.getTimestamp());
                player.addDelShuffle();
                shuffle.changeMessage(player.getShuffle(), loadCheck,
                        loadPlaylist, player, loadAlbum);
                if (player.getShuffle() == 1 && loadPlaylist == 1) {
                    assert player.getPlaylist() != null;
                    Playlist aux = player.getPlaylist();
                    player.getOldPlaylist().setSongs(player.changeOldPlaylist(aux));
                    player.getOldPlaylist().setName(player.getPlaylist().getName());
                    Collections.shuffle(player.getPlaylist().getSongs(),
                            new Random(command.getSeed()));
                    setSongNumber();
                } else {
                    if (loadPlaylist == 1 && player.getShuffle() == 0) {
                        assert player.getPlaylist() != null;
                        player.getPlaylist().setSongs(player.getOldPlaylist().getSongs());
                        setSongNumber();
                    }
                }
                if (loadAlbum == 1 && player.getShuffle() == 1) {
                    assert player.getAlbum() != null;
                    player.getOldAlbum().setSongs(player.changeOldAlbum(player.getAlbum()));
                    player.getOldAlbum().setName(player.getAlbum().getName());
                    Collections.shuffle(player.getAlbum().getSongs(),
                            new Random(command.getSeed()));
                    setSongNumberAlbum();
                } else {
                    if (loadAlbum == 1 && player.getShuffle() == 0) {
                        assert player.getAlbum() != null;
                        player.getAlbum().setSongs(player.getOldAlbum().getSongs());
                        setSongNumberAlbum();
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(shuffle);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("next")) {
                OutputClass next = new OutputClass();
                next.setCommand("next");
                next.setUser(command.getUsername());
                next.setTimestamp(command.getTimestamp());
                if (loadCheck == 0) {
                    next.setMessage("Please load a source before skipping to the next track.");
                } else {
                    player.setPlay(1);
                    player.skipToNext(next, songs, loadSong, loadPodcast, loadPlaylist, loadAlbum);
                }

                JsonNode selectNode = objectMapper.valueToTree(next);
                outputs.add(selectNode);

            }

            if (command.getCommand().equals("prev")) {
                OutputClass prev = new OutputClass();
                prev.setCommand("prev");
                prev.setUser(command.getUsername());
                prev.setTimestamp(command.getTimestamp());
                if (loadCheck == 0 || player.getSong().isEmpty()) {
                    prev.setMessage("Please load a source before returning"
                            + " to the previous track.");
                } else {
                    player.setPlay(1);
                    player.skipToPrev(prev, songs, loadSong, loadPodcast, loadPlaylist,
                            podcasts, loadAlbum);
                }
                JsonNode selectNode = objectMapper.valueToTree(prev);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("forward")) {
                OutputClass forward = new OutputClass();
                forward.setCommand("forward");
                forward.setUser(command.getUsername());
                forward.setTimestamp(command.getTimestamp());
                if (loadCheck == 0) {
                    forward.setMessage("Please load a source before attempting to forward.");
                } else {
                    if (loadPodcast == 0) {
                        forward.setMessage("The loaded source is not a podcast.");
                    } else {
                        player.skipForward(forward);
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(forward);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("backward")) {
                OutputClass backward = new OutputClass();
                backward.setCommand("backward");
                backward.setUser(command.getUsername());
                backward.setTimestamp(command.getTimestamp());
                if (loadCheck == 0) {
                    backward.setMessage("Please select a source before rewinding.");
                } else {
                    if (loadPodcast == 0) {
                        backward.setMessage("The loaded source is not a podcast.");
                    } else {
                        player.skipBackward(backward);
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(backward);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("follow")) {
                OutputClass follow = new OutputClass();
                follow.setCommand("follow");
                follow.setUser(command.getUsername());
                follow.setTimestamp(command.getTimestamp());
                if (select == 0) {
                    follow.setMessage("Please select a source before following or unfollowing.");
                } else {
                    if (selectPlaylist == 0) {
                        follow.setMessage("The selected source is not a playlist.");
                    } else {
                        followPlaylist(follow, command, users, playlists);
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(follow);
                outputs.add(selectNode);

            }

            if (command.getCommand().equals("getTop5Playlists")) {
                GetTopPlaylist topPlaylist = new GetTopPlaylist();
                topPlaylist.setTimestamp(command.getTimestamp());
                topPlaylist.showTop5Playlist(users);
                JsonNode selectNode = objectMapper.valueToTree(topPlaylist);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("getTop5Songs")) {
                GetTopSongs topSongs = new GetTopSongs();
                topSongs.setTimestamp(command.getTimestamp());
                topSongs.showTop5Songs(songs);
                JsonNode selectNode = objectMapper.valueToTree(topSongs);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("switchVisibility")) {
                OutputClass switchVis = new OutputClass();
                switchVis.setCommand("switchVisibility");
                switchVis.setUser(command.getUsername());
                switchVis.setTimestamp(command.getTimestamp());
                int number = command.getPlaylistId();
                for (User user : users) {
                    if (user.getUsername().equals(command.getUsername())) {
                        if (user.getPlaylists() != null && number
                                > user.getPlaylists().size()) {
                            switchVis.setMessage("The specified playlist ID is too high.");
                        } else {
                            assert user.getPlaylists() != null;
                            Playlist oldPLaylist = user.getOldPlaylists().get(number - 1);
                            Playlist playlist = user.getPlaylists().get(number - 1);
                            playlist.switchVisibility();
                            oldPLaylist.switchVisibility();
                            switchVis.setMessage("Visibility status updated successfully to "
                                    + playlist.getVisibility() + ".");
                        }
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(switchVis);
                outputs.add(selectNode);

            }
            if (command.getCommand().equals("switchConnectionStatus")) {
                OutputClass switchCon = new OutputClass();
                switchCon.setCommand("switchConnectionStatus");
                switchCon.setUser(command.getUsername());
                switchCon.setTimestamp(command.getTimestamp());
                switchCon.changeConnectionStatus(command, users);
                JsonNode selectNode = objectMapper.valueToTree(switchCon);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("getOnlineUsers")) {
                GetUsers onlineUsers = new GetUsers();
                onlineUsers.setTimestamp(command.getTimestamp());
                for (User user : users) {
                    if (user.getConnection().equals("online")
                            && user.getUserType().equals("user")) {
                        onlineUsers.addResult(user.getUsername());
                    }
                }
                JsonNode selectNode = objectMapper.valueToTree(onlineUsers);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("addAlbum")) {
                OutputClass addAlbum = new OutputClass();
                addAlbum.setCommand("addAlbum");
                addAlbum.setUser(command.getUsername());
                addAlbum.setTimestamp(command.getTimestamp());
                addAlbumFunc(users, command, addAlbum, songs);
                JsonNode selectNode = objectMapper.valueToTree(addAlbum);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("showAlbums")) {
                ShowAlbums showAlbums = new ShowAlbums();
                showAlbums.setTimestamp(command.getTimestamp());
                showAlbums.setUser(command.getUsername());
                addResultForAlbum(command, showAlbums);
                JsonNode selectNode = objectMapper.valueToTree(showAlbums);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("printCurrentPage")) {
                PrintPage printPage = new PrintPage();
                printPage.setUser(command.getUsername());
                printPage.setCommand("printCurrentPage");
                printPage.setTimestamp(command.getTimestamp());
                if (connection.equals("offline")) {
                    printPage.setMessage(command.getUsername() + " is offline.");
                } else {
                    printPage.printPageFunc(artists, hosts, command, users);
                }
                JsonNode selectNode = objectMapper.valueToTree(printPage);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("addEvent")) {
                OutputClass addEvent = new OutputClass();
                addEvent.setCommand("addEvent");
                addEvent.setUser(command.getUsername());
                addEvent.setTimestamp(command.getTimestamp());
                addEvent.addEventFunc(command, artists, users);
                JsonNode selectNode = objectMapper.valueToTree(addEvent);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("addMerch")) {
                OutputClass addMerch = new OutputClass();
                addMerch.setCommand("addMerch");
                addMerch.setUser(command.getUsername());
                addMerch.setTimestamp(command.getTimestamp());
                addMerch.addMerchFunc(command, artists, users);
                JsonNode selectNode = objectMapper.valueToTree(addMerch);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("getAllUsers")) {
                GetUsers getAllUsers = new GetUsers();
                getAllUsers.setCommand("getAllUsers");
                getAllUsers.setTimestamp(command.getTimestamp());
                for (User user : users) {
                    if (user.getUserType().equals("user")) {
                        getAllUsers.addResult(user.getUsername());
                    }
                }
                for (Artist artist : artists) {
                    getAllUsers.addResult(artist.getUsername());
                }
                for (Host host : hosts) {
                    getAllUsers.addResult(host.getUsername());
                }
                JsonNode selectNode = objectMapper.valueToTree(getAllUsers);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("deleteUser")) {
                OutputClass deleteUser = new OutputClass();
                deleteUser.setCommand("deleteUser");
                deleteUser.setUser(command.getUsername());
                deleteUser.setTimestamp(command.getTimestamp());
                deleteUserFunc(command, deleteUser, users, songs, podcasts);
                JsonNode selectNode = objectMapper.valueToTree(deleteUser);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("getTop5Albums")) {
                GetTopSongs topAlbums = new GetTopSongs();
                topAlbums.setCommand("getTop5Albums");
                topAlbums.setTimestamp(command.getTimestamp());
                topAlbums.showTop5Albums(artists);
                JsonNode selectNode = objectMapper.valueToTree(topAlbums);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("addPodcast")) {
                OutputClass addPodcast = new OutputClass();
                addPodcast.setCommand("addPodcast");
                addPodcast.setUser(command.getUsername());
                addPodcast.setTimestamp(command.getTimestamp());
                addPodcast.addPodcastFunc(command, hosts, users, podcasts);
                JsonNode selectNode = objectMapper.valueToTree(addPodcast);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("addAnnouncement")) {
                OutputClass addAnnouncement = new OutputClass();
                addAnnouncement.setCommand("addAnnouncement");
                addAnnouncement.setUser(command.getUsername());
                addAnnouncement.setTimestamp(command.getTimestamp());
                addAnnouncement.addAnnouncementFunc(command, hosts, users);
                JsonNode selectNode = objectMapper.valueToTree(addAnnouncement);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("showPodcasts")) {
                ShowPodcasts showPodcasts = new ShowPodcasts();
                showPodcasts.setUser(command.getUsername());
                showPodcasts.setTimestamp(command.getTimestamp());
                showPodcastsFunc(showPodcasts, command);
                JsonNode selectNode = objectMapper.valueToTree(showPodcasts);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("removeAnnouncement")) {
                OutputClass removeAnnouncement = new OutputClass();
                removeAnnouncement.setCommand("removeAnnouncement");
                removeAnnouncement.setUser(command.getUsername());
                removeAnnouncement.setTimestamp(command.getTimestamp());
                removeAnnouncement.removeAnnouncementFunc(command, hosts, users);
                JsonNode selectNode = objectMapper.valueToTree(removeAnnouncement);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("removeAlbum")) {
                OutputClass removeAlbum = new OutputClass();
                removeAlbum.setCommand("removeAlbum");
                removeAlbum.setUser(command.getUsername());
                removeAlbum.setTimestamp(command.getTimestamp());
                removeAlbumFunc(command, removeAlbum, users, songs);
                JsonNode selectNode = objectMapper.valueToTree(removeAlbum);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("changePage")) {
                OutputClass changePage = new OutputClass();
                changePage.setCommand("changePage");
                changePage.setUser(command.getUsername());
                changePage.setTimestamp(command.getTimestamp());
                if (command.getNextPage().equals("Home")
                        || command.getNextPage().equals("LikedContent")) {
                    currentPage = command.getNextPage();
                }
                changePageFunc(command, changePage, users);
                JsonNode selectNode = objectMapper.valueToTree(changePage);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("removePodcast")) {
                OutputClass removePodcast = new OutputClass();
                removePodcast.setCommand("removePodcast");
                removePodcast.setUser(command.getUsername());
                removePodcast.setTimestamp(command.getTimestamp());
                removePodcastFunc(command, removePodcast, users, podcasts);
                JsonNode selectNode = objectMapper.valueToTree(removePodcast);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("removeEvent")) {
                OutputClass removeEvent = new OutputClass();
                removeEvent.setCommand("removeEvent");
                removeEvent.setUser(command.getUsername());
                removeEvent.setTimestamp(command.getTimestamp());
                removeEventFunc(command, removeEvent, users);
                JsonNode selectNode = objectMapper.valueToTree(removeEvent);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("getTop5Artists")) {
                GetTopArtists topArtists = new GetTopArtists();
                topArtists.setTimestamp(command.getTimestamp());
                showTop5Artists(topArtists);
                JsonNode selectNode = objectMapper.valueToTree(topArtists);
                outputs.add(selectNode);
            }

            player.setTimestamp(command.getTimestamp());
            for (User user : users) {
                if (command.getUsername() != null) {
                    if (user.getUsername().equals(command.getUsername())) {
                        user.setSearchCount(searchCount);
                        user.setLoadCheck(loadCheck);
                        user.setLoadSong(loadSong);
                        user.setSearchPlaylist(searchPlaylist);
                        user.setSearchSong(searchSong);
                        user.setSelectPlaylist(selectPlaylist);
                        user.setSearchPodcast(searchPodcast);
                        user.setLoadPodcast(loadPodcast);
                        user.setLoadPlaylist(loadPlaylist);
                        user.setSelect(select);
                        user.setCurrentPage(currentPage);
                        user.setMediaPlayer(player);
                        user.setSearchAlbum(searchAlbum);
                        user.setSearchArtist(searchArtist);
                        user.setLoadAlbum(loadAlbum);
                        user.setSearchHost(searchHost);
                        user.setResult(selectResult);
                    }
                }
            }
        }
    }

    private static void showTop5Artists(final GetTopArtists topArtists) {
        ArrayList<Artist> resultArtist = new ArrayList<>();
        for (Artist artist : artists) {
            int likesForArtist = 0;
            for (Album album : artist.getAlbum()) {
                int likes = 0;
                for (Song song : album.getSongs()) {
                    if (song.getArtist().equals(album.getArtist())) {
                        likes += song.getLikes();
                    }
                }
                album.setTotalLikes(likes);
                likesForArtist += likes;
            }
            artist.setNrOfLikes(likesForArtist);
            resultArtist.add(artist);
        }
        resultArtist.sort(new Comparator<Artist>() {
            @Override
            public int compare(final Artist o1, final Artist o2) {
                return o2.getNrOfLikes() - o1.getNrOfLikes();
            }
        });
        if (resultArtist.size() > MAGICNUM) {
            for (int i = 0; i < MAGICNUM; i++) {
                topArtists.addResult(resultArtist.get(i).getUsername());
            }
        } else {
            for (Artist artist : resultArtist) {
                topArtists.addResult(artist.getUsername());
            }
        }

    }


    private static void removeEventFunc(final CommandInput command, final OutputClass removeEvent,
                                        final ArrayList<User> users) {
        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                if (!user.getUserType().equals("artist")) {
                    removeEvent.setMessage(command.getUsername() + " is not an artist.");
                    return;
                }
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(command.getUsername())) {
                found = 1;
                if (Check.checkEvent(artist, command) == 0) {
                    removeEvent.setMessage(command.getUsername()
                            + " doesn't have an event with the given name.");
                    break;
                }
                artist.removeEvent(command.getName());
                removeEvent.setMessage(command.getUsername()
                        + " deleted the event successfully.");
            }
        }
        if (found == 0) {
            removeEvent.setMessage("The username " + command.getUsername()
                    + " doesn't exist.");
        }
    }

    private static void removePodcastFunc(final CommandInput command,
                                          final OutputClass removePodcast,
                                          final ArrayList<User> users,
                                          final ArrayList<Podcasts> podcasts) {
        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                if (!user.getUserType().equals("host")) {
                    removePodcast.setMessage(command.getUsername() + " is not a host.");
                    return;
                }
            }
        }
        for (Host host : hosts) {
            if (host.getUsername().equals(command.getUsername())) {
                found = 1;
                if (Check.checkPodcast(host, command) == 0) {
                    removePodcast.setMessage(command.getUsername()
                            + " doesn't have a podcast with the given name.");
                    break;
                }
                if (checkPlayingPodcast(command, users) == 1) {
                    removePodcast.setMessage(command.getUsername()
                            + " can't delete this podcast.");
                    break;
                }
                host.removePodcast(command.getName());
                removePodcast.setMessage(command.getUsername()
                        + " deleted the podcast successfully.");
                for (Podcasts podcasts1 : podcasts) {
                    if (podcasts1.getName().equals(command.getName())) {
                        podcasts.remove(podcasts1);
                        break;
                    }
                }

            }
        }
        if (found == 0) {
            removePodcast.setMessage("The username " + command.getUsername()
                    + " doesn't exist.");
        }
    }

    private static int checkPlayingPodcast(final CommandInput command,
                                           final ArrayList<User> users) {
        for (User user : users) {
            if (user.getMediaPlayer() != null) {
                if (user.getMediaPlayer().getPodcast() != null) {
                    if (user.getLoadPodcast() == 1) {
                        player = user.getMediaPlayer();
                        Play.playPodcasts(command.getTimestamp(), player);
                    }
                    if (user.getMediaPlayer().getPodcast().getName().equals(command.getName())) {
                        return 1;
                    }
                }
            }
        }
        return 0;
    }

    private static void changePageFunc(final CommandInput command, final OutputClass changePage,
                                       final ArrayList<User> users) {
        for (User user : users) {
            if (command.getUsername().equals(user.getUsername())) {
                if (command.getNextPage().equals("Home")) {
                    changePage.setMessage(user.getUsername() + " accessed Home successfully.");
                } else {
                    if (command.getNextPage().equals("LikedContent")) {
                        changePage.setMessage(user.getUsername()
                                + " accessed LikedContent successfully.");
                    } else {
                        changePage.setMessage(user.getUsername()
                                + " is trying to access a non-existent page.");
                    }
                }
            }
        }
    }

    private static void removeAlbumFunc(final CommandInput command,
                                        final OutputClass removeAlbum,
                                        final ArrayList<User> users, final ArrayList<Song> songs) {
        int found = 0;
        User artistFound = null;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                if (!user.getUserType().equals("artist")) {
                    removeAlbum.setMessage(command.getUsername() + " is not an artist.");
                    return;
                } else {
                    artistFound = new User(user);
                }
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(command.getUsername())) {
                found = 1;
                if (Check.checkAlbum(artist, command) == 0) {
                    removeAlbum.setMessage(command.getUsername()
                            + " doesn't have an album with the given name.");
                    break;
                }

                if (checkPlaying(command, users, songs, artistFound) == 1) {
                    removeAlbum.setMessage(command.getUsername()
                            + " can't delete this album.");
                    break;
                }
                artist.removeAlbum(command.getName());
                removeAlbum.setMessage(command.getUsername()
                        + " deleted the album successfully.");
            }
        }
        if (found == 0) {
            removeAlbum.setMessage("The username " + command.getUsername()
                    + " doesn't exist.");
        }

    }

    private static void showPodcastsFunc(final ShowPodcasts showPodcasts,
                                         final CommandInput command) {
        for (Host host : hosts) {
            if (host.getUsername().equals(command.getUsername())) {
                for (Podcasts podcast : host.getPodcasts()) {
                    ResultForPodcast result = new ResultForPodcast();
                    result.setName(podcast.getName());
                    for (Episode episode : podcast.getEpisodes()) {
                        result.addEpisode(episode.getName());
                    }
                    showPodcasts.addResult(result);
                }
            }
        }
    }
    private static void chooseAlbum(final int number, final ArrayList<String> selectResult,
                                    final ArrayList<User> users) {

        String albumName = selectResult.get(number - 1);
        int select = 0;
        for (Artist artist : artists) {
            for (Album album : artist.getAlbum()) {
                if (album.getName().equals(albumName)) {
                    for (User user : users) {
                        if (user.getMediaPlayer() != null
                                && user.getMediaPlayer().getOldAlbum() != null
                                && user.getMediaPlayer().getOldAlbum().getName() != null) {
                            if (user.getMediaPlayer().getOldAlbum().getName().equals(albumName)) {
                                album.getSongs().clear();
                                album.setSongs(user.getMediaPlayer().getOldAlbum().getSongs());
                            }

                        }
                    }
                }
            }
        }

        for (Artist artist : artists) {
            for (Album album : artist.getAlbum()) {
                if (album.getName().equals(albumName)) {
                    player.setAlbum(album);
                    player.setSong(album.getSongs().get(0).getName());
                    player.setSongNumber(0);
                    player.setTimeLeft(album.getSongs().get(0).getDuration());
                    player.setArtist(artist.getUsername());
                    select++;
                    if (select == number) {
                        return;
                    }
                }
            }
        }
    }

    private static void conductSearchAlbum(final CommandInput command) {
        int numFilters = Filters.verifyFiltersForAlbum(command);
        for (Artist artist : artists) {
            artist.verifyAll(command, results, numFilters);
        }
    }

    private static User searchUser(final CommandInput command, final ArrayList<User> users) {
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                return new User(user);
            }
        }
        return null;
    }

    private static void deleteUserFunc(final CommandInput command,
                                       final OutputClass deleteUser, final ArrayList<User> users,
                                       final ArrayList<Song> songs,
                                       final ArrayList<Podcasts> podcasts) {
        User user = searchUser(command, users);
        if (user == null) {
            deleteUser.setMessage("The username " + command.getUsername() + " doesn't exist.");
            return;
        }
        if (user.getUserType().equals("user")) {
            deleteUser.setMessage(user.getUsername() + " was successfully deleted.");
            if (user.getPlaylists() != null) {
                if (checkPlaylist(users, user, command, songs) == 1) {
                    deleteUser.setMessage(user.getUsername() + " can't be deleted.");
                    return;
                }
                for (User user1 : users) {
                    for (Playlist playlist : user.getPlaylists()) {
                        user1.getFollowedPlaylists().remove(playlist);
                    }
                }
                if (user.getFollowedPlaylists() != null) {
                    for (Playlist playlist : user.getFollowedPlaylists()) {
                        for (User user1 : users) {
                            if (user1.getPlaylists().contains(playlist)) {
                                for (Playlist playlist1 : user1.getPlaylists()) {
                                    if (playlist1.equals(playlist)) {
                                        playlist1.setFollowers(playlist1.getFollowers() - 1);
                                    }
                                }
                            }
                        }
                    }
                }
            }
            users.remove(user);
            return;
        }
        if (user.getUserType().equals("artist")) {
            if (checkPlaying(command, users, songs, user) == 1) {
                deleteUser.setMessage(user.getUsername() + " can't be deleted.");
                return;
            }
            if (Check.checkPage(users, user) == 1) {
                deleteUser.setMessage(user.getUsername() + " can't be deleted.");
                return;
            }
            for (Artist artist : artists) {
                if (artist.getUsername().equals(command.getUsername())) {
                    deleteUser.setMessage(user.getUsername() + " was successfully deleted.");
                    users.remove(user);
                    artists.remove(artist);
                    break;
                }
            }
            Iterator<Song> iterator = songs.iterator();
            while (iterator.hasNext()) {
                Song song = iterator.next();
                if (song.getArtist().equals(command.getUsername())) {
                    for (User user1 : users) {
                        user1.getLikedSongs().remove(song);
                    }
                    iterator.remove();
                }
            }
        }
        if (user.getUserType().equals("host")) {
            if (Check.checkPlayingHost(command, users, podcasts) == 1) {
                deleteUser.setMessage(user.getUsername() + " can't be deleted.");
                return;
            }
            if (Check.checkPage(users, user) == 1) {
                deleteUser.setMessage(user.getUsername() + " can't be deleted.");
                return;
            }
            deleteUser.setMessage(user.getUsername() + " was successfully deleted.");
            users.remove(user);
            for (Host host : hosts) {
                if (host.getUsername().equals(command.getUsername())) {
                    hosts.remove(host);
                    break;
                }
            }
            podcasts.removeIf(podcast -> podcast.getOwner().equals(command.getUsername()));
        }

    }

    /**
     * checks if a user is playing the playlist of a specific user
     * @param users the list of users in the database
     * @param owner the owner of the playlist
     * @param command the command input
     * @param songs the list of songs in the database
     * @return 1 if the user is playing the playlist, 0 otherwise
     */
    public static int checkPlaylist(final ArrayList<User> users, final User owner,
                                    final CommandInput command, final ArrayList<Song> songs) {

        for (User user : users) {
            if (user.getMediaPlayer() != null) {
                if (user.getMediaPlayer().getSong() != null) {
                    if (user.getLoadPlaylist() == 1) {
                        player = user.getMediaPlayer();
                        if (player.getPlay() == 1) {
                            Play.playPlaylist(songs, command.getTimestamp(), player);
                        }
                        if (player.getPlaylist() != null) {
                            for (Playlist playlist : owner.getPlaylists()) {
                                if (user.getMediaPlayer().getPlaylist().getName() != null) {
                                    Playlist aux = user.getMediaPlayer().getPlaylist();
                                    if (aux.getName().equals(playlist.getName())) {
                                        return 1;
                                    }
                                }
                            }
                        } else {
                            user.setLoadCheck(0);
                            user.setLoadPlaylist(0);
                        }
                    }
                }
            }
        }
        return 0;
    }

    /**
     * checks if a user is playing a song from a specific artist,
     * album from a specific artist or playlist that may contain
     * songs from a specific artist
     *
     * @param command where we have the username of the artist
     * @param users   the list of users in the database
     * @param songs   the list of songs in the database
     * @param artist  the artist to be verified
     * @return 1 if the user is playing a song from the artist, 0 otherwise
     */
    private static int checkPlaying(final CommandInput command,
                                    final ArrayList<User> users, final ArrayList<Song> songs,
                                    final User artist) {
        for (User user : users) {
            if (user.getMediaPlayer() != null) {
                if (user.getMediaPlayer().getSong() != null) {
                    if (user.getLoadAlbum() == 1) {
                        player = user.getMediaPlayer();
                        Play.playAlbum(command.getTimestamp(), player);
                    }
                    if (user.getLoadSong() == 1) {
                        player = user.getMediaPlayer();
                        Play.playSongs(songs, command.getTimestamp(), player);
                    }
                    if (user.getLoadPlaylist() == 1
                            && user.getMediaPlayer().getPlaylist() != null) {
                        player = user.getMediaPlayer();
                        Play.playPlaylist(songs, command.getTimestamp(), player);
                    }
                    for (Song song : songs) {
                        if (user.getMediaPlayer().getSong().equals(song.getName())) {
                            if (song.getArtist().equals(artist.getUsername())) {
                                return 1;
                            }
                        }
                    }
                }
            }
        }
        return 0;
    }

    private static void addResultForAlbum(final CommandInput command,
                                          final ShowAlbums showAlbums) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(command.getUsername())) {
                for (Album album : artist.getAlbum()) {
                    ResultForAlbum result = new ResultForAlbum();
                    result.setName(album.getName());
                    for (Song song : album.getSongs()) {
                        result.addSong(song.getName());
                    }
                    showAlbums.addResult(result);
                }
            }
        }
    }

    private static void addAlbumFunc(final ArrayList<User> users,
                                     final CommandInput command, final OutputClass addAlbum,
                                     final ArrayList<Song> songs) {
        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                if (!user.getUserType().equals("artist")) {
                    addAlbum.setMessage(user.getUsername() + " is not an artist.");
                    break;
                }
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(command.getUsername())) {
                found = 1;
                ArrayList<Song> songForAlbum = new ArrayList<>();
                for (SongsToAdd song : command.getSongs()) {
                    Song aux = new Song(song);
                    songs.add(aux);
                    songForAlbum.add(aux);
                }
                int checkAlbum = Check.checkDuplicateAlbum(artist, command);
                if (checkAlbum == 0) {
                    addAlbum.setMessage(command.getUsername()
                            + " has another album with the same name.");
                    break;
                }
                int checkSongs = Check.checkSongsForAlbum(songForAlbum);
                if (checkSongs == 0) {
                    addAlbum.setMessage(command.getUsername()
                            + " has the same song at least twice in this album.");
                    break;
                }
                Album album = new Album(command.getName(), command.getReleaseYear(),
                        command.getDescription(), songForAlbum, artist.getUsername());
                artist.addAlbum(album);
                addAlbum.setMessage(artist.getUsername()
                        + " has added new album successfully.");
            }
        }
        if (found == 0) {
            addAlbum.setMessage("The username " + command.getUsername() + " doesn't exist.");
        }
    }

    private static void createUser(final AddUser addUser, final ArrayList<User> users,
                                   final CommandInput command) {

        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                addUser.setMessage("The username " + command.getUsername()
                        + " is already taken.");
            }
        }
        if (found == 0) {
            User createdUser = new User(command.getUsername(),
                    command.getAge(), command.getCity());
            users.add(createdUser);
            createdUser.setUserType(command.getType());
            if (command.getType().equals("artist")) {
                Artist artist = new Artist();
                artists.add(artist);
                artist.setUsername(command.getUsername());
            }
            if (command.getType().equals("host")) {
                Host host = new Host();
                hosts.add(host);
                host.setUsername(command.getUsername());
            }
            addUser.setMessage("The username " + command.getUsername()
                    + " has been added successfully.");
        }
    }

    private static void choosePodcast(final int number, final ArrayList<Podcasts> podcasts,
                                      final ArrayList<String> selectResult) {
        for (Podcasts podcast : podcasts) {
            if (podcast.getName().equals(selectResult.get(number - 1))) {
                if (player.getPodcast() != null) {
                    if (!player.getPodcast().getName().equals(podcast.getName())) {
                        player.setPodcast(podcast);
                        player.setEpisode(podcast.getEpisodes().get(0).getName());
                        player.setEpisodeNumber(0);
                        player.setTimeEpisode(podcast.getEpisodes().get(0).getDuration());
                    }
                } else {
                    player.setPodcast(podcast);
                    player.setEpisode(podcast.getEpisodes().get(0).getName());
                    player.setEpisodeNumber(0);
                    player.setTimeEpisode(podcast.getEpisodes().get(0).getDuration());
                }
            }
        }
    }

    private static void chooseSong(final int number, final ArrayList<Song> songs,
                                   final ArrayList<String> selectResult) {
        player.setSong(selectResult.get(number - 1));
        int select = 0;
        for (Song song : songs) {
            if (song.getName().equals(selectResult.get(number - 1))) {
                player.setTimeLeft(song.getDuration());
                player.setArtist(song.getArtist());
                select++;
                if (select == number) {
                    return;
                }
            }
        }
    }

    private static void choosePlaylist(final int number, final ArrayList<User> users,
                                       final ArrayList<Song> songs,
                                       final ArrayList<String> selectResult) {
        int found = 0;
        for (User user : users) {
            for (Playlist playlist : user.getPlaylists()) {
                String aux = selectResult.get(number - 1);
                if (player.getOldPlaylist().getName() != null) {
                    if (player.getOldPlaylist().getName().equals(aux)) {
                        playlist = player.getOldPlaylist();
                        playlist.setSongs(player.getOldPlaylist().getSongs());
                    }
                }
                if (playlist.getName().equals(aux)) {
                    player.setPlaylist(playlist);
                    player.getPlaylist().setName(playlist.getName());
                    if (!playlist.getSongs().isEmpty()) {
                        player.setSong(playlist.getSongs().get(0));
                        player.setSongNumber(0);
                        for (Song song : songs) {
                            String song1 = playlist.getSongs().get(0);
                            if (found != number
                                    && song.getName().equals(song1)) {
                                player.setTimeLeft(song.getDuration());
                                player.setArtist(song.getArtist());
                                found++;
                            }
                        }
                    }
                }
            }
        }
    }

    /**
     * adds or removes a playlist from the followed playlists list
     *
     * @param follow    output class
     * @param command   command input
     * @param users     arraylist of users
     * @param playlists arraylist of playlists
     */
    private static void followPlaylist(final OutputClass follow, final CommandInput command,
                                       final ArrayList<User> users,
                                       final ArrayList<Playlist> playlists) {

        if (player.getPlaylist() != null) {
            for (User user : users) {
                if (user.getUsername().equals(command.getUsername())) {
                    for (Playlist playlist : user.getPlaylists()) {
                        if (playlist.getName().equals(player.getPlaylist().getName())) {
                            follow.setMessage("You cannot follow or unfollow your own playlist.");
                            return;
                        }
                    }
                    if (player.getPlaylist().getVisibility().equals("private")) {
                        follow.setMessage("Please select a source before"
                                + " following or unfollowing.");
                        return;
                    }
                    if (user.getFollowedPlaylists().contains(player.getPlaylist())) {
                        user.getFollowedPlaylists().remove(player.getPlaylist());
                        follow.setMessage("Playlist unfollowed successfully.");
                        for (Playlist playlist : playlists) {
                            String aux = player.getPlaylist().getName();
                            if (playlist.getName().equals(aux)) {
                                playlist.removeFollower();
                            }
                        }
                        return;
                    }
                    user.addFollowedPlaylist(player.getPlaylist());
                    follow.setMessage("Playlist followed successfully.");
                    for (Playlist playlist : playlists) {
                        if (playlist.getName().equals(player.getPlaylist().getName())) {
                            playlist.plusFollower();
                        }
                    }
                }
            }
        }
    }

    /**
     * changes the length of the result array to 5
     */
    public static ArrayList<String> checkSize() {
        if (results.size() > MAGICNUM) {
            return new ArrayList<String>(results.subList(0, MAGICNUM));
        }
        return results;
    }

    /**
     * searches for songs that match the filters
     *
     * @param songs   arraylist of songs
     * @param command command input
     */
    public static void searchSongs(final ArrayList<Song> songs, final CommandInput command) {
        int numFilters = Filters.verifyMultipleForSongs(command);
        for (Song song : songs) {
            song.verifyAll(command, results, numFilters);
        }

    }

    /**
     * sets the song number of the song is currently playing
     * from a playlist
     */
    public static void setSongNumber() {
        for (int i = 0; i < player.getPlaylist().getSongs().size(); i++) {
            if (player.getPlaylist().getSongs().get(i).equals(player.getSong())) {
                player.setSongNumber(i);
            }
        }
    }

    /**
     * sets the song number of the song is currently playing
     * from an album
     */
    public static void setSongNumberAlbum() {
        for (int i = 0; i < player.getAlbum().getSongs().size(); i++) {
            if (player.getAlbum().getSongs().get(i).getName().equals(player.getSong())) {
                player.setSongNumber(i);
            }
        }
    }
}
