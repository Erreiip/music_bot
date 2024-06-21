package discord_bot.utils;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface IProcessAudio {

    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track, Float speed);

    public void onTrackGet(SlashCommandInteractionEvent event, List<AudioTrack> track);
}
