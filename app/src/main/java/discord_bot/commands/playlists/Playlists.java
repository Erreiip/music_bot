package discord_bot.commands.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Playlists extends Commands {
    
    public Playlists(TrackScheduler scheduler) {
        super(scheduler);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        List<String> playlists;

        try {
            playlists = Playlist.getPlaylistsNames();
        } catch (Exception e) {
            event.getHook().sendMessage("An error occurred while getting the playlists.").queue();
            return;
        }

        if (playlists.isEmpty()) {
            event.getHook().sendMessage("No playlist found.").queue();
            return;
        }

        StringBuilder builder = new StringBuilder();

        playlists.forEach(playlist -> builder.append(":arrow_forward: ").append(playlist).append("\n"));

        event.getHook().sendMessage(builder.toString()).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
