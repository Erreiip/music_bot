package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Remove implements Commands {
    
    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String trackNameOrIndex = event.getOption(Main.PLAYLIST_REMOVE_OPTION_TITLE).getAsString();

        Playlist playlist;

        try { playlist = Playlist.readPlaylist(name); } 
        catch (Exception e) { event.reply("An error occurred while loading the playlist : " + e.getMessage()).queue(); return; }

        boolean removed = false; 

        if ( Main.isInteger(trackNameOrIndex) ) {

            int index = Integer.parseInt(trackNameOrIndex);
            removed = playlist.removeTrack(index - 1);
        } else {

            removed = playlist.removeTrack(trackNameOrIndex);
        }

        if ( ! removed ) {
            event.reply("Track not found.").queue();
            return;
        }

        try { Playlist.writePlaylist(playlist); } 
        catch (Exception e) { 
            event.reply("An error occurred while saving the playlist.").queue();
            return;
        }

        event.reply("Track removed.").queue();
    }
}
