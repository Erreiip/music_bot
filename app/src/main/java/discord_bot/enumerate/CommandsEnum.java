package discord_bot.enumerate;

import java.util.HashMap;
import java.util.Map;

import discord_bot.Main;

public enum CommandsEnum {
    
    HELP        (Main.HELP, 16, 6, "🆘", null, true),

    PLAY        (Main.PLAY, 0, null, null, null, true),
    SKIP        (Main.SKIP, 1, 1, "⏭️", null, true),
    PAUSE       (Main.PAUSE, 2, 2, "⏯️", null, true),
    CLEAR_QUEUE (Main.CLEAR_QUEUE, 3, 7, "🆑", null, true),
    LAST        (Main.LAST, 4, 4, "⏮️➕", null, true),
    QUEUE       (Main.QUEUE, 5, 11, "📜", null, true),
    STOP        (Main.STOP, 6, 8, "🛑", null, true),
    LOOP        (Main.LOOP, 7, 3, "🔁", null, true),
    SHUFFLE     (Main.SHUFFLE, 17, 9, "🔀", null, true),

    //Playlist commands
    ADD         (Main.PLAYLIST_ADD, 8, null, null, null, true),
    CREATE      (Main.PLAYLIST_CREATE, 9, null, null, null, true),
    LOAD        (Main.PLAYLIST_LOAD, 10, null, null, null, true),
    REMOVE      (Main.PLAYLIST_REMOVE, 11, null, null, null, true),
    SAVE        (Main.PLAYLIST_SAVE, 12, null, null, null, true),
    SEE         (Main.PLAYLISTS_SEE, 13, null, null, null, true),
    RECORD      (Main.PLAYLIST_RECORD, 14, null, null, null, true),
    PLAYLISTS   (Main.PLAYLISTS, 15, 5, "🎶", null, true),
    FROM_QUEUE  (Main.FROM_QUEUE, 20, 12, "🆕", "FromQueueModal", false),

    REPORT      (Main.REPORT, 18, null, null, null, true),
    SEE_REPORT  (Main.REPORT, 19, null, null, null, true);


    public final String label;
    public final int commandId;
    public final Integer buttonId;
    public final String buttonlabel;
    public final String modalId;
    public final boolean isHookable;

    private static Map<String, CommandsEnum> mapCommandId = new HashMap<>();
    private static Map<Integer, CommandsEnum> mapButtonId = new HashMap<>();
    private static Map<String, CommandsEnum> mapModalId = new HashMap<>();

    static {
        for (CommandsEnum command : CommandsEnum.values()) {
            mapCommandId.put(command.label, command);
            mapButtonId.put(command.buttonId, command);
            mapModalId.put(command.modalId, command);
        }
    }

    private CommandsEnum(String label, int commandId, Integer buttonId, String buttonlabel, String modalId, boolean isHookable) {
        this.label = label;
        this.commandId = commandId;
        this.buttonId = buttonId;
        this.buttonlabel = buttonlabel;
        this.modalId = modalId;
        this.isHookable = isHookable;
    }

    public static CommandsEnum getCommandId(String label) {
        
        return mapCommandId.get(label);
    }

    public static CommandsEnum getButtonId(Integer id) {

        return mapButtonId.get(id);
    }

    public static CommandsEnum getModalId(String modalId) {

        return mapModalId.get(modalId);
    }
}
