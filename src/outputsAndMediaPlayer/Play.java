package outputsAndMediaPlayer;

import songs.Song;

import java.util.ArrayList;

public class Play {

    public Play() {
    }

    /**
     * simulates the playing of a playlist
     *
     * @param songs            list of all songs
     * @param currentTimestamp current timestamp of the action
     * @param player           the media player
     */
    public static void playPlaylist(final ArrayList<Song> songs, final int currentTimestamp,
                                    final MediaPlayer player) {
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
                            player.setArtist(song.getArtist());
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
                                    player.setArtist(song.getArtist());
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
                }
                String aux = player.getPlaylist().getSongs().get(player.getSongNumber());
                player.setSong(aux);
                for (Song song : songs) {
                    if (song.getName().equals(player.getSong())) {
                        player.setTimeLeft(song.getDuration());
                        player.setArtist(song.getArtist());
                    }
                }
                player.delTime(remainedTime);
                while (player.getTimeLeft() <= 0) {
                    int remainedTime1 = player.getTimeLeft() * (-1);
                    player.nextSong();
                    if (player.getSongNumber() == player.getPlaylist().getSongs().size()) {
                        player.setSongNumber(0);
                    }
                    player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                            player.setArtist(song.getArtist());
                        }
                    }
                    player.delTime(remainedTime1);
                }
            }

            if (player.getRepeat() == 2) {
                int remainedTime = player.getTimeLeft() * (-1);
                player.setSong(player.getPlaylist().getSongs().get(player.getSongNumber()));
                for (Song song : songs) {
                    if (song.getName().equals(player.getSong())) {
                        player.setTimeLeft(song.getDuration());
                        player.setArtist(song.getArtist());
                    }
                }
                player.delTime(remainedTime);
                while (player.getTimeLeft() <= 0) {
                    int remainedTime1 = player.getTimeLeft() * (-1);
                    for (Song song : songs) {
                        if (song.getName().equals(player.getSong())) {
                            player.setTimeLeft(song.getDuration());
                            player.setArtist(song.getArtist());
                        }
                    }
                    player.delTime(remainedTime1);
                }
            }
        }
    }

    /**
     * simulates the playing of an album
     *
     * @param timestamp current timestamp of the action
     * @param player    the media player
     */
    public static void playAlbum(final int timestamp, final MediaPlayer player) {
        if (player.getTimeLeft() > 0) {
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

    /**
     * simulates the playing of a song
     *
     * @param songs            list of all songs in the database
     * @param currentTimestamp current timestamp of the action
     * @param player           the media player
     */
    public static void playSongs(final ArrayList<Song> songs, final int currentTimestamp,
                                 final MediaPlayer player) {

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
     * simulates the playing of a podcast
     * @param currentTimestamp current timestamp of the action
     * @param player the media player
     */
    public static void playPodcasts(final int currentTimestamp, final MediaPlayer player) {

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
}
