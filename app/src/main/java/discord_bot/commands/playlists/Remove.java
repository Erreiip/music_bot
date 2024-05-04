package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Remove extends Commands {
    
    public Remove(TrackScheduler scheduler) {
        super(scheduler);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String trackNameOrIndex = event.getOption(Main.PLAYLIST_REMOVE_OPTION_TITLE).getAsString();

        Playlist playlist;

        try { playlist = Playlist.readPlaylist(name); } 
        catch (Exception e) { event.getHook().sendMessage("An error occurred while loading the playlist : " + e.getMessage()).queue(); return; }

        boolean removed = false; 

        if ( Main.isInteger(trackNameOrIndex) ) {

            int index = Integer.parseInt(trackNameOrIndex);
            removed = playlist.removeTrack(index - 1);
        } else {

            removed = playlist.removeTrack(trackNameOrIndex);
        }

        if ( ! removed ) {
            event.getHook().sendMessage("Track not found.").queue();
            return;
        }

        try { Playlist.writePlaylist(playlist); } 
        catch (Exception e) { 
            event.getHook().sendMessage("An error occurred while saving the playlist.").queue();
            return;
        }

        event.getHook().sendMessage("Track removed.").queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
