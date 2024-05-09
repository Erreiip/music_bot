package discord_bot.commands.track;

import discord_bot.commands.Commands;
import discord_bot.commands_listeners.loop.ILoopListener;
import discord_bot.embded.MusicEmbded;
import discord_bot.jda_listener.Kawaine;
import discord_bot.jda_listener.model.GuildMusicManager;
import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Loop extends Commands implements ILoopListener {
    
    public Loop(TrackScheduler scheduler) {
        super(scheduler);

        scheduler.addLoopListener(this);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
        
        musicManager.scheduler.switchLoop();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
        
        musicManager.scheduler.switchLoop();
    }

    @Override
    public void onLoop(IDeferrableCallback event, TrackScheduler scheduler) {

        MessageEmbed mb = MusicEmbded.createEmbdedResponse("Loop is now " + (scheduler.isLooped() ? "enabled" : "disabled"));

        event.getHook().sendMessageEmbeds(mb).queue();
    }


}
