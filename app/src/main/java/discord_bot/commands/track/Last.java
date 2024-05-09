package discord_bot.commands.track;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.commands.Commands;
import discord_bot.jda_listener.Kawaine;
import discord_bot.jda_listener.model.GuildMusicManager;
import discord_bot.jda_listener.model.TrackScheduler;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.interactions.callbacks.IDeferrableCallback;

public class Last extends Commands {
    
    public Last(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        addLastTrack((IDeferrableCallback) event, kawaine);
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        
        addLastTrack((IDeferrableCallback) event, kawaine);
    }

    public void addLastTrack(IDeferrableCallback event, Kawaine kawaine) {

        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        musicManager.scheduler.addLastTrack();

        AudioTrack currentTrack = musicManager.scheduler.getCurrentTrack();

        event.getHook().sendMessage("Added the last played song to the queue: " + currentTrack.getInfo().title).queue();
    }
}
