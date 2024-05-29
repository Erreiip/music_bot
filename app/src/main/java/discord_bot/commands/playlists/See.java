package discord_bot.commands.playlists;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class See extends Commands {
    
    public See(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        
        String name = event.getOption(Main.PLAYLISTS_SEE_OPTION_NAME).getAsString();

        Playlist playlist;

        try {
            playlist = Playlist.readPlaylist(name);
        } catch (Exception e) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while loading the playlist.", event);
            return;
        }

        StringBuilder builder = new StringBuilder();

        List<AudioTrackInfo> tracks = playlist.getTracks();

        tracks.forEach(track -> builder.append(":arrow_forward: ").append(track.title).append("\n"));

        MessageSender.infoEvent(musicManager.getMessageSender(), builder.toString(), event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
