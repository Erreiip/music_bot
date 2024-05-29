package discord_bot.jda;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import discord_bot.Main;
import discord_bot.commands.Commands;
import discord_bot.model.GuildMusicManager;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class Kawaine extends ListenerAdapter {

    private static final AudioPlayerManager playerManager;
    private static final Map<String, Integer> commands = new HashMap<>();

    static {
        playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);

        commands.put(Main.PLAY, Commands.PLAY);
        commands.put(Main.SKIP, Commands.SKIP);
        commands.put(Main.LOOP, Commands.LOOP);
        commands.put(Main.STOP, Commands.STOP);
        commands.put(Main.LAST, Commands.LAST);
        commands.put(Main.QUEUE, Commands.QUEUE);
        commands.put(Main.PAUSE, Commands.PAUSE);
        commands.put(Main.CLEAR_QUEUE, Commands.CLEAR_QUEUE);
        commands.put(Main.PLAYLIST_CREATE, Commands.CREATE);
        commands.put(Main.PLAYLIST_RECORD, Commands.RECORD);
        commands.put(Main.PLAYLIST_SAVE, Commands.SAVE);
        commands.put(Main.PLAYLIST_LOAD, Commands.LOAD);
        commands.put(Main.PLAYLISTS, Commands.PLAYLISTS);
        commands.put(Main.PLAYLISTS_SEE, Commands.SEE);
        commands.put(Main.PLAYLIST_ADD, Commands.ADD);
        commands.put(Main.PLAYLIST_REMOVE, Commands.REMOVE);
        commands.put(Main.HELP, Commands.HELP);
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

        Commands command = musicManager.getCommand(commands.get(event.getName()));

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
