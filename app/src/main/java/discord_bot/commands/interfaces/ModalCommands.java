package discord_bot.commands.interfaces;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;

public interface ModalCommands {
        
    public void execute(ModalInteractionEvent event);
}
