package discord_bot.commands.playlists;

import java.util.Collections;
import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.Main;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.commands.track.Play;
import discord_bot.common.IProcessAudio;
import discord_bot.embded.MusicEmbded;
import discord_bot.playlist_writer.Playlist;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Load extends Commands implements IProcessAudio {

    public Load(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
     
        String name = event.getOption(Main.PLAYLIST_LOAD_OPTION_NAME).getAsString();

        Playlist playlist;

        try {
            playlist = Playlist.readPlaylist(name);
        } catch (Exception e) {
            e.printStackTrace();
            event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("An error occurred while loading the playlist.")).queue();
            return;
        }
        
        List<AudioTrackInfo> tracks = playlist.getTracks();
        Collections.shuffle(tracks);

        kawaine.joinChannel(event);

        playlist.getTracks().forEach(track -> kawaine.addSong(event, track.identifier, null, this));

        event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("Playlist loaded.")).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }

    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, GuildMusicManager musicManager, AudioTrack track,
            Float speed) {
        
        musicManager.scheduler.queueWithoutFire(track, speed != null ? speed : 1, event);
    }
}
