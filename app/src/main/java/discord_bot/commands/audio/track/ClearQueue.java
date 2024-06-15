package discord_bot.commands.audio.track;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class ClearQueue extends Commands {

    public ClearQueue(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        clearQueue((IDeferrableCallback) event);
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
        
        clearQueue((IDeferrableCallback) event);
    }

    public void clearQueue(IDeferrableCallback event) {

        musicManager.getScheduler().clearQueue();

        MessageSender.infoEvent(musicManager.getMessageSender(), "Queue cleared.", event);
    }
    
}
