package discord_bot.commands.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.embded.MusicEmbded;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Playlists extends Commands {
    
    public Playlists(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        try {
            getPlaylists((IDeferrableCallback) event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        try {
            getPlaylists((IDeferrableCallback) event);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void getPlaylists(IDeferrableCallback event) throws Exception {

        List<String> playlists;

        playlists = Playlist.getPlaylistsNames();

        if (playlists.isEmpty()) {
            event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("No playlist found.")).queue();
            return;
        }

        StringBuilder builder = new StringBuilder();

        playlists.forEach(playlist -> builder.append(":arrow_forward: ").append(playlist).append("\n"));

        event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse(builder.toString()) ).queue();
    }
}
