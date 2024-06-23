package discord_bot.commands.interfaces;

import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;

public interface SelectedCommands {
    
    public void execute(StringSelectInteractionEvent event);
}