package discord_bot.commands.playlists;

import java.util.Collections;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Load implements Commands {
    
    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
     
        String name = event.getOption(Main.PLAYLIST_LOAD_OPTION_NAME).getAsString();

        Playlist playlist;

        try {
            playlist = Playlist.readPlaylist(name);
        } catch (Exception e) {
            e.printStackTrace();
            event.reply("An error occurred while loading the playlist.").queue();
            return;
        }
        
        List<AudioTrackInfo> tracks = playlist.getTracks();
        Collections.shuffle(tracks);

        playlist.getTracks().forEach(track -> kawaine.addSong(event, track.identifier, playerManager, null, kawaine));

        event.reply("Playlist loaded.").queue();
    }
}
