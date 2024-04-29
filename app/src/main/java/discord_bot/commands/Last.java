package discord_bot.commands;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.Kawaine;
import discord_bot.lava_player.GuildMusicManager;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;

public class Last implements Commands {
    
    @Override
    public void execute(SlashCommandInteractionEvent event, AudioPlayerManager playerManager, Kawaine kawaine) {
        
        GuildMusicManager musicManager = kawaine.getGuildAudioPlayer(event.getGuild());

        musicManager.scheduler.addLastTrack();

        AudioTrack currentTrack = musicManager.scheduler.getCurrentTrack();

        event.reply("Added the last played song to the queue: " + currentTrack.getInfo().title).queue();
    }
}
