package discord_bot.commands.playlists;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Add implements Commands {
    
    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        String name = event.getOption(Main.PLAYLIST_ADD_REMOVE_OPTION_NAME).getAsString();
        String url = event.getOption(Main.PLAYLIST_ADD_OPTION_URL).getAsString();

        url = Kawaine.getSongIdentifier(url);

        Playlist playlist;

        try { playlist = Playlist.readPlaylist(name); } 
        catch (Exception e) { event.reply("An error occurred while loading the playlist : " + e.getMessage()).queue(); return; }

        kawaine.addSong(event, url, playerManager, null, playlist);
    }
}
