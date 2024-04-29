package discord_bot.commands.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class See implements Commands {
    
    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        String name = event.getOption(Main.PLAYLISTS_SEE_OPTION_NAME).getAsString();

            Playlist playlist;

            try {
                playlist = Playlist.readPlaylist(name);
            } catch (Exception e) {
                event.reply("An error occurred while loading the playlist.").queue();
                return;
            }

            StringBuilder builder = new StringBuilder();

            List<AudioTrackInfo> tracks = playlist.getTracks();

            tracks.forEach(track -> builder.append(":arrow_forward: ").append(track.title).append("\n"));

            event.reply(builder.toString()).queue();
    }
}
