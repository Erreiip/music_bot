package discord_bot.commands.audio.playlists;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.database.PlaylistDatabase;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.dao.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class See extends Commands {
    
    public See(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        String name = event.getOption(Main.PLAYLISTS_SEE_OPTION_NAME).getAsString();

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();

        See.seePlaylist(musicManager.getMessageSender(), name, playlistDB, event);
    }

    public static void seePlaylist(MessageSender messageSender, String name, PlaylistDatabase playlistDB, IDeferrableCallback event) {

        Playlist playlist = playlistDB.getPlaylistWithTracks(event.getGuild().getIdLong(), name);

        if ( playlist == null ) {
            MessageSender.errorEvent(messageSender, "Playlist not found.", event);
            return;
        }
        
        StringBuilder builder = new StringBuilder();

        playlist.getTracks().forEach(track -> builder.append(":arrow_forward: ").append(track.title).append("\n"));

        MessageSender.onPlaylistEvent(messageSender, builder.toString(), event, name);
    }
}
