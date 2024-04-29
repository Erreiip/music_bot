package discord_bot.commands;

import java.util.List;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Kawaine;
import discord_bot.lava_player.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Queue implements Commands {

    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        List<AudioTrack> queue = musicManager.scheduler.getQueue();
        AudioTrack currentTrack = musicManager.scheduler.getCurrentTrack();

        if (queue.isEmpty() && currentTrack == null) {
            event.reply("The queue is empty.").queue();
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

        event.reply(builder.toString()).queue();
    }
    
}
