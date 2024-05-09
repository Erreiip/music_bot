package discord_bot.lava_player;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.common.IProcessAudio;
import discord_bot.embded.MusicEmbded;
import discord_bot.jda_listener.model.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AudioLoadResultHandlerImpl implements AudioLoadResultHandler {

    private final GuildMusicManager musicManager;
    private final String songIdentifier;
    private final IProcessAudio callback;
    private final SlashCommandInteractionEvent event;
    private final Float speed;

    public AudioLoadResultHandlerImpl(GuildMusicManager musicManager, String songIdentifier, IProcessAudio callback, SlashCommandInteractionEvent event, Float speed) {

        this.musicManager = musicManager;
        this.songIdentifier = songIdentifier;
        this.callback = callback;
        this.event = event;
        this.speed = speed;
    }

    @Override
    public void trackLoaded(AudioTrack track) {

        callback.onTrackGet(event, musicManager, track, this.speed);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {

        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        callback.onTrackGet(event, musicManager, firstTrack, this.speed);
    }

    @Override
    public void noMatches() {

        event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("Nothing found by " + songIdentifier)).queue();
    }

    @Override
    public void loadFailed(FriendlyException exception) {

        event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("Could not play: " + exception.getMessage())).queue();
        exception.printStackTrace();
    }
    
}
