package discord_bot.commands.playlists;

import java.util.Collections;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.jda.Kawaine;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import discord_bot.model.TrackScheduler;
import discord_bot.model.playlist_writer.Playlist;
import discord_bot.utils.IProcessAudio;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
public class Load extends Commands implements IProcessAudio {

    public Load(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
     
        String name = event.getOption(Main.PLAYLIST_LOAD_OPTION_NAME).getAsString();

        Playlist playlist;

        try {
            playlist = Playlist.readPlaylist(name);
        } catch (Exception e) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "An error occurred while loading the playlist : " + e.getMessage(), event);
            return;
        }
        
        List<AudioTrackInfo> tracks = playlist.getTracks();
        Collections.shuffle(tracks);

        musicManager.joinChannel(event);

        playlist.getTracks().forEach(track -> musicManager.addSong(event, track.identifier, null, this));

        MessageSender.infoEvent(musicManager.getMessageSender(), "Playlist loaded.", event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track,
            Float speed) {

        musicManager.getScheduler().queue(track, speed != null ? speed : 1, event);
    }
}
