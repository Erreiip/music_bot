package discord_bot.commands.track;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Last extends Commands {
    
    public Last(TrackScheduler scheduler) {
        super(scheduler);
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        musicManager.scheduler.addLastTrack();

        AudioTrack currentTrack = musicManager.scheduler.getCurrentTrack();

        event.getHook().sendMessage("Added the last played song to the queue: " + currentTrack.getInfo().title).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
}
