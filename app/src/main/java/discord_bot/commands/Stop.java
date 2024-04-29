package discord_bot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.managers.AudioManager;

public class Stop implements Commands {
    
    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        AudioManager audioManager = event.getMember().getGuild().getAudioManager();
        audioManager.closeAudioConnection();

        event.reply("Stopped the player and left the voice channel.").queue();
    }
}
