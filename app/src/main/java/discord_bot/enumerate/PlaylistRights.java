package discord_bot.enumerate;

import java.util.HashMap;
import java.util.Map;

public enum PlaylistRights {
    
    PUBLIC("public"),
    PRIVATE("private");

    public final String value;

    private static final Map<String, PlaylistRights> map = new HashMap<>();
    
    static {
        for (PlaylistRights playlistRights : PlaylistRights.values()) {
            map.put(playlistRights.value, playlistRights);
        }
    }

    private PlaylistRights(String value) {
        this.value = value;
    }

    public static PlaylistRights get(String value) {
        return map.get(value);
    }
}
