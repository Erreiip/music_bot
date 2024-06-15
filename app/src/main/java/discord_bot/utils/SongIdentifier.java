package discord_bot.utils;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import discord_bot.utils.spotify.SpotifyAuthenticator;
public class SongIdentifier {

    private static final Pattern PATTERN_SPOTIFY = Pattern.compile("https://open.spotify.com/.*", Pattern.CASE_INSENSITIVE);
    private static final Pattern PATTERN_URL = Pattern.compile(".*://.*\\..*", Pattern.CASE_INSENSITIVE);

    public static boolean isPlaylist(String songIdentifier) {

        if ( PATTERN_URL.matcher(songIdentifier).matches() ) {

            return songIdentifier.contains("list=") || songIdentifier.contains("playlist");
        }

        return false;
    }

    public static String getSongIdentifier(String songIdentifier) {

        if ( PATTERN_URL.matcher(songIdentifier).matches() && ! PATTERN_SPOTIFY.matcher(songIdentifier).matches() ) {

            return songIdentifier;
        }

        if ( PATTERN_SPOTIFY.matcher(songIdentifier).matches() ) {

            songIdentifier = querySpotify(songIdentifier);
        }

        return "ytsearch: " + songIdentifier;
    }

    public static List<String> getPlaylist(String songIdentifier) {

        List<String> playlist = new ArrayList<>();

        if ( PATTERN_SPOTIFY.matcher(songIdentifier).matches() ) {

            SpotifyAuthenticator spotifyAuthenticator = SpotifyAuthenticator.getInstance();

            List<String> spotifySong = spotifyAuthenticator.getPlaylistTracks(songIdentifier);

            if (spotifySong == null) return null;

            for ( String song : spotifySong ) {

                playlist.add(getSongIdentifier(song));
            }
        }

        return playlist;
    }

    private static String querySpotify(String songIdentifier) {

        SpotifyAuthenticator spotifyAuthenticator = SpotifyAuthenticator.getInstance();

        return spotifyAuthenticator.getTitle(songIdentifier);
    }
    
}
