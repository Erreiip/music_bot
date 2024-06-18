package discord_bot.model.dao;

import java.util.ArrayList;
import java.util.List;

public class Playlist {

    public final Long guildId;
    public final String name;
    public final Long ownerId;
    public final String rights;
    private List<Track> tracks;

    public Playlist(Long guildId, String name, Long ownerId, String rights, List<Track> tracks) {
        this.guildId = guildId;
        this.name = name;
        this.ownerId = ownerId;
        this.rights = rights;
        this.tracks = tracks;
    }

    public Playlist(Long guildId, String name, Long ownerId, String rights) {
        
        this(guildId, name, ownerId, rights, new ArrayList<>());
    }

    public List<Track> getTracks() {
        return tracks;
    }

    public void setTracks(List<Track> tracks) {
        this.tracks = tracks;
    }

    public void addTrack(Track track) {
        this.tracks.add(track);
    }

    
    public static class Track {
        
        public final int id;
        public final String title;
        public final String uri;

        public Track(int id, String title, String uri) {
            this.id = id;
            this.title = title;
            this.uri = uri;
        }
    }
    
}
