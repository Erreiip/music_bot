package discord_bot;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;

import discord_bot.common.Couple;
import discord_bot.lava_player.AudioLoadResultHandlerImpl;
import discord_bot.lava_player.GuildMusicManager;
import discord_bot.youtube.ApiYoutube;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.channel.middleman.AudioChannel;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.managers.AudioManager;

public class Kawaine extends ListenerAdapter {

    private static final Pattern patternURL = Pattern.compile(".*://.*\\..*", Pattern.CASE_INSENSITIVE);

    private static final AudioPlayerManager playerManager = new DefaultAudioPlayerManager();
    private final Map<Long, GuildMusicManager> musicManagers = new HashMap<>();

    static {

        AudioSourceManagers.registerRemoteSources(playerManager);
    }

    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {

        if (!event.isFromGuild()) return;

        if (event.getName().equals("play")) {

            String songIdentifier = event.getOption(Main.PLAY_OPTION).getAsString();

            if (patternURL.matcher(songIdentifier).matches()) {

                AudioManager audioChannel = this.joinChannel(event);
                if (audioChannel == null) return;

                this.addSong(event, songIdentifier, playerManager);
                return;
            }

            Couple<String, String> video = this.queryByName(songIdentifier);
            String URL = video.second;

            AudioManager audioChannel = this.joinChannel(event);
            if (audioChannel == null) return;

            this.addSong(event, URL, playerManager);
        }

        if (event.getName().equals("skip")) {

            this.skipTrack(event);
        }
        
        if (event.getName().equals("loop")) {


            boolean loop = this.getGuildAudioPlayer(event.getGuild()).scheduler.switchLoop();
            event.reply("Loop is now " + (loop ? "enabled" : "disabled")).queue();
        }
        
        if (event.getName().equals("stop")) {

            AudioManager audioManager = event.getMember().getGuild().getAudioManager();
            audioManager.closeAudioConnection();

            event.reply("Stopped the player and left the voice channel.").queue();
        }

        if ( event.getName().equals("last") ) {

            GuildMusicManager musicManager = this.getGuildAudioPlayer(event.getGuild());
            musicManager.scheduler.addLastTrack();
        }
    }
    
    private AudioManager joinChannel(SlashCommandInteractionEvent event) {

        AudioChannel memberChannel = event.getMember().getVoiceState().getChannel();

        if (memberChannel == null) {
            event.reply("You are not in a voice channel.");
            return null;
        }

        AudioManager audioManager = event.getMember().getGuild().getAudioManager();
        audioManager.openAudioConnection(memberChannel);

        return audioManager;
    }

    private void addSong(SlashCommandInteractionEvent event, String songIdentifier, AudioPlayerManager playerManager) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        AudioLoadResultHandler handler = new AudioLoadResultHandlerImpl(musicManager, songIdentifier, this, event);

        playerManager.loadItemOrdered(musicManager, songIdentifier, handler);
    }

    public void play(SlashCommandInteractionEvent event, GuildMusicManager musicManager, AudioTrack track) {
        joinChannel(event);

        musicManager.scheduler.queue(track);
    }
    
    private void skipTrack(SlashCommandInteractionEvent event) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());
           
        musicManager.scheduler.setLoop(false);
        musicManager.scheduler.nextTrack();
    
        event.reply("Skipped to next track.").queue();
    }
    
    private synchronized GuildMusicManager getGuildAudioPlayer(Guild guild) {
        
        long guildId = Long.parseLong(guild.getId());
        GuildMusicManager musicManager = musicManagers.get(guildId);

        if (musicManager == null) {
            musicManager = new GuildMusicManager(playerManager);
            musicManagers.put(guildId, musicManager);
        }

        guild.getAudioManager().setSendingHandler(musicManager.getSendHandler());

        return musicManager;
    }
    
    private Couple<String, String> queryByName(String name) {

        try {
            return ApiYoutube.search(name);
        } catch (IOException e) {
            fatalMessages("An error occurred while searching for the video.");
            return null;
        }
    }
    
    private void fatalMessages(String message) {

        System.out.println(message);
    }
    
}
