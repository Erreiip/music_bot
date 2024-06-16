package discord_bot.model.dao;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    private final int id;
    private final Long guildId;
    private final String name;
    private final int ownerId;
    private final String rights;
    private List<Track> tracks;

    public Playlist(int id, Long guildId, String name, int ownerId, String rights, List<Track> tracks) {
        this.id = id;
        this.guildId = guildId;
        this.name = name;
        this.ownerId = ownerId;
        this.rights = rights;
        this.tracks = tracks;
    }

    public Playlist(int id, Long guildId, String name, int ownerId, String rights) {
        
        this(id, guildId, name, ownerId, rights, new ArrayList<>());
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }

    
    public static class Track {
        
        private final int id;
        private final String title;
        private final String uri;

        public Track(int id, String title, String uri) {
            this.id = id;
            this.title = title;
            this.uri = uri;
        }
    }
    
}
