package discord_bot.jda;

import discord_bot.commands.Commands;
import discord_bot.enumerate.CommandsEnum;
import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class ButtonListener extends ListenerAdapter {

    private Kawaine kawaine;

    public ButtonListener(Kawaine kawaine) {
     
        this.kawaine = kawaine;
    }

    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());


        Integer id = Integer.valueOf(event.getButton().getId());

        CommandsEnum commandEnum = CommandsEnum.getButtonId(id);

        Commands command = musicManager.getCommand(commandEnum.commandId);

        if ( commandEnum.isHookable ) {

            event.deferEdit().queue();
        }

        command.execute(event);
    }
    
}
