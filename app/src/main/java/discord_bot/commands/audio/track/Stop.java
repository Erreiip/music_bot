package discord_bot.commands.audio.track;

import discord_bot.commands.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Stop extends Commands {
    
    public Stop(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        stop(event);
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
            
        stop(event);
    }

    public void stop(IDeferrableCallback event) {
        
        musicManager.disconnect();

        MessageSender.infoEvent(musicManager.getMessageSender(), "Stopped the player and left the voice channel.", event);
    }
}
