package discord_bot.commands;

import discord_bot.Kawaine;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ButtonCommands {
    
    public void execute(ButtonInteractionEvent event, Kawaine kawaine);
}   
