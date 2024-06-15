package discord_bot.jda;

import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ModalListener extends ListenerAdapter {

    public enum Modal {
        FromQueue("FromQueueModal");

        public final String id;
        private Modal(String id) {
            this.id = id;
        }
    }

    Kawaine kawaine;

    public ModalListener(Kawaine kawaine) {
        super();

        this.kawaine = kawaine;
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

        if (event.getModalId().equals("modmail")) {
            String subject = event.getValue("subject").getAsString();
            String body = event.getValue("body").getAsString();

            event.reply("Thanks for your request!").setEphemeral(true).queue();
        }
    }
    
}
