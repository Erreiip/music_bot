package discord_bot.commands.interfaces;

import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public interface ButtonCommands {
    
    public void execute(ButtonInteractionEvent event);
}   
