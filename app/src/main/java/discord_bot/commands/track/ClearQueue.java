package discord_bot.commands.track;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.commands.Commands;
import discord_bot.embded.MusicEmbded;
import discord_bot.jda_listener.Kawaine;
import discord_bot.jda_listener.model.GuildMusicManager;
import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class ClearQueue extends Commands {

    public ClearQueue(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        clearQueue((IDeferrableCallback) event, kawaine);
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        
        clearQueue((IDeferrableCallback) event, kawaine);
    }

    public void clearQueue(IDeferrableCallback event, Kawaine kawaine) {

        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        musicManager.scheduler.clearQueue();

        event.getHook().sendMessageEmbeds(MusicEmbded.createEmbdedResponse("Queue cleared")).queue();
    }
    
}
