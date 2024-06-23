package discord_bot.enumerate;

import java.util.HashMap;
import java.util.Map;

import discord_bot.Main;

public enum CommandsEnum {
    
    HELP        (Main.HELP, 16, "❓", true),

    PLAY        (Main.PLAY, 0, null, true),
    SKIP        (Main.SKIP, 1, "⏭️", true),
    PAUSE       (Main.PAUSE, 2, "⏯️", true),
    CLEAR_QUEUE (Main.CLEAR_QUEUE, 7, "🆑", true),
    LAST        (Main.LAST, 4, "⏮️➕", true),
    QUEUE       (Main.QUEUE, 5, "📜", true),
    STOP        (Main.STOP, 6, "🛑", true),
    LOOP        (Main.LOOP, 7, "🔁", true),
    SHUFFLE     (Main.SHUFFLE, 17, "🔀", true),

    //Playlist commands
    ADD         (Main.PLAYLIST_ADD, 8, null, true),
    CREATE      (Main.PLAYLIST_CREATE, 9, null, true),
    LOAD        (Main.PLAYLIST_LOAD, 10, "📢", true),
    REMOVE      (Main.PLAYLIST_REMOVE, 11, null, true),
    SAVE        (Main.PLAYLIST_SAVE, 12, null, true),
    SEE         (Main.PLAYLISTS_SEE, 13, null, true),
    RECORD      (Main.PLAYLIST_RECORD, 14, "🔴",true),
    PLAYLISTS   (Main.PLAYLISTS, 15, "🎶", true),
    FROM_QUEUE  (Main.FROM_QUEUE, 20, "🆕", false),

    REPORT      (Main.REPORT, 18, null, true),
    SEE_REPORT  (Main.REPORT, 19, null, true);


    public final String label;
    public final int commandId;
    public final String buttonlabel;
    public final boolean isHookable;

    private static Map<String, CommandsEnum> mapCommandId = new HashMap<>();

    static {
        for (CommandsEnum command : CommandsEnum.values()) {
            mapCommandId.put(command.label, command);
        }
    }

    private CommandsEnum(String label, int commandId, String buttonlabel, boolean isHookable) {
        this.label = label;
        this.commandId = commandId;
        this.buttonlabel = buttonlabel;
        this.isHookable = isHookable;
    }

    public static CommandsEnum getCommandId(String label) {
        
        return mapCommandId.get(label);
    }
}
