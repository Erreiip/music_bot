package discord_bot.commands.track;

import discord_bot.commands.Commands;
import discord_bot.listeners.commands_listeners.skip.ISkipListener;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Skip extends Commands implements ISkipListener {

    public Skip(GuildMusicManager musicManager) {
        super(musicManager);

        musicManager.getScheduler().addSkipListener(this);
    }

    @Override
    public void onSkip(IDeferrableCallback event) {
        
        MessageSender.playEvent(musicManager.getMessageSender(), musicManager.getScheduler().getCurrentTrack().getInfo(), musicManager.getScheduler().isLooped(), event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        
        sendSkipMessage(event);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {

        sendSkipMessage(event);
    }

    public void sendSkipMessage(IDeferrableCallback event) {

        musicManager.getScheduler().setLoop(false);
        musicManager.player.setPaused(false);
        
        if ( ! musicManager.getScheduler().nextTrack() ) {

            MessageSender.errorEvent(musicManager.getMessageSender(), "No more track in the queue", event);
        } else {

            MessageSender.infoEvent(musicManager.getMessageSender(), "Skipped", event);
        }
    }
    
}
