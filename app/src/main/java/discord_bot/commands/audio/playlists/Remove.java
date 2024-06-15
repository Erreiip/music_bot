package discord_bot.commands.audio.playlists;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
public class Remove extends Commands {
    
    public Remove(GuildMusicManager musicManager) {
        super(musicManager);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String trackNameOrIndex = event.getOption(Main.PLAYLIST_REMOVE_OPTION_TITLE).getAsString();

        Playlist playlist;

        try { playlist = Playlist.readPlaylist(name); } 
        catch (Exception e) { 

            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while loading the playlist : " + e.getMessage(), event);
            
            return;
        }

        boolean removed = false; 

        if ( Main.isInteger(trackNameOrIndex) ) {

            int index = Integer.parseInt(trackNameOrIndex);
            removed = playlist.removeTrack(index - 1);
        } else {
            removed = playlist.removeTrack(trackNameOrIndex);
        }

        if ( ! removed ) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "Track not found.", event);
            return;
        }

        try { Playlist.writePlaylist(playlist); } 
        catch (Exception e) { 
            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while saving the playlist.", event);
            return;
        }

        MessageSender.infoEvent(musicManager.getMessageSender(), "Track removed.", event);
    }
}
