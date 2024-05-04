package discord_bot.commands.track;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.GuildMusicManager;
import discord_bot.Kawaine;
import discord_bot.TrackScheduler;
import discord_bot.commands.Commands;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;

public class Queue extends Commands {

    public Queue(TrackScheduler scheduler) {
        super(scheduler);
        //TODO Auto-generated constructor stub
    }

    @Override
    public void execute(SlashCommandInteractionEvent event, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        List<AudioTrack> queue = musicManager.scheduler.getQueue();
        AudioTrack currentTrack = musicManager.scheduler.getCurrentTrack();

        if (queue.isEmpty() && currentTrack == null) {
            event.getHook().sendMessage("The queue is empty.").queue();
            return;
        }

        StringBuilder builder = new StringBuilder();

        if (currentTrack != null) {

            if (musicManager.scheduler.isLooped()) {
                builder.append(":arrows_counterclockwise: ");
            } else {
                builder.append(":arrow_forward: ");
            }

            builder.append(currentTrack.getInfo().title).append("\n");
        }

        for (int i = 0; i < queue.size(); i++) {
            
            AudioTrack track = queue.get(i);
            builder.append(i + 1).append(". ").append(track.getInfo().title).append("\n");
        }

        event.getHook().sendMessage(builder.toString()).queue();
    }

    @Override
    public void execute(ButtonInteractionEvent event, Kawaine kawaine) {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'execute'");
    }
    
}
