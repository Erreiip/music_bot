package discord_bot.commands;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Help extends Commands {

    public Help(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        
        sendEmbdedHelp(event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {

        sendEmbdedHelp(event);
    }

    private void sendEmbdedHelp(IDeferrableCallback event) {

        MessageSender.helpEvent(musicManager.getMessageSender(), event);
    }
    
}
