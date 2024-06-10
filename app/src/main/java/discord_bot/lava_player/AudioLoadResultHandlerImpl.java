package discord_bot.lava_player;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.model.MessageSender;
import discord_bot.utils.IProcessAudio;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class AudioLoadResultHandlerImpl implements AudioLoadResultHandler {

    private final String songIdentifier;
    private final IProcessAudio callback;
    private final SlashCommandInteractionEvent event;
    private final Float speed;
    private final MessageSender messageSender;

    public AudioLoadResultHandlerImpl(String songIdentifier, IProcessAudio callback, SlashCommandInteractionEvent event, Float speed, MessageSender messageSender) {

        this.songIdentifier = songIdentifier;
        this.callback = callback;
        this.event = event;
        this.speed = speed;
        this.messageSender = messageSender;
    }

    @Override
    public void trackLoaded(AudioTrack track) {

        callback.onTrackGet(event, track, this.speed);
    }

    @Override
    public void playlistLoaded(AudioPlaylist playlist) {

        System.out.println(playlist);
        System.out.println(playlist.getTracks());
        System.out.println(playlist.getName());

        AudioTrack firstTrack = playlist.getSelectedTrack();

        if (firstTrack == null) {
            firstTrack = playlist.getTracks().get(0);
        }

        callback.onTrackGet(event, firstTrack, this.speed);
    }

    @Override
    public void noMatches() {

        MessageSender.errorEvent(this.messageSender, "Nothing found by " + songIdentifier, event);
    }

    @Override
    public void loadFailed(FriendlyException exception) {

        MessageSender.errorEvent(this.messageSender, "An error occurred while loading the song : " + exception,
                event);

        exception.printStackTrace();
    }
    
    
}
