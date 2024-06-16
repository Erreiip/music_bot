package discord_bot.utils.database;

import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import discord_bot.enumerate.PlaylistRights;
import discord_bot.model.dao.Playlist;

public class PlaylistDatabase {

    private static final String TAG = "PlaylistDatabase";
    private static PlaylistDatabase instance;

    private Database database;

    private PlaylistDatabase() {
        this.database = Database.getInstance();

        database.addPreparedStatement(CREATE_KEY, CREATE_PLAYLIST);
        database.addPreparedStatement(CREATE_TRACK_KEY, CREATE_TRACK);

        database.addPreparedStatement(GET_PLAYLISTS_FROM_GUILD_KEY, GET_PLAYLISTS_FROM_GUILD);
    }

    public static PlaylistDatabase getInstance() {

        if (instance == null) {
            PlaylistDatabase.instance = new PlaylistDatabase();
        }

        return instance;
    }


    /*
     * Inserts
     * */
    private final String CREATE_KEY = TAG + ".createPlaylist";
    private final String CREATE_PLAYLIST = "INSERT INTO playlists (guild_id, name, owner_id, rights) VALUES (?, ?, ?, ?)";

    private final String CREATE_TRACK_KEY = TAG + ".createTrack";
    private final String CREATE_TRACK = "INSERT INTO tracks (playlist_guild_id, playlist_name, title, uri) VALUES (?, ?, ?, ?)";

    public boolean createPlaylist(Long guildId, String name, int ownerId, PlaylistRights rights) {
        
        PreparedStatement statement = database.mapCache.get(CREATE_KEY);
        
        try {
            statement.setLong(1, guildId);
            statement.setString(2, name);
            statement.setInt(3, ownerId);
            statement.setString(4, rights.value);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }

    public boolean addTrackToPlaylist(Long guildId, String name, String title, String uri) {
        
        PreparedStatement statement = database.mapCache.get(CREATE_TRACK_KEY);
        
        try {
            statement.setLong(1, guildId);
            statement.setString(2, name);
            statement.setString(3, title);
            statement.setString(4, uri);

            statement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        return true;
    }
    

    /*
     * Selects
     * */
    private final String GET_PLAYLISTS_FROM_GUILD_KEY = TAG + ".getPlaylistsFromGuild";
    private final String GET_PLAYLISTS_FROM_GUILD = "SELECT * FROM playlists WHERE guild_id = ?";

    private final String GET_PLAYLIST_KEY = TAG + ".getPlaylist";
    private final String GET_PLAYLIST = "SELECT * FROM playlists WHERE guild_id = ? AND name = ?";

    private final String GET_TRACKS_FROM_PLAYLIST_KEY = TAG + ".getTracksFromPlaylist";
    private final String GET_TRACKS_FROM_PLAYLIST = "SELECT * FROM tracks WHERE playlist_guild_id = ? AND playlist_name = ?";

    public List<Playlist> getPlaylistsFromGuild(Long guildId) {
        
        List<Playlist> playlists = new ArrayList<>();

        try {
            PreparedStatement statement = database.mapCache.get(GET_PLAYLISTS_FROM_GUILD_KEY);

            statement.setLong(1, guildId);

            var result = statement.executeQuery();

            while (result.next()) {
                playlists.add(new Playlist(
                    result.getInt("id"),
                    result.getLong("guild_id"),
                    result.getString("name"),
                    result.getInt("owner_id"),
                    result.getString("rights")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlists;
    }

    public Playlist getPlaylist(Long guildId, String name) {
        
        try {
            PreparedStatement statement = database.mapCache.get(GET_PLAYLIST_KEY);

            statement.setLong(1, guildId);
            statement.setString(2, name);

            var result = statement.executeQuery();

            if (result.next()) {
                return new Playlist(
                    result.getInt("id"),
                    result.getLong("guild_id"),
                    result.getString("name"),
                    result.getInt("owner_id"),
                    result.getString("rights")
                );
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public Playlist getPlaylistWithTracks(Long guildId, String name) {
        
        Playlist playlist = getPlaylist(guildId, name);

        if (playlist == null) {
            return null;
        }

        try {
            PreparedStatement statement = database.mapCache.get(GET_TRACKS_FROM_PLAYLIST_KEY);

            statement.setLong(1, guildId);
            statement.setString(2, name);

            var result = statement.executeQuery();

            while (result.next()) {
                playlist.addTrack(new Playlist.Track(
                    result.getInt("id"),
                    result.getString("title"),
                    result.getString("uri")
                ));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return playlist;
    }

}
