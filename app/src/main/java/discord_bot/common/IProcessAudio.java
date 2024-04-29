package discord_bot.common;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.lava_player.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface IProcessAudio {

    public void onTrackGet(SlashCommandInteractionEvent event, GuildMusicManager musicManager, AudioTrack track, Float speed);
}
