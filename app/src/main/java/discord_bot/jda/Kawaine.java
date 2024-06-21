package discord_bot.jda;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import dev.lavalink.youtube.YoutubeAudioSourceManager;
import dev.lavalink.youtube.clients.Music;
import dev.lavalink.youtube.clients.Web;
import dev.lavalink.youtube.clients.skeleton.Client;
import discord_bot.commands.Commands;
import discord_bot.enumerate.CommandsEnum;
import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Kawaine extends ListenerAdapter {

    private static final AudioPlayerManager playerManager;

    static {
        
        playerManager = new DefaultAudioPlayerManager();
        YoutubeAudioSourceManager ytSourceManager = new YoutubeAudioSourceManager(true, new Client[] {new Music(), new Web()});

        //AudioSourceManagers.registerRemoteSources(playerManager, com.sedmelluq.discord.lavaplayer.source.youtube.YoutubeAudioSourceManager.class);
        AudioSourceManagers.registerLocalSource(playerManager);
        playerManager.registerSourceManager(ytSourceManager);
    }

    private final Map<Float, GuildMusicManager> musicManagers;

    public Kawaine() {
    
        this.musicManagers = new HashMap<>();
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.isFromGuild()) return;

        event.deferReply().queue();
        
        fireEvent(event);
    }
    
    public void fireEvent(SlashCommandInteractionEvent event) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        Commands command = musicManager.getCommand(CommandsEnum.getCommandId(event.getName()).commandId);

        command.execute(event);
    }
    
    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        
        Float guildId = Float.parseFloat(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager();
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }

    public static AudioPlayerManager getPlayerManager() {

        return playerManager;
    }
}
