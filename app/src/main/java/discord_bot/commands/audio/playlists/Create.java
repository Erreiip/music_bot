package discord_bot.commands.audio.playlists;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Create extends Commands {

    public Create(GuildMusicManager musicManager) {
        super(musicManager);
    }

   @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
     
        String name = event.getOption(Main.PLAYLIST_CREATE_OPTION_NAME).getAsString();

        try { Playlist.createPlaylist(name); } 
        catch (Exception e) { 

            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while loading the playlist : " + e.getMessage(), event);            
            return;
        }

        MessageSender.infoEvent(musicManager.getMessageSender(), "Playlist created.", event);
    }
    
}
