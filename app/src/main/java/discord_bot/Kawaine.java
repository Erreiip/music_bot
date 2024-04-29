package discord_bot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.commands.ClearQueue;
import discord_bot.commands.Commands;
import discord_bot.commands.Last;
import discord_bot.commands.Loop;
import discord_bot.commands.Pause;
import discord_bot.commands.Play;
import discord_bot.commands.Queue;
import discord_bot.commands.Skip;
import discord_bot.commands.Stop;
import discord_bot.commands.playlists.Add;
import discord_bot.commands.playlists.Create;
import discord_bot.commands.playlists.Load;
import discord_bot.commands.playlists.Playlists;
import discord_bot.commands.playlists.Record;
import discord_bot.commands.playlists.Remove;
import discord_bot.commands.playlists.Save;
import discord_bot.commands.playlists.See;
import discord_bot.common.Couple;
import discord_bot.common.IProcessAudio;
import discord_bot.embded.MusicEmbded;
import discord_bot.enumerate.ButtonEnum;
import discord_bot.lava_player.AudioLoadResultHandlerImpl;
import discord_bot.lava_player.GuildMusicManager;
import discord_bot.youtube.ApiYoutube;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.components.buttons.Button;
import net.dv8tion.jda.api.managers.AudioManager;

public class Kawaine extends ListenerAdapter implements IProcessAudio {

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

        Commands command = null;

        if (event.getName().equals(Main.PLAY)) {

            command = new Play();
        }

        if (event.getName().equals(Main.SKIP)) {

            command = new Skip();
        }
        
        if (event.getName().equals(Main.LOOP)) {

            command = new Loop();
        }
        
        if (event.getName().equals(Main.STOP)) {

            command = new Stop();
        }

        if (event.getName().equals(Main.LAST)) {

            command = new Last();
        }

        if (event.getName().equals(Main.QUEUE)) {

            command = new Queue();
        }
        
        if (event.getName().equals(Main.PAUSE)) {

            command = new Pause();
        }

        if (event.getName().equals(Main.CLEAR_QUEUE)) {

            command = new ClearQueue();
        }

        if(event.getName().equals(Main.PLAYLIST_CREATE)) {

            command = new Create();
        }

        if (event.getName().equals(Main.PLAYLIST_RECORD)) {

            command = new Record();
        }

        if (event.getName().equals(Main.PLAYLIST_SAVE)) {

            command = new Save();
        }

        if (event.getName().equals(Main.PLAYLIST_LOAD)) {

            command = new Load();
        }

        if (event.getName().equals(Main.PLAYLISTS)) {

            command = new Playlists();
        }

        if (event.getName().equals(Main.PLAYLISTS_SEE)) {

            command = new See();
        }

        if ( event.getName().equals(Main.PLAYLIST_ADD) ) {

            command = new Add();
        }

        if ( event.getName().equals(Main.PLAYLIST_REMOVE) ) {

            command = new Remove();
        }

        command.execute(event, playerManager, this);
    }
    
    public void processSong(SlashCommandInteractionEvent event, String songIdentifier, Float speed) {

        AudioManager audioChannel = this.joinChannel(event);
        if (audioChannel == null) return;

        songIdentifier = getSongIdentifier(songIdentifier);

        this.addSong(event, songIdentifier, playerManager, speed, this);
    }
    
    public AudioManager joinChannel(SlashCommandInteractionEvent event) {

        AudioChannel memberChannel = event.getMember().getVoiceState().getChannel();

        if (memberChannel == null) {
            event.reply("You are not in a voice channel.");
            return null;
        }

        AudioManager audioManager = event.getMember().getGuild().getAudioManager();
        audioManager.openAudioConnection(memberChannel);

        return audioManager;
    }

    public void addSong(SlashCommandInteractionEvent event, String songIdentifier, AudioPlayerManager playerManager, Float speed, IProcessAudio callback) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        AudioLoadResultHandler handler = new AudioLoadResultHandlerImpl(musicManager, songIdentifier, callback, event, speed);

        playerManager.loadItemOrdered(musicManager, songIdentifier, handler);
    }

    @Override
    public void onTrackGet(SlashCommandInteractionEvent event, GuildMusicManager musicManager, AudioTrack track, Float speed ) {
        
        joinChannel(event);

        musicManager.scheduler.queue(track, speed != null ? speed : 1);

        event.getChannel().sendMessageEmbeds(MusicEmbded.createEmbded(track.getInfo()))
            .addActionRow(
                Button.primary(ButtonEnum.ADD_PLAYLIST.id + "", ButtonEnum.ADD_PLAYLIST.label),
                Button.primary(ButtonEnum.SKIP.id + "", ButtonEnum.SKIP.label)
            )    
            .queue();

        event.reply("Added to queue: " + track.getInfo().title).queue();
        
        if (musicManager.record) musicManager.playlist.addTrack(track.getInfo()); 
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
}
