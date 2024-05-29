package discord_bot.utils;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Pattern;

import discord_bot.Main;
import discord_bot.utils.database.Database;
import discord_bot.utils.spotify.SpotifyAuthenticator;
import discord_bot.utils.youtube.ApiYoutube;

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

        if ( PATTERN_URL.matcher(songIdentifier).matches() ) {

            return songIdentifier;
        }

        if ( ! Env.isDebug() ) {

            try {
                String song = Database.getInstance().getQuery(songIdentifier);
                if (song != null) return song;
            } catch(Exception e) { e.printStackTrace(); } 
        }       

        Couple<String, String> query = queryByName(songIdentifier);

        if (query == null) return null;

        if ( ! Env.isDebug() ) {
            Database.addTrack(songIdentifier, query.second);
        }

        return query.second;
    }

    public static List<String> getPlaylist(String songIdentifier) {

        List<String> playlist = new ArrayList<>();

        if ( PATTERN_SPOTIFY.matcher(songIdentifier).matches() ) {

            SpotifyAuthenticator spotifyAuthenticator = SpotifyAuthenticator.getInstance();

            List<String> spotifySong = spotifyAuthenticator.getPlaylistTracks(songIdentifier);

            if (spotifySong == null) return null;

            for ( String song : spotifySong ) {

                playlist.add(getSongIdentifier(song));

                System.out.println(song + "/" + getSongIdentifier(song));
            }

        } else {

            try {
                playlist = ApiYoutube.getPlaylistItems(songIdentifier);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        return playlist;
    }

    private static Couple<String, String> queryByName(String name) {

        try {
            return ApiYoutube.search(name);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
    
}
