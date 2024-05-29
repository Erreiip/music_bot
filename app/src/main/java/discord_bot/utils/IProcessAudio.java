package discord_bot.utils;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.jda.Kawaine;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface IProcessAudio {

    public void onTrackGet(SlashCommandInteractionEvent event, AudioTrack track, Float speed);
}
