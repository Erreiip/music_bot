package discord_bot.jda;

import discord_bot.commands.Commands;
import discord_bot.enumerate.CommandsEnum;
import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.component.StringSelectInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SelectListener extends ListenerAdapter {

    public static final String PREFIX = "SELECT"; // faire un get pour chaque type dans buttonEnum

    Kawaine kawaine;

    public SelectListener(Kawaine kawaine) {
        super();

        this.kawaine = kawaine;
    }

    @Override
    public void onStringSelectInteraction(StringSelectInteractionEvent event) {;

        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        event.deferReply().queue();

        String id = event.getSelectMenu().getId();
        id = id.substring(PREFIX.length());

        Commands command = musicManager.getCommand(CommandsEnum.getCommandId(id).commandId);

        command.execute(event);
    }
}