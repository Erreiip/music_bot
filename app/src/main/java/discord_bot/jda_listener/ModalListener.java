package discord_bot.jda_listener;

import javax.annotation.Nonnull;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ModalListener extends ListenerAdapter {

    Kawaine kawaine;

    public ModalListener(Kawaine kawaine) {
        super();

        this.kawaine = kawaine;
    }

    @Override
    public void onModalInteraction(@Nonnull ModalInteractionEvent event) {

        if (event.getModalId().equals("modmail")) {
            String subject = event.getValue("subject").getAsString();
            String body = event.getValue("body").getAsString();

            event.reply("Thanks for your request!").setEphemeral(true).queue();
        }
    }
    
}
