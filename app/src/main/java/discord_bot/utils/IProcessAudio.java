package discord_bot.utils;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.interactions.callbacks.IReplyCallback;

public interface IProcessAudio {

    public void onTrackGet(IReplyCallback event, AudioTrack track, Float speed);

    public void onTrackGet(IReplyCallback event, List<AudioTrack> track);
}
