package discord_bot.jda;

import discord_bot.commands.Commands;
import discord_bot.enumerate.CommandsEnum;
import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelectListener extends ListenerAdapter {

    Kawaine kawaine;

    public SelectListener(Kawaine kawaine) {
        super();

        this.kawaine = kawaine;
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {;

        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        event.deferReply().queue();

        Commands command = musicManager.getCommand(CommandsEnum.getCommandId(event.getSelectMenu().getId()).commandId);

        command.execute(event);
    }
    
}
