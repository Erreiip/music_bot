package discord_bot.commands.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Playlists implements Commands {
    
    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        List<String> playlists;

        try {
            playlists = Playlist.getPlaylistsNames();
        } catch (Exception e) {
            event.reply("An error occurred while getting the playlists.").queue();
            return;
        }

        if (playlists.isEmpty()) {
            event.reply("No playlist found.").queue();
            return;
        }

        StringBuilder builder = new StringBuilder();

        playlists.forEach(playlist -> builder.append(":arrow_forward: ").append(playlist).append("\n"));

        event.reply(builder.toString()).queue();
    }
}
