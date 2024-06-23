package discord_bot.commands.audio.playlists;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.database.PlaylistDatabase;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Remove extends Commands {
    
    public Remove(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String trackName = event.getOption(Main.PLAYLIST_REMOVE_OPTION_TITLE).getAsString();

        PlaylistDatabase playlistDB = PlaylistDatabase.getInstance();
        boolean removed = playlistDB.removeTrackFromPlaylist(event.getGuild().getIdLong(), name, trackName); 

        if ( ! removed ) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "Track not found.", event);
            return;
        }

        MessageSender.infoEvent(musicManager.getMessageSender(), "Track removed.", event);
    }
}
