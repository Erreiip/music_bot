package discord_bot.enumerate;

public enum PlaylistRights {
    
    PUBLIC("public"),
    PRIVATE("private");

    public final String value;

    PlaylistRights(String value) {
        this.value = value;
    }
}
