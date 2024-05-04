package discord_bot.commands.track;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class ClearQueue extends Commands {

    public ClearQueue(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
        
        musicManager.scheduler.clearQueue();

        event.getHook().sendMessage("Cleared the queue.").queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
