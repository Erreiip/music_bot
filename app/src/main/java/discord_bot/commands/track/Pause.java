package discord_bot.commands.track;

import discord_bot.commands.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Pause extends Commands {
    
    public Pause(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
    
        musicManager.player.setPaused(!musicManager.player.isPaused());

        sendResponse(event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        
        musicManager.player.setPaused(!musicManager.player.isPaused());

        sendResponse(event);
    }

    public void sendResponse(IDeferrableCallback event) {

        MessageSender.infoEvent(musicManager.getMessageSender(), "Player is now " + (musicManager.player.isPaused() ? "paused" : "resumed"), event);
    }

}
