package discord_bot.commands.track;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import discord_bot.commands_listeners.skip.ISkipListener;
import discord_bot.embded.MusicEmbded;
import discord_bot.enumerate.ButtonEnum;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Skip extends Commands implements ISkipListener {

    public Skip(TrackScheduler scheduler) {
        super(scheduler);

        scheduler.addSkipListener(this);
    }

    @Override
    public void onSkip(IDeferrableCallback event, TrackScheduler scheduler) {
        
        MessageEmbed mb = MusicEmbded.createEmbded(scheduler.getCurrentTrack().getInfo());

        event.getHook().sendMessageEmbeds(mb).setActionRow(ButtonEnum.getPlayButton()).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {

        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
           
        musicManager.scheduler.setLoop(false);
        musicManager.scheduler.nextTrack();
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {

        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());
           
        musicManager.scheduler.setLoop(false);
        musicManager.scheduler.nextTrack();
    }
    
}
