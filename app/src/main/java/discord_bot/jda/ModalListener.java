package discord_bot.jda;

import discord_bot.commands.Commands;
import discord_bot.enumerate.CommandsEnum;
import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ModalListener extends ListenerAdapter {

    Kawaine kawaine;

    public ModalListener(Kawaine kawaine) {
        super();

        this.kawaine = kawaine;
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {

        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        event.deferEdit().queue();
        //TODO
        Commands command = musicManager.getCommand(CommandsEnum.getModalId(event.getModalId()).commandId);

        command.execute(event);
    }
    
}
