package discord_bot;

import javax.security.auth.login.LoginException;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.Commands;

public class Main {

    private static final String token = Secret.getToken();

    public static final String HELP = "help";

    public static final String PLAY = "play";
    public static final String PLAY_OPTION_QUERY = "query";
    public static final String PLAY_OPTION_SPEED = "speed";

    public static final String PLAYLIST_RECORD = "playlist_record";
    public static final String PLAYLIST_RECORD_OPTION_NAME = "name";

    public static final String PLAYLIST_LOAD = "playlist_load";
    public static final String PLAYLIST_LOAD_OPTION_NAME = "name";

    public static final String PLAYLISTS = "playlists";

    public static final String PLAYLISTS_SEE = "playlists_see";
    public static final String PLAYLISTS_SEE_OPTION_NAME = "name";

    public static final String PLAYLIST_CREATE = "playlist_create";
    public static final String PLAYLIST_CREATE_OPTION_NAME = "name";

    public static final String PLAYLIST_ADD = "playlist_add";
    public static final String PLAYLIST_REMOVE = "playlist_remove";
    public static final String PLAYLIST_ADD_REMOVE_OPTION_NAME = "name";
    public static final String PLAYLIST_ADD_OPTION_URL = "url";
    public static final String PLAYLIST_REMOVE_OPTION_TITLE = "title";

    public static final String SKIP = "skip";
    public static final String LOOP = "loop";
    public static final String QUEUE = "queue";
    public static final String STOP = "stop";
    public static final String LAST = "last";
    public static final String PAUSE = "pause";    
    public static final String PLAYLIST_SAVE = "playlist_save";
    public static final String CLEAR_QUEUE = "clear_queue";

    public static void main(String[] args) throws LoginException, InterruptedException {

        JDA jda = JDABuilder.createDefault(token).build().awaitReady();

        for (Guild guild : jda.getGuilds()) {
            
            guild.updateCommands().addCommands
            (
                Commands.slash(HELP, "Display the help"),
                Commands.slash(PLAY, "Play a song in your voice channel")
                    .addOption(OptionType.STRING, PLAY_OPTION_QUERY, "url or title of the video", true)
                    .addOption(OptionType.STRING, PLAY_OPTION_SPEED, "speed of the song", false),
                Commands.slash(SKIP, "Skip the current song"),
                Commands.slash(LOOP, "Set or unset the loop mode"),
                Commands.slash(QUEUE, "Display the queue"),
                Commands.slash(STOP, "Stop the music"),
                Commands.slash(LAST, "Add last played song to the queue"),
                Commands.slash(PAUSE, "Pause the music"),
                Commands.slash(PLAYLIST_RECORD, "Record all the music added")
                    .addOption(OptionType.STRING, PLAYLIST_RECORD_OPTION_NAME, "name of the playlist", true),
                Commands.slash(PLAYLIST_SAVE, "Save the current playlist"),
                Commands.slash(CLEAR_QUEUE, "Clear the queue"),
                Commands.slash(PLAYLIST_LOAD, "Load a playlist")
                    .addOption(OptionType.STRING, PLAYLIST_LOAD_OPTION_NAME, "name of the playlist", true),
                Commands.slash(PLAYLISTS, "Display all the playlists"),
                Commands.slash(PLAYLISTS_SEE, "Display a playlist")
                    .addOption(OptionType.STRING, PLAYLISTS_SEE_OPTION_NAME, "name of the playlist", true),
                Commands.slash(PLAYLIST_ADD, "Add a song to a playlist")
                    .addOption(OptionType.STRING, PLAYLIST_ADD_REMOVE_OPTION_NAME, "name of the playlist", true)
                    .addOption(OptionType.STRING, PLAYLIST_ADD_OPTION_URL, "url or title of the video", true),
                Commands.slash(PLAYLIST_REMOVE, "Remove a song from a playlist")
                    .addOption(OptionType.STRING, PLAYLIST_ADD_REMOVE_OPTION_NAME, "name of the playlist", true)
                    .addOption(OptionType.STRING, PLAYLIST_REMOVE_OPTION_TITLE, "title or index of the video", true)
            ).queue();
            
        }
        
        Kawaine kawaine = new Kawaine();
        jda.addEventListener(kawaine);
        jda.addEventListener(new ButtonListener(kawaine));
    } 

    public static boolean isInteger(String s) {

        try {
            Integer.parseInt(s);
        } catch (NumberFormatException e) {
            return false;
        }

        return true;
    }
}
