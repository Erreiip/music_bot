package discord_bot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;

import discord_bot.commands.Commands;
import discord_bot.common.Couple;
import discord_bot.common.IProcessAudio;
import discord_bot.lava_player.AudioLoadResultHandlerImpl;
import discord_bot.youtube.ApiYoutube;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Kawaine extends ListenerAdapter {

    private static final Pattern patternURL;

    private static final AudioPlayerManager playerManager;

    private static final Map<String, Integer> commands = new HashMap<>();

    private final Map<Float, GuildMusicManager> musicManagers = new HashMap<>();

    static {

        patternURL = Pattern.compile(".*://.*\\..*", Pattern.CASE_INSENSITIVE);
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

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.isFromGuild()) return;

        event.deferReply().queue();

        fireEvent(event);
    }

    public void fireEvent(SlashCommandInteractionEvent event) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        Commands command = musicManager.getCommand(commands.get(event.getName()));

        command.execute(event, this);
    }
    
    public AudioManager joinChannel(SlashCommandInteractionEvent event) {

        AudioChannel memberChannel = event.getMember().getVoiceState().getChannel();

        if (memberChannel == null) {
            event.getHook().sendMessage("You are not in a voice channel.");
            return null;
        }
                                        
        AudioManager audioManager = event.getMember().getGuild().getAudioManager();
        audioManager.openAudioConnection(memberChannel);

        return audioManager;
    }

    public void addSong(SlashCommandInteractionEvent event, String songIdentifier, Float speed, IProcessAudio callback) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        AudioLoadResultHandler handler = new AudioLoadResultHandlerImpl(musicManager, songIdentifier, callback, event, speed);

        playerManager.loadItemOrdered(musicManager, songIdentifier, handler);
    }
    
    public synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        
        Float guildId = Float.parseFloat(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }
    
    public static String getSongIdentifier(String songIdentifier) {

        if (patternURL.matcher(songIdentifier).matches()) return songIdentifier;

        Couple<String, String> query = queryByName(songIdentifier);

        if (query == null) return null;

        return query.second;
    }

    private static Couple<String, String> queryByName(String name) {

        try {
            return ApiYoutube.search(name);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static AudioPlayerManager getPlayerManager() {

        return playerManager;
    }
}
