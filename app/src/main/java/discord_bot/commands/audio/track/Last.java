package discord_bot.commands.audio.track;

import discord_bot.commands.audio.Commands;
import discord_bot.model.GuildMusicManager;
import discord_bot.model.MessageSender;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Last extends Commands {
    
    public Last(GuildMusicManager musicManager) {
        super(musicManager);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        
        addLastTrack((IDeferrableCallback) event);
    }

    @Override
    public void execute(ButtonInteractionEvent event) {
        
        addLastTrack((IDeferrableCallback) event);
    }

    public void addLastTrack(IDeferrableCallback event) {

        if ( musicManager.getScheduler().addLastTrack() == false) {
            MessageSender.errorEvent(musicManager.getMessageSender(), "No track to add", event);
        }
    }
}
