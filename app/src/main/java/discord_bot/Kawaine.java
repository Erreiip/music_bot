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

    private final Map<Float, GuildMusicManager> musicManagers = new HashMap<>();

    static {

        patternURL = Pattern.compile(".*://.*\\..*", Pattern.CASE_INSENSITIVE);
        playerManager = new DefaultAudioPlayerManager();

        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.isFromGuild()) return;

        event.deferReply().queue();

        fireEvent(event);
    }

    public void fireEvent(SlashCommandInteractionEvent event) {

        Commands command = null;

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        if (event.getName().equals(Main.PLAY)) {

            command = musicManager.getCommand(Commands.PLAY);
        }

        if (event.getName().equals(Main.SKIP)) {

            command = musicManager.getCommand(Commands.SKIP);   
        }
        
        if (event.getName().equals(Main.LOOP)) {

            command = musicManager.getCommand(Commands.LOOP);
        }
        
        if (event.getName().equals(Main.STOP)) {

            command = musicManager.getCommand(Commands.STOP);
        }

        if (event.getName().equals(Main.LAST)) {

            command = musicManager.getCommand(Commands.LAST);
        }

        if (event.getName().equals(Main.QUEUE)) {

            command = musicManager.getCommand(Commands.QUEUE);
        }
        
        if (event.getName().equals(Main.PAUSE)) {

            command = musicManager.getCommand(Commands.PAUSE);
        }

        if (event.getName().equals(Main.CLEAR_QUEUE)) {

            command = musicManager.getCommand(Commands.CLEAR_QUEUE);
        }

        if(event.getName().equals(Main.PLAYLIST_CREATE)) {

            command = musicManager.getCommand(Commands.CREATE);
        }

        if (event.getName().equals(Main.PLAYLIST_RECORD)) {

            command = musicManager.getCommand(Commands.RECORD);
        }

        if (event.getName().equals(Main.PLAYLIST_SAVE)) {

            command = musicManager.getCommand(Commands.SAVE);
        }

        if (event.getName().equals(Main.PLAYLIST_LOAD)) {

            command = musicManager.getCommand(Commands.LOAD);
        }

        if (event.getName().equals(Main.PLAYLISTS)) {

            command = musicManager.getCommand(Commands.PLAYLISTS);
        }

        if (event.getName().equals(Main.PLAYLISTS_SEE)) {

            command = musicManager.getCommand(Commands.SEE);
        }

        if ( event.getName().equals(Main.PLAYLIST_ADD) ) {

            command = musicManager.getCommand(Commands.ADD);
        }

        if ( event.getName().equals(Main.PLAYLIST_REMOVE) ) {

            command = musicManager.getCommand(Commands.REMOVE);
        }

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
