package discord_bot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public interface Commands {
    
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine);
}
