package main;

import Artist.SongsToAdd;
import Artist.ShowAlbums;
import Events.Event;
import OnlineUsers.AddUser;
import OnlineUsers.GetUsers;
import PrintCurrentPage.PrintPage;
import checker.Checker;
import checker.CheckerConstants;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import com.fasterxml.jackson.databind.node.ArrayNode;
import fileio.input.*;
import Artist.Artist;
import java.io.File;
import Artist.Merch;

import Artist.ResultForAlbum;
import Artist.Album;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


/**
 * The entry point to this homework. It runs the checker that tests your implentation.
 */
public final class Main {
    static final String LIBRARY_PATH = CheckerConstants.TESTS_PATH + "library/library.json";
    static final int MAGIGNUM = 5;
    static final int PODCASTNUM = 90;
    static ArrayList<String> selectResult = new ArrayList<>();
    static ArrayList<Artist> artists = new ArrayList<>();
    static ArrayList<String> results = new ArrayList<>();
    static SelectOutput selectOutput = new SelectOutput();
    static MediaPlayer player = new MediaPlayer();

    /**
     * for coding style
     */
    private Main() {
    }

    /**
     * DO NOT MODIFY MAIN METHOD
     * Call the checker
     *
     * @param args from command line
     * @throws IOException in case of exceptions to reading / writing
     */
    public static void main(final String[] args) throws IOException, ParseException {
        File directory = new File(CheckerConstants.TESTS_PATH);
        Path path = Paths.get(CheckerConstants.RESULT_PATH);

        if (Files.exists(path)) {
            File resultFile = new File(String.valueOf(path));
            for (File file : Objects.requireNonNull(resultFile.listFiles())) {
                file.delete();
            }
            resultFile.delete();
        }
        Files.createDirectories(path);
        for (File file : Objects.requireNonNull(directory.listFiles())) {
            if (file.getName().startsWith("library")) {
                continue;
            }

            String filepath = CheckerConstants.OUT_PATH + file.getName();
            File out = new File(filepath);
            boolean isCreated = out.createNewFile();
            if (isCreated) {
                action(file.getName(), filepath);
            }
        }

        Checker.calculateScore();
    }

    /**
     * @param filePathInput  for input file
     * @param filePathOutput for output file
     * @throws IOException in case of exceptions to reading / writing
     */
    public static <SelectFile> void action(final String filePathInput,
                                           final String filePathOutput) throws IOException, ParseException {
        ObjectMapper objectMapper = new ObjectMapper();
        LibraryInput library = objectMapper.readValue(new File(LIBRARY_PATH), LibraryInput.class);
        String filePath = CheckerConstants.TESTS_PATH;
        ArrayNode outputs = objectMapper.createArrayNode();
        filePath = filePath + filePathInput;

        CommandInput[] commands = objectMapper.readValue(new File(filePath), CommandInput[].class);
        player.setPlaylist(null);
        player.setSong("");
        player.setEpisode("");
        artists.clear();


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
        String userType;
        String previousCommand = null;
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
            player = username.getMediaPlayer();
            if (loadCheck == 1 && loadSong == 1 && player.getPlay() == 1
                    && connection.equals("online")) {
                playSongs(songs, command.getTimestamp());
                if (player.getSong() == null) {
                    loadCheck = 0;
                    loadSong = 0;
                }

            }
            if (loadPodcast == 1 && player.getPlay() == 1 && connection.equals("online")) {
                loadPodcasts(command.getTimestamp());
            }
            if (loadPlaylist == 1 && player.getPlay() == 1 && connection.equals("online")) {
                playPlaylist(songs, command.getTimestamp());
                if (player.getPlaylist() == null || player.getTimeLeft() == 0) {
                    loadCheck = 0;
                    loadPlaylist = 0;
                }
            }
            if (loadAlbum == 1 && player.getPlay() == 1 && connection.equals("online")) {
                playAlbum(command.getTimestamp());
                if (player.getPlaylist() == null || player.getTimeLeft() == 0) {
                    loadCheck = 0;
                    loadAlbum = 0;
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
                    loadPlaylist = 0;
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
                                playlist.verifyName(command, results, user, command.getUsername());
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
                        for (User user:users) {
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
                        player.setRepeat(0);
                        message = "Successfully selected " + selectResult.get(number - 1) + ".";
                        if (searchArtist == 1) {
                            message = "Successfully selected " + selectResult.get(number - 1)
                                    + "'s page.";
                            currentPage = selectResult.get(number - 1);
                        }

                        selectOutput.setMessage(message);
                        selectOutput.setSuccessfulSelect(true);
                        if (searchSong == 1) {
                            chooseSong(number, songs);
                        } else {
                            if (searchPlaylist == 1) {
                                choosePlaylist(number, users, playlists, songs);
                                selectPlaylist = 1;
                            }
                            if (searchPodcast == 1) {
                                choosePodcast(number, podcasts);
                            }
                            if (searchAlbum == 1) {
                                chooseAlbum(number, artists);
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
                assert previousCommand != null;
                if (select == 0) {
                    message = "Please select a source before attempting to load.";
                    loadOutput.setMessage(message);
                    player.setRepeat(0);
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
                } else {
                    message = "Please load a source before attempting to pause or resume playback";
                    selectPlay.setMessage(message);
                }
                if (player.getTimeLeft() == 0) {
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
                }
                if (player.getTimeLeft() == 0) {
                    player.setShuffle(0);
                }
                stats.changeRepeat(player.getRepeat(), loadSong, loadPodcast, loadPlaylist);
                stats.changeShuffle(player.getShuffle(), loadSong, loadPodcast, loadPlaylist);
                if (loadCheck == 0) {
                    player.setPlay(0);
                    player.setTimeLeft(0);
                    player.setSong("");
                    stats.setName(player.song);
                    stats.setRemainedTime(player.getTimeLeft());
                }
                stats.setPaused(player.getPlay() != 1);
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
                            user.addPlaylist(playlist);
                            playlists.add(playlist);
                            playlistOutput.setMessage("Playlist created successfully.");
                            // oldPlaylist has the un-shuffled songs
                            ArrayList<String> changedSongs = user.changeOldPlaylist(playlist);
                            Playlist auxPlaylist = new Playlist(playlist.getName(),
                                    changedSongs, playlist.getVisibility(),
                                    playlist.getFollowers());
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
                }
                if (loadCheck == 1 && loadSong == 1) {
                    for (User user : users) {
                        if (user.getUsername().equals(command.getUsername())) {
                            if (user.getPlaylists() != null && command.getPlaylistId() > user.getPlaylists().size()) {
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
                        if (loadSong == 0) {
                            likedSongs.setMessage("Loaded source is not a song.");
                        }
                        if ((loadSong == 1 || loadPlaylist == 1) && loadCheck == 1) {
                            addLikedSong(likedSongs, command, users, songs);
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
                        preferredSongs.setResult(user.getLikedSongs());
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
                shuffle.changeMessage(player.getShuffle(), loadCheck, loadPlaylist, player);
                if (player.getShuffle() == 1 && player.getPlaylist() != null) {
                    player.getOldPlaylist().setSongs(player.changeOldPlaylist(player.getPlaylist()));
                    Collections.shuffle(player.getPlaylist().getSongs(),
                            new Random(command.getSeed()));
                    setSongNumber();
                } else {
                    if (loadCheck == 1 && selectPlaylist == 1) {
                        assert player.getPlaylist() != null;
                        player.getPlaylist().setSongs(player.getOldPlaylist().getSongs());
                        setSongNumber();
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
                    skipToNext(next, songs, loadSong, loadPodcast, loadPlaylist);
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
                    skipToPrev(prev, songs, loadSong, loadPodcast, loadPlaylist,
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
                        skipForward(forward);
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
                        skipBackward(backward);
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
                showTop5Playlist(topPlaylist, users);
                JsonNode selectNode = objectMapper.valueToTree(topPlaylist);
                outputs.add(selectNode);
            }

            if (command.getCommand().equals("getTop5Songs")) {
                GetTopSongs topSongs = new GetTopSongs();
                topSongs.setTimestamp(command.getTimestamp());
                showTop5Songs(topSongs, songs);
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
                changeConnectionStatus(switchCon, command, users);
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
                AddAlbumFunc(users, command, addAlbum, songs);
                JsonNode selectNode = objectMapper.valueToTree(addAlbum);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("showAlbums")) {
                ShowAlbums showAlbums = new ShowAlbums();
                showAlbums.setTimestamp(command.getTimestamp());
                showAlbums.setUser(command.getUsername());
                ResultForAlbum result = new ResultForAlbum();
                addResultForAlbum(command, result, showAlbums);
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
                    printPageFunc(printPage, command, users);
                }
                JsonNode selectNode = objectMapper.valueToTree(printPage);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("addEvent")) {
                OutputClass addEvent = new OutputClass();
                addEvent.setCommand("addEvent");
                addEvent.setUser(command.getUsername());
                addEvent.setTimestamp(command.getTimestamp());
                addEventFunc(command, addEvent, users);
                JsonNode selectNode = objectMapper.valueToTree(addEvent);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("addMerch")) {
                OutputClass addMerch = new OutputClass();
                addMerch.setCommand("addMerch");
                addMerch.setUser(command.getUsername());
                addMerch.setTimestamp(command.getTimestamp());
                addMerchFunc(command, addMerch, users);
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
                JsonNode selectNode = objectMapper.valueToTree(getAllUsers);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("deleteUser")) {
                OutputClass deleteUser = new OutputClass();
                deleteUser.setCommand("deleteUser");
                deleteUser.setUser(command.getUsername());
                deleteUser.setTimestamp(command.getTimestamp());
                deleteUserFunc(command, deleteUser, users, songs,filePathInput);
                JsonNode selectNode = objectMapper.valueToTree(deleteUser);
                outputs.add(selectNode);
            }
            if (command.getCommand().equals("getTop5Albums")) {
                GetTopSongs topAlbums = new GetTopSongs();
                topAlbums.setCommand("getTop5Albums");
                topAlbums.setTimestamp(command.getTimestamp());
                showTop5Albums(topAlbums);
                JsonNode selectNode = objectMapper.valueToTree(topAlbums);
                outputs.add(selectNode);
            }

            previousCommand = command.getCommand();
            player.setTimestamp(command.getTimestamp());
            for (User user : users) {
                if (user.getUsername().equals(username.getUsername())) {
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
                }
            }
        }


        ObjectWriter objectWriter = objectMapper.writerWithDefaultPrettyPrinter();
        objectWriter.writeValue(new File(filePathOutput), outputs);
    }

    private static void showTop5Albums(final GetTopSongs topAlbums) {
        ArrayList<Album> albumsToSort = new ArrayList<>();
        for (Artist artist : artists) {
            for (Album album : artist.getAlbum()) {
                int likes = 0;
                for (Song song : album.getSongs()) {
                    likes += song.getLikes();
                }
                album.setTotalLikes(likes);
                albumsToSort.add(album);
            }
        }
        albumsToSort.sort(new Comparator<Album>() {
            @Override
            public int compare(final Album o1, final Album o2) {
                return o2.getTotalLikes() - o1.getTotalLikes();
            }
        });
        if (albumsToSort.size() < 5) {
            for (Album album : albumsToSort) {
                topAlbums.addResult(album.getName());
            }
        } else {
            for (int i = 0; i < 5; i++) {
                topAlbums.addResult(albumsToSort.get(i).getName());
            }
        }

    }

    private static void playAlbum(final int timestamp) {
        if (player.getTimeLeft() > 0 ) {
            int time = timestamp - player.getTimestamp();
            player.delTime(time);
        }
        if (player.getTimeLeft() <= 0) {
            int remainedTime = player.getTimeLeft() * (-1);
            player.nextSong();
            if (player.getAlbum().getSongs().size() - 1 >= player.getSongNumber()) {
                player.setSong(player.getAlbum().getSongs()
                        .get(player.getSongNumber()).getName());
                player.setTimeLeft(player.getAlbum().getSongs().get(player.getSongNumber())
                        .getDuration() - remainedTime);
            }
            while (player.getTimeLeft() < 0) {
                remainedTime = player.getTimeLeft() * (-1);
                player.nextSong();
                if (player.getAlbum().getSongs().size() - 1 >= player.getSongNumber()) {
                    player.setSong(player.getAlbum().getSongs()
                            .get(player.getSongNumber()).getName());
                    player.setTimeLeft(player.getAlbum().getSongs().get(player.getSongNumber())
                            .getDuration() - remainedTime);
                } else {
                    player.setTimeLeft(0);
                    player.setSong("");
                    player.setPlay(0);
                    break;
                }
            }
        }
    }

    private static void chooseAlbum(final int number,final ArrayList<Artist> artists) {
        for (Artist artist : artists) {
            for (Album album : artist.getAlbum()) {
                String aux = selectResult.get(number - 1);
                if (album.getName().equals(aux)) {
                    player.setAlbum(album);
                    player.getAlbum().setName(album.getName());
                    player.setSong(album.getSongs().get(0).getName());
                    player.setSongNumber(0);
                    player.setTimeLeft(album.getSongs().get(0).getDuration());
                }
            }
        }
    }

    private static void conductSearchAlbum(final CommandInput command) {
        int numFilters = verifyFiltersForAlbum(command);
        for (Artist artist: artists) {
            artist.verifyAll(command,results, numFilters);
        }
    }

    private static int verifyFiltersForAlbum(final CommandInput command) {
        int numFilters = 0;
        if (command.getFilters().getOwner() != null) {
            numFilters++;
        }
        if (command.getFilters().getName() != null) {
            numFilters++;
        }
        if (command.getFilters().getDescription() != null) {
            numFilters++;
        }
        return  numFilters;
    }
    private static User SearchUser(final CommandInput command, final ArrayList<User> users) {
        for (User user: users) {
            if (user.getUsername().equals(command.getUsername())) {
                return new User(user);
            }
        }
        return null;
    }
    private static void deleteUserFunc(final CommandInput command,
                                       final OutputClass deleteUser, final ArrayList<User> users,
                                       final ArrayList<Song> songs, String filepath) {
        User user = SearchUser(command, users);
        if (user == null) {
            deleteUser.setMessage("The username " + command.getUsername() + " doesn't exist.");
            return;
        }
        if (user.getUserType().equals("user")) {
            deleteUser.setMessage(user.getUsername() + " was successfully deleted.");
            users.remove(user);
            return;
        }
        if (user.getUserType().equals("artist")) {
            if (checkPlaying(command, users, songs, user,filepath) == 1) {
                deleteUser.setMessage(user.getUsername() + " can't be deleted.");
                return;
            }
            deleteUser.setMessage(user.getUsername() + " was successfully deleted.");
            users.remove(user);
            for (Artist artist : artists) {
                if (artist.getUsername().equals(command.getUsername())) {
                    artists.remove(artist);
                    break;
                }
            }
            Iterator<Song> iterator = songs.iterator();
            while (iterator.hasNext()) {
                Song song = iterator.next();
                if (song.getArtist().equals(command.getUsername())) {
                    for (User user1 : users) {
                        user1.getLikedSongs().remove(song.getName());
                    }
                    iterator.remove();
                }
            }
         }

    }

    private static int checkPlaying(final CommandInput command,
                                    ArrayList<User> users, final ArrayList<Song> songs,
                                    final User artist, String filepath) {
        for (User user : users) {
            if (user.getMediaPlayer() != null) {
                if (user.getMediaPlayer().getSong() != null) {
                    if (user.getLoadAlbum() == 1) {
                        player = user.getMediaPlayer();
                        playAlbum(command.getTimestamp());
                    }
                    if (user.getLoadSong() == 1) {
                        player = user.getMediaPlayer();
                        playSongs(songs, command.getTimestamp());
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

    private static void addMerchFunc(final CommandInput command, final OutputClass addMerch,
                                     final ArrayList<User> users) {
        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                if (!user.getUserType().equals("artist")) {
                    addMerch.setMessage(user.getUsername() + " is not an artist.");
                    break;
                }
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(command.getUsername())) {
                found = 1;
                int checkMerch = checkDuplicateMerch(artist, command);
                if (checkMerch == 0) {
                    addMerch.setMessage(artist.getUsername()
                            + " has merchandise with the same name.");
                    break;
                }
                if (command.getPrice() < 0) {
                    addMerch.setMessage("Price for merchandise can not be negative.");
                    break;
                }
                Merch merch = new Merch(command.getName(), command.getPrice(),
                        command.getDescription());
                artist.addMerch(merch);
                addMerch.setMessage(command.getUsername()
                        + " has added new merchandise successfully.");
            }
        }
        if (found == 0) {
            addMerch.setMessage("The username " + command.getUsername() + " doesn't exist.");
        }
    }

    private static int checkDuplicateMerch(final Artist artist, final CommandInput command) {
        for (Merch merch : artist.getMerch()) {
            if (merch.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }

    private static void addEventFunc(final CommandInput command, final OutputClass addEvent,
                                     final ArrayList<User> users) throws ParseException {
        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                if (!user.getUserType().equals("artist")) {
                    addEvent.setMessage(user.getUsername() + " is not an artist.");
                    break;
                }
            }
        }
        for (Artist artist : artists) {
            if (artist.getUsername().equals(command.getUsername())) {
                found = 1;
                int checkEvent = checkDuplicateEvent(artist, command);
                if (checkEvent == 0) {
                    addEvent.setMessage(artist.getUsername()
                            + " has another event with the same name.");
                    break;
                }
                int checkDate = checkDate(command);
                if (checkDate == 0) {
                    addEvent.setMessage("Event for " + command.getUsername()
                            + " does not have a valid date.");
                    break;
                }
                Event event = new Event(command.getName(), command.getDescription(),
                        command.getDate());
                artist.addEvent(event);
                addEvent.setMessage(command.getUsername()
                        + " has added new event successfully.");
            }
        }
        if (found == 0) {
            addEvent.setMessage("The username " + command.getUsername() + " doesn't exist.");
        }
    }

    /**
     * check if the date is valid
     * @param command the command that contains the event
     * @return 1 if is valid, 0 otherwise
     */
    private static int checkDate(final CommandInput command) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        Date date = format.parse(command.getDate());
        int year = Integer.parseInt(command.getDate().substring(6));
        int month = Integer.parseInt(command.getDate().substring(3, 5));
        int day = Integer.parseInt(command.getDate().substring(0, 2));
        if (year > 2023 || year < 1900) {
            return 0;
        }
        if (month > 12 || month < 1) {
            return 0;
        }
        if (month == 2) {
            if (day > 28 || day < 1) {
                return 0;
            }
        } else {
            if (day > 31 || day < 1) {
                return 0;
            }
        }
        return 1;
    }

    /**
     * check for duplicate events
     * @param artist the artist that wants to add the event
     * @param command the command that contains the event
     * @return 1 if the event is not duplicate, 0 otherwise
     */
    private static int checkDuplicateEvent(final Artist artist, final CommandInput command) {
        for (Event event : artist.getEvents()) {
            if (event.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
    }

    private static void printPageFunc(final PrintPage printPage,
                                      final CommandInput command, final ArrayList<User> users) {


        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                if (user.getCurrentPage().equals("home")
                        ||user.getCurrentPage().equals("LikedPage")) {
                    printHomePage(printPage, user);
                } else {
                    printArtistHostPage(printPage, user);
                }
            }
        }
    }

    private static void printArtistHostPage(final PrintPage printPage,final  User user) {

        for (Artist artist : artists) {
            if (user.getCurrentPage().equals(artist.getUsername())) {
                printPage.setMessage("Albums:\n\t[");
                for (int i = 0; i < artist.getAlbum().size(); i++) {
                    if (i == artist.getAlbum().size() - 1) {
                        printPage.setMessage(printPage.getMessage()
                                + artist.getAlbum().get(i).getName() + "]\n\n");
                        break;
                    }
                    printPage.setMessage(printPage.getMessage()
                            + artist.getAlbum().get(i).getName() + ", ");
                }

                if (artist.getMerch().isEmpty()) {
                    printPage.setMessage(printPage.getMessage() + "Merch:\n\t[]\n\n");
                } else {
                    printPage.setMessage(printPage.getMessage() + "Merch:\n\t[");
                    for (int i = 0; i < artist.getMerch().size(); i++) {
                        if (i == artist.getMerch().size() - 1) {
                            printPage.setMessage(printPage.getMessage()
                                    + artist.getMerch().get(i).getName() + " - "
                                    + artist.getMerch().get(i).getPrice() + ":\n\t"
                                    + artist.getMerch().get(i).getDescription() + "]\n\n");
                            break;
                        }
                        printPage.setMessage(printPage.getMessage()
                                + artist.getMerch().get(i).getName() + " - "
                                + artist.getMerch().get(i).getPrice() + ":\n\t"
                                + artist.getMerch().get(i).getDescription() + ", ");
                    }
                }
                printPage.setMessage(printPage.getMessage() + "Events:\n\t[");
                for (int i = 0; i < artist.getEvents().size(); i++) {
                    if (i == artist.getEvents().size() - 1) {
                        printPage.setMessage(printPage.getMessage()
                                + artist.getEvents().get(i).getName() + " - "
                                + artist.getEvents().get(i).getDate() + ":\n\t");
                        printPage.setMessage(printPage.getMessage()
                                + artist.getEvents().get(i).getDescription() + "]");
                        break;
                    }
                    printPage.setMessage(printPage.getMessage()
                            + artist.getEvents().get(i).getName() + " - "
                            + artist.getEvents().get(i).getDate() + ":\n\t");
                    printPage.setMessage(printPage.getMessage()
                            + artist.getEvents().get(i).getDescription() + ", ");
                }
            }
        }
    }

    private static void printHomePage(final PrintPage printPage, final User user) {
        printPage.setMessage("Liked songs:\n\t[");
        for (int i = 0; i < user.getLikedSongs().size(); i++) {
            if (i == user.getLikedSongs().size() - 1) {
                printPage.setMessage(printPage.getMessage() + user.getLikedSongs().get(i));
                break;
            }
            printPage.setMessage(printPage.getMessage()
                    + user.getLikedSongs().get(i) + ", ");
        }
        printPage.setMessage(printPage.getMessage()
                + "]\n\nFollowed playlists:\n\t[");
        for (Playlist playlist : user.getPlaylists()) {
            printPage.setMessage(printPage.getMessage() + playlist.getName() + ", ");
        }
        printPage.setMessage(printPage.getMessage() + "]");
    }

    private static void addResultForAlbum(final CommandInput command, ResultForAlbum result,
                                          final ShowAlbums showAlbums) {
        for (Artist artist : artists) {
            if (artist.getUsername().equals(command.getUsername())) {
                for (Album album : artist.getAlbum()) {
                    result = new ResultForAlbum();
                    result.setName(album.getName());
                    for (Song song : album.getSongs()) {
                        result.addSong(song.getName());
                    }
                    showAlbums.addResult(result);
                }
            }
        }
    }

    private static void AddAlbumFunc(final ArrayList<User> users,
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
                int checkAlbum = checkDuplicateAlbum(artist, command);
                if (checkAlbum == 0) {
                    addAlbum.setMessage(command.getUsername()
                            + " has another album with the same name.");
                    break;
                }
                int checkSongs = checkSongsForAlbum(songForAlbum);
                if (checkSongs == 0) {
                    addAlbum.setMessage(command.getUsername()
                            + " has the same song at least twice in this album.");
                    break;
                }
                Album album = new Album(command.getName(), command.getReleaseYear(),
                        command.getDescription(), songForAlbum);
                artist.addAlbum(album);
                addAlbum.setMessage(artist.getUsername()
                        + " has added new album successfully.");
            }
        }
        if (found == 0) {
            addAlbum.setMessage("The username " + command.getUsername() + " doesn't exist.");
        }
    }

    private static int checkSongsForAlbum(final ArrayList<Song> songForAlbum) {
        for (int i = 0; i < songForAlbum.size() - 1; i++) {
            for (int j = i + 1; j < songForAlbum.size(); j++) {
                if (songForAlbum.get(i).getName().equals(songForAlbum.get(j).getName())) {
                    return 0;
                }
            }
        }
        return 1;
    }

    private static int checkDuplicateAlbum(final Artist artist, final CommandInput command) {
        if (artist.getAlbum() == null) {
            return 1;
        }
        for (Album album : artist.getAlbum()) {
            if (album.getName().equals(command.getName())) {
                return 0;
            }
        }
        return 1;
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
            addUser.setMessage("The username " + command.getUsername()
                    + " has been added successfully.");
        }
    }

    private static void changeConnectionStatus(final OutputClass switchCon,
                                               final CommandInput command,
                                               final ArrayList<User> users) {

        int found = 0;
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                found = 1;
                if (user.getUserType().equals("user")) {
                    user.changeConnection();
                    switchCon.setMessage(user.getUsername() + " has changed status successfully.");
                } else {
                    switchCon.setMessage(user.getUsername() + " is not a normal user.");
                }
            }
        }
        if (found == 0) {
            switchCon.setMessage("The username " + command.getUsername() + " doesn't exist.");
        }
    }

    private static void choosePodcast(final int number, final ArrayList<Podcasts> podcasts) {
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

    private static void chooseSong(final int number, final ArrayList<Song> songs) {
        player.setSong(selectResult.get(number - 1));
        for (Song song : songs) {
            if (song.getName().equals(selectResult.get(number - 1))) {
                player.setTimeLeft(song.getDuration());
            }
        }
    }

    private static void choosePlaylist(final int number, final ArrayList<User> users,
                                       final ArrayList<Playlist> playlists,
                                       final ArrayList<Song> songs) {
        int found = 0;
        for (User user : users) {
            for (Playlist playlist : user.getPlaylists()) {
                String aux = selectResult.get(number - 1);
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
                                found++;
                            }
                        }
                    }
                }
            }
        }
    }

    private static void showTop5Songs(final GetTopSongs topSongs,
                                      final ArrayList<Song> songs) {
        List<Song> copySong = new ArrayList<>(songs);
        copySong.sort(Comparator.comparingInt(Song::getLikes).reversed());

        if (copySong.size() > MAGIGNUM) {
            copySong = copySong.subList(0, MAGIGNUM);
        }
        for (Song song : copySong) {
            topSongs.addResult(song.getName());
        }
    }

    private static void showTop5Playlist(final GetTopPlaylist topPlaylist,
                                         final ArrayList<User> users) {
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
        if (copyPlaylist.size() > MAGIGNUM) {
            copyPlaylist1 = copyPlaylist.subList(0, MAGIGNUM);
        }
        for (Playlist playlist : copyPlaylist1) {
            topPlaylist.addResult(playlist.getName());
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
                    if (user.getPlaylists().contains(player.getPlaylist())) {
                        follow.setMessage("You cannot follow or unfollow your own playlist.");
                    } else {
                        if (player.getPlaylist().getVisibility().equals("private")) {
                            follow.setMessage("Please select a source before"
                                    + " following or unfollowing.");
                        } else {
                            if (user.getFollowedPlaylists().contains(player.getPlaylist())) {
                                user.getFollowedPlaylists().remove(player.getPlaylist());
                                follow.setMessage("Playlist unfollowed successfully.");
                                for (Playlist playlist : playlists) {
                                    String aux = player.getPlaylist().getName();
                                    if (playlist.getName().equals(aux)) {
                                        playlist.removeFollower();
                                    }
                                }
                            } else {
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
            }
        }
    }

    /**
     * adds or removes a song from the liked songs list
     *
     * @param likedSongs output class
     * @param command    command input
     * @param users      arraylist of users
     * @param songs      arraylist of songs
     */
    private static void addLikedSong(final OutputClass likedSongs, final CommandInput command,
                                     final ArrayList<User> users, final ArrayList<Song> songs) {
        for (User user : users) {
            if (user.getUsername().equals(command.getUsername())) {
                if (user.getLikedSongs().contains(player.getSong())) {
                    user.getLikedSongs().remove(player.getSong());
                    likedSongs.setMessage("Unlike registered successfully.");
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            song.removeLike();
                        }
                    }
                } else {
                    user.addLikedSong(player.getSong());
                    likedSongs.setMessage("Like registered successfully.");
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            song.addLike();
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
        if (results.size() > MAGIGNUM) {
            return new ArrayList<String>(results.subList(0, MAGIGNUM));
        }
        return results;
    }

    /**
     * plays playlist
     *
     * @param songs arraylist of songs
     * @param currentTimestamp current timestamp
     */
    public static void playPlaylist(final ArrayList<Song> songs, final int currentTimestamp) {
        if (player.getTimeLeft() > 0) {
            int time = currentTimestamp - player.getTimestamp();
            player.delTime(time);
        }
        if (player.getTimeLeft() <= 0) {
            if (player.getRepeat() == 0) {
                int remainedTime = player.getTimeLeft() * (-1);
                player.nextSong();
                if (player.getPlaylist().getSongs().size() - 1 >= player.getSongNumber()) {
                    player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                        }
                    }
                    player.delTime(remainedTime);
                    while (player.getTimeLeft() < 0) {
                        remainedTime = player.getTimeLeft() * (-1);
                        player.nextSong();
                        if (player.getPlaylist().getSongs().size() - 1 >= player.getSongNumber()) {
                            String aux = player.getPlaylist().getSongs().get(player.getSongNumber());
                            player.setSong(aux);
                            for (Song song : songs) {
                                if (song.getName().equals(player.getSong())) {
                                    player.setTimeLeft(song.getDuration());
                                }
                            }
                            player.delTime(remainedTime);
                        } else {
                            player.setSong("");
                            player.setTimeLeft(0);
                            player.setPlay(0);
                            player.setPlaylist(null);
                        }
                    }

                } else {
                    player.setSong("");
                    player.setTimeLeft(0);
                    player.setPlay(0);
                    player.setPlaylist(null);
                }
            }
            if (player.getRepeat() == 1) {
                int remainedTime = player.getTimeLeft() * (-1);
                player.nextSong();
                if (player.getSongNumber() == player.getPlaylist().getSongs().size()) {
                    player.setSongNumber(0);
                    String aux = player.getPlaylist().getSongs().get(player.getSongNumber());
                    player.setSong(aux);
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                        }
                    }
                    player.delTime(remainedTime);
                } else {

                    player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                        }
                    }
                    player.delTime(remainedTime);
                    while (player.getTimeLeft() <= 0) {
                        int remainedTime1 = player.getTimeLeft() * (-1);
                        player.nextSong();
                        if (player.getPlaylist().getSongs().size() - 1 >= player.getSongNumber()) {
                            player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                            for (Song song : songs) {
                                if (song.getName().equals(player.getSong())) {
                                    player.setTimeLeft(song.getDuration());
                                }
                            }
                            player.delTime(remainedTime1);
                        } else {
                            player.setSongNumber(0);
                            String aux = player.getPlaylist().getSongs().get(player.getSongNumber());
                            player.setSong(aux);
                            for (Song song : songs) {
                                if (song.getName().equals(player.getSong())) {
                                    player.setTimeLeft(song.getDuration());
                                }
                            }
                            player.delTime(remainedTime1);
                        }
                    }
                }
            }

            if (player.getRepeat() == 2) {
                int remainedTime = player.getTimeLeft() * (-1);
                player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                for (Song song : songs) {
                    if (song.getName().equals(player.getSong())) {
                        player.setTimeLeft(song.getDuration());
                    }
                }
                player.delTime(remainedTime);
                while (player.getTimeLeft() <= 0) {
                    int remainedTime1 = player.getTimeLeft() * (-1);
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                        }
                    }
                    player.delTime(remainedTime1);
                }

            }
        }
    }

    /**
     * plays songs
     *
     * @param songs            arraylist of songs
     * @param currentTimestamp current timestamp
     */
    public static void playSongs(final ArrayList<Song> songs, final int currentTimestamp) {

        if (player.getTimeLeft() > 0) {
            int time = currentTimestamp - player.getTimestamp();
            player.delTime(time);
        }
        if (player.getTimeLeft() <= 0) {
            if (player.getRepeat() == 0) {
                player.setPlay(0);
                player.setTimeLeft(0);
                player.setSong("");
            }
            if (player.getRepeat() == 1) {
                int remainedTime = player.getTimeLeft() * (-1);
                player.setSong(player.getSong());
                for (Song song : songs) {
                    if (song.getName().equals(player.getSong())) {
                        player.setTimeLeft(song.getDuration());
                    }
                }
                player.setRepeat(0);
                player.delTime(remainedTime);
            }
            if (player.getRepeat() == 2) {
                int remainedTime = player.getTimeLeft() * (-1);
                player.setSong(player.getSong());
                for (Song song : songs) {
                    if (song.getName().equals(player.getSong())) {
                        player.setTimeLeft(song.getDuration());
                    }
                }
                player.delTime(remainedTime);
                while (player.getTimeLeft() <= 0) {
                    int remainedTime1 = player.getTimeLeft() * (-1);
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                        }
                    }
                    player.delTime(remainedTime1);
                }
            }

        }
    }

    /**
     * plays podcasts
     *
     * @param currentTimestamp current timestamp
     */
    public static void loadPodcasts(final int currentTimestamp) {

        if (player.getTimeEpisode() > 0) {
            int time = currentTimestamp - player.getTimestamp();
            player.delTimeEpisode(time);
        }
        if (player.getTimeEpisode() <= 0) {
            int remainedTime = player.getTimeEpisode() * (-1);
            player.nextEpisode();
            if (player.getPodcast().getEpisodes().get(player.getEpisodeNumber()) != null) {
                int aux2 = player.getEpisodeNumber();
                String aux1 = player.getPodcast().getEpisodes().get(aux2).getName();
                player.setEpisode(aux1);
                int aux3 = player.getPodcast().getEpisodes().get(aux2).getDuration();
                player.setTimeEpisode(aux3);
                player.delTimeEpisode(remainedTime);
            } else {
                player.setEpisode("");
                player.setTimeEpisode(0);
                player.setPlay(0);
                player.setPodcast(null);
            }
        }
    }

    /**
     * plays songs
     *
     * @param songs   arraylist of songs
     * @param command command input
     */
    public static void searchSongs(final ArrayList<Song> songs, final CommandInput command) {
        int numFilters = verifyMultiple(command);
        for (Song song : songs) {
            song.verifyAll(command, results, numFilters);
        }

    }

    /**
     * calculates the number of filters
     *
     * @param command the command input
     * @return the number of filters
     */

    private static int verifyMultiple(final CommandInput command) {
        int numFilters = 0;
        if (command.getFilters().getName() != null) {
            numFilters++;
        }
        if (command.getFilters().getArtist() != null) {
            numFilters++;
        }
        if (command.getFilters().getAlbum() != null) {
            numFilters++;
        }
        if (command.getFilters().getGenre() != null) {
            numFilters++;
        }
        if (command.getFilters().getLyrics() != null) {
            numFilters++;
        }
        if (command.getFilters().getReleaseYear() != null) {
            numFilters++;
        }
        if (command.getFilters().getTags() != null) {
            numFilters++;
        }
        return numFilters;
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

    private static void skipToNext(final OutputClass next, final ArrayList<Song> songs,
                                   final int loadsong, final int loadpodcast,
                                   final int loadPlaylist) {
        if (loadsong == 1) {
            if (player.getRepeat() == 0) {
                next.setMessage("Please load a source before skipping to the next track.");
                player.setPlay(0);
                player.setTimeLeft(0);
                player.setSong("");
            } else {
                if (player.getRepeat() == 1) {
                    player.setRepeat(0);
                }
                for (Song song : songs) {
                    if (song.getName().equals(player.getSong())) {
                        player.setTimeLeft(song.getDuration());
                    }
                }
                next.setMessage("Skipped to next track successfully. "
                        + "The current track is " + player.getSong() + ".");
            }
        }
        if (loadPlaylist == 1) {
            if (player.getRepeat() != 2) {
                player.nextSong();
                if (player.getPlaylist().getSongs().size() - 1 >= player.getSongNumber()) {
                    player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                        }
                    }
                    next.setMessage("Skipped to next track successfully. "
                            + "The current track is " + player.getSong() + ".");
                } else {
                    if (player.getRepeat() == 0) {
                        next.setMessage("Please load a source before skipping to the next track.");
                        player.setSong("");
                        player.setTimeLeft(0);
                        player.setPlay(0);

                    }
                    if (player.getRepeat() == 1) {
                        player.setSongNumber(0);
                        player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                        for (Song song : songs) {
                            if (song.getName().equals(player.getSong())) {
                                player.setTimeLeft(song.getDuration());
                            }
                        }
                        next.setMessage("Skipped to next track successfully."
                                + " The current track is " + player.getSong() + ".");
                    }
                }
            } else {
                for (Song song : songs) {
                    if (song.getName().equals(player.getSong())) {
                        player.setTimeLeft(song.getDuration());
                    }
                }
                next.setMessage("Skipped to next track successfully. "
                        + "The current track is " + player.getSong() + ".");
            }
        }

        if (loadpodcast == 1) {
            player.nextEpisode();
            if (player.getPodcast().getEpisodes().get(player.getEpisodeNumber()) != null) {
                int aux1 = player.getEpisodeNumber();
                String aux2 = player.getPodcast().getEpisodes().get(aux1).getName();
                player.setEpisode(aux2);
                player.setTimeEpisode(player.getPodcast().getEpisodes().get(player.getEpisodeNumber()).getDuration());
                next.setMessage("Skipped to next track successfully."
                        + " The current track is " + player.getEpisode() + ".");
            } else {
                if (player.getRepeat() == 0) {
                    next.setMessage("Please load a source before skipping to the next track.");
                    player.setEpisode("");
                    player.setTimeEpisode(0);
                    player.setPlay(0);
                    player.setPodcast(null);
                }
                if (player.getRepeat() == 1) {
                    next.setMessage("Skipped to next track successfully."
                            + " The current track is " + player.getEpisode() + ".");
                    player.setRepeat(0);
                    player.setEpisodeNumber(0);
                    int aux1 = player.getEpisodeNumber();
                    String aux2 = player.getPodcast().getEpisodes().get(aux1).getName();
                    player.setEpisode(aux2);
                    player.setTimeEpisode(player.getPodcast().getEpisodes().get(player.getEpisodeNumber()).getDuration());
                }
                if (player.getRepeat() == 2) {
                    next.setMessage("Skipped to next track successfully. "
                            + "The current track is " + player.getEpisode() + ".");
                    player.setEpisodeNumber(0);
                    int aux1 = player.getEpisodeNumber();
                    String aux2 = player.getPodcast().getEpisodes().get(aux1).getName();
                    player.setEpisode(aux2);
                    int duration = player.getPodcast().getEpisodes().get(aux1).getDuration();
                    player.setTimeEpisode(duration);
                }
            }
        }
    }

    private static void skipToPrev(final OutputClass prev, final ArrayList<Song> songs,
                                   final int loadsong, final int loadpodcast,
                                   final int loadPlaylist, final ArrayList<Podcasts> podcasts,
                                   final int loadAlbum) {
        if (loadsong == 1) {
            int duration;
            for (Song song : songs) {
                if (song.getName().equals(player.getSong())) {
                    duration = song.getDuration();
                    if (player.getTimeLeft() < duration) {
                        player.setTimeLeft(duration);
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + player.getSong() + ".");
                    }
                }
            }
        }
        if (loadPlaylist == 1) {
            int duration;
            player.prevSong();
            for (Song song : songs) {
                if (song.getName().equals(player.getSong())) {
                    duration = song.getDuration();
                    if (player.getTimeLeft() < duration) {
                        player.setTimeLeft(duration);
                        player.nextSong();
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + player.getSong() + ".");
                    } else {
                        if (player.getSongNumber() < 0) {
                            player.setSongNumber(0);
                        }
                        int num = player.getSongNumber();
                        String songAux = player.getPlaylist().getSongs().get(num);
                        player.setSong(songAux);
                        for (Song song1 : songs) {
                            if (song1.getName().equals(player.getSong())) {
                                player.setTimeLeft(song1.getDuration());
                            }
                        }
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + player.getSong() + ".");
                    }
                }
            }
        }

        if (loadpodcast == 1) {
            int duration;
            for (Podcasts podcast : podcasts) {
                if (player.getPodcast().getName().equals(podcast.getName())) {
                    duration = player.getPodcast().getEpisodes().get(player.getEpisodeNumber()).getDuration();
                    if (player.getTimeEpisode() < duration) {
                        player.setTimeEpisode(duration);
                        prev.setMessage("Returned to previous track successfully. "
                                + "The current track is " + player.getEpisode() + ".");
                    } else {
                        if (duration == player.getTimeEpisode() && player.getEpisodeNumber() != 0) {
                            player.prevEpisode();
                            int num = player.getEpisodeNumber();
                            String episodeAux = player.getPodcast().getEpisodes().get(num).getName();
                            player.setEpisode(episodeAux);
                            player.setTimeEpisode(player.getPodcast().getEpisodes().get(num).getDuration());
                            prev.setMessage("Returned to previous track successfully. "
                                    + "The current track is " + player.getEpisode() + ".");
                        } else {
                            if (duration == player.getTimeEpisode()) {
                                prev.setMessage("Returned to previous track successfully. "
                                        + "The current track is " + player.getEpisode() + ".");
                            }
                        }
                    }
                }
            }
        }
        if (loadAlbum == 1) {
            if (player.getTimeLeft() <
                    player.getAlbum().getSongs().get(player.getSongNumber()).getDuration()) {
                player.setTimeLeft(player.getAlbum().getSongs()
                        .get(player.getSongNumber()).getDuration());
                prev.setMessage("Returned to previous track successfully. "
                        + "The current track is " + player.getSong() + ".");
            } else {
                if (player.getSongNumber() != 0) {
                    player.prevSong();
                    int num = player.getSongNumber();
                    String songAux = player.getAlbum().getSongs().get(num).getName();
                    player.setSong(songAux);
                    player.setTimeLeft(player.getAlbum().getSongs().get(num).getDuration());
                    prev.setMessage("Returned to previous track successfully. "
                            + "The current track is " + player.getSong() + ".");
                } else {
                    prev.setMessage("Returned to previous track successfully. "
                            + "The current track is " + player.getSong() + ".");
                }
            }
        }
    }

    /**
     * skips forward in a podcast (+90sec)
     *
     * @param forward output class
     */

    private static void skipForward(final OutputClass forward) {
        int timeLeft = player.getTimeEpisode();
        if (timeLeft < PODCASTNUM) {
            player.nextEpisode();
            forward.setMessage("Skipped forward successfully.");
            if (player.getPodcast().getEpisodes().get(player.getEpisodeNumber()) != null) {
                int aux1 = player.getEpisodeNumber();
                String aux2 = player.getPodcast().getEpisodes().get(aux1).getName();
                player.setEpisode(aux2);
                player.setTimeEpisode(player.getPodcast().getEpisodes().get(aux1).getDuration());
            } else {
                if (player.getRepeat() == 0) {
                    player.setEpisode("");
                    player.setTimeEpisode(0);
                    player.setPlay(0);
                    player.setPodcast(null);
                }
                if (player.getRepeat() == 1) {
                    player.setRepeat(0);
                    player.setEpisodeNumber(0);
                    int aux1 = player.getEpisodeNumber();
                    String aux2 = player.getPodcast().getEpisodes().get(aux1).getName();
                    player.setEpisode(aux2);
                    int duration = player.getPodcast().getEpisodes().get(aux1).getDuration();
                    player.setTimeEpisode(duration);
                }
                if (player.getRepeat() == 2) {
                    player.setEpisodeNumber(0);
                    int aux1 = player.getEpisodeNumber();
                    String aux2 = player.getPodcast().getEpisodes().get(aux1).getName();
                    player.setEpisode(aux2);
                    int duration = player.getPodcast().getEpisodes().get(aux1).getDuration();
                    player.setTimeEpisode(duration);
                }
            }

        } else {
            forward.setMessage("Skipped forward successfully.");
            player.delTimeEpisode(PODCASTNUM);
        }

    }

    /**
     * skips backward in a podcast(-90sec)
     *
     * @param backward output class
     */
    private static void skipBackward(final OutputClass backward) {
        int timeLeft = player.getTimeEpisode();
        int aux1 = player.getEpisodeNumber();
        int duration = player.getPodcast().getEpisodes().get(aux1).getDuration();
        int time = duration - timeLeft;
        if (time < PODCASTNUM) {
            backward.setMessage("Rewound successfully.");
            aux1 = player.getEpisodeNumber();
            player.setEpisode(player.getPodcast().getEpisodes().get(aux1).getName());
            player.setTimeEpisode(player.getPodcast().getEpisodes().get(aux1).getDuration());

        } else {
            backward.setMessage("Rewound successfully.");
            player.setTimeEpisode(timeLeft + PODCASTNUM);
        }
    }

}

