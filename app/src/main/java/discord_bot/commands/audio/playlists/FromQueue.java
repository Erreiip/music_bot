package discord_bot.commands.audio.playlists;

import org.checkerframework.checker.units.qual.t;

import discord_bot.commands.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

public class FromQueue extends Commands {

    public static final String MODAL_ID = "FromQueueModal";
    public static final String MODAL_NAME = "name";

    public FromQueue(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
        
        TextInput subject = TextInput.create(MODAL_NAME, "Nom de la playlist", TextInputStyle.SHORT)
            .setPlaceholder("ZiziCacaMixtape")
            .setMaxLength(30)
            .setRequired(true)
            .build();

        Modal modal = Modal.create(MODAL_ID, "Créer une playlist à partir de la queue")
            .addComponents(ActionRow.of(subject))
            .build();

        event.replyModal(modal).queue();
    }

    @Override
    public void executeCommands(ModalInteractionEvent event) {
        
        System.out.println("FromQueue executeCommands(ModalInteractionEvent event)");
    }
    
}