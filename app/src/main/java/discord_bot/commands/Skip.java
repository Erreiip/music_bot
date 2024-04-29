package discord_bot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.Kawaine;
import discord_bot.lava_player.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Skip implements Commands {

    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
           
        musicManager.scheduler.setLoop(false);
        musicManager.scheduler.nextTrack();
    
        event.reply("Skipped to next track.").queue();
    }
    
}
