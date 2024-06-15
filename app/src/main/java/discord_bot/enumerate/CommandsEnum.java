package discord_bot.enumerate;

import discord_bot.Main;

public enum CommandsEnum {
    
    HELP        (Main.HELP, 16, 6, "🆘", null),

    PLAY        (Main.PLAY, 0, null, null, null),
    SKIP        (Main.SKIP, 1, 1, "⏭️", null),
    PAUSE       (Main.PAUSE, 2, 2, "⏯️", null),
    CLEAR_QUEUE (Main.CLEAR_QUEUE, 3, 7, "🆑", null),
    LAST        (Main.LAST, 4, 4, "⏮️➕", null),
    QUEUE       (Main.QUEUE, 5, 11, "📜", null),
    STOP        (Main.STOP, 6, 8, "🛑", null),
    LOOP        (Main.LOOP, 7, 3, "🔁", null),
    SHUFFLE     (Main.SHUFFLE, 17, 9, "🔀", null),

    //Playlist commands
    ADD         (Main.PLAYLIST_ADD, 8, null, null, null),
    CREATE      (Main.PLAYLIST_CREATE, 9, null, null, null),
    LOAD        (Main.PLAYLIST_LOAD, 10, null, null, null),
    REMOVE      (Main.PLAYLIST_REMOVE, 11, null, null, null),
    SAVE        (Main.PLAYLIST_SAVE, 12, null, null, null),
    SEE         (Main.PLAYLISTS_SEE, 13, null, null, null),
    RECORD      (Main.PLAYLIST_RECORD, 14, null, null, null),
    PLAYLISTS   (Main.PLAYLISTS, 15, 5, "🎶", null),

    REPORT      (Main.REPORT, 18, null, null, null),
    SEE_REPORT  (Main.REPORT, 19, null, null, null),
    SEE_CACHE   (Main.REPORT, 20, null, null, null);


    public final String label;
    public final int commandId;
    public final Integer buttonId;
    public final String buttonlabel;
    public final String modalId;

    private CommandsEnum(String label, int commandId, Integer buttonId, String buttonlabel, String modalId) {
        this.label = label;
        this.commandId = commandId;
        this.buttonId = buttonId;
        this.buttonlabel = buttonlabel;
        this.modalId = modalId;
    }

    public static int getCommandId(String label) {

        for (CommandsEnum command : CommandsEnum.values()) {

            if (command.label.equals(label)) {
                return command.commandId;
            }
        }

        return -1;
    }

    public static int getButtonId(Integer id) {

        for (CommandsEnum command : CommandsEnum.values()) {

            if (command.buttonId == id) {
                return command.commandId;
            }
        }

        return -1;
    }
}
