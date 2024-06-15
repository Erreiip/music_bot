package discord_bot.commands.audio.track;

import discord_bot.commands.audio.Commands;
import discord_bot.listeners.commands_listeners.loop.ILoopListener;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Loop extends Commands implements ILoopListener {
    
    public Loop(GuildMusicManager musicManager) {
        super(musicManager);

        musicManager.getScheduler().addLoopListener(this);
    }

    @Override
    public void executeCommands(SlashCommandInteractionEvent event) {
        
        musicManager.getScheduler().switchLoop();
    }

    @Override
    public void executeCommands(ButtonInteractionEvent event) {
            
        musicManager.getScheduler().switchLoop();
    }

    @Override
    public void onLoop(IDeferrableCallback event) {

        musicManager.getMessageSender().editLastPlayEvent(musicManager.getScheduler().getCurrentTrack().getInfo(), musicManager.getScheduler().isLooped());
        MessageSender.infoEvent(musicManager.getMessageSender(), "Loop is now " + (musicManager.getScheduler().isLooped() ? "enabled" : "disabled"), event);
    }


}
