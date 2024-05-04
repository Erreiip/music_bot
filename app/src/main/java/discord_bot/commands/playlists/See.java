package discord_bot.commands.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class See extends Commands {
    
    public See(TrackScheduler scheduler) {
        super(scheduler);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        String name = event.getOption(Main.PLAYLISTS_SEE_OPTION_NAME).getAsString();

            Playlist playlist;

            try {
                playlist = Playlist.readPlaylist(name);
            } catch (Exception e) {
                event.getHook().sendMessage("An error occurred while loading the playlist.").queue();
                return;
            }

            StringBuilder builder = new StringBuilder();

            List<AudioTrackInfo> tracks = playlist.getTracks();

            tracks.forEach(track -> builder.append(":arrow_forward: ").append(track.title).append("\n"));

            event.getHook().sendMessage(builder.toString()).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
