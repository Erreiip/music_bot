package discord_bot.commands.audio.playlists;

import java.util.ArrayList;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Main;
import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.playlist_writer.Playlist;
import discord_bot.utils.IProcessAudio;
import discord_bot.utils.SongIdentifier;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Add extends Commands implements IProcessAudio {

    public Add(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String url = event.getOption(Main.PLAYLIST_ADD_OPTION_URL).getAsString();

        List<String> songs = new ArrayList<>();

        if ( SongIdentifier.isPlaylist(url) ) {
            songs = SongIdentifier.getPlaylist(url);           
        } else {
            songs.add(SongIdentifier.getSongIdentifier(url));
        }

        Playlist playlist;

        try { playlist = Playlist.readPlaylist(name); } 
        catch (Exception e) { 

            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while loading the playlist : " + e.getMessage(), event);   
            return; 
        }

        for ( String song : songs ) {
            musicManager.addSong(event, song, null, this);
        }
    }

    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track, Float speed) {
        
        //TODO : Add the track to the playlist
    }
}
