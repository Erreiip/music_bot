package discord_bot.commands.audio.track;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Queue extends Commands {

    public Queue(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {

        MessageSender.queueEvent(musicManager.getMessageSender(), musicManager.getScheduler().getQueue(), event);
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
        
        MessageSender.queueEvent(musicManager.getMessageSender(), musicManager.getScheduler().getQueue(), event);
    }
    
}
