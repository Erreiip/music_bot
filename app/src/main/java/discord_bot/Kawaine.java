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
        
        Guild guild = event.getGuild();

        if (guild != event.getMember().getGuild())
            System.out.println("shesh");

        if (event.getName().equals("play")) {

            String songIdentifier = event.getOption(Main.PLAY_OPTION).getAsString();

            if (patternURL.matcher(songIdentifier).matches()) {

                this.joinChannel(event.getMember());

                this.addSong(event, songIdentifier, playerManager);
                return;
            }

            Couple<String, String> video = this.queryByName(songIdentifier);
            String URL = video.second;
            String title = video.first;

            this.joinChannel(event.getMember());

            this.addSong(event, URL, playerManager);
        }

        if (event.getName().equals("skip")) {
                
            this.skipTrack(event);
        }
    }
    
    private AudioManager joinChannel(Member member) {

        AudioChannel memberChannel = member.getVoiceState().getChannel();

        if (memberChannel == null) {
            fatalMessages("You are not in a voice channel.");
        }

        AudioManager audioManager = member.getGuild().getAudioManager();
        audioManager.openAudioConnection(memberChannel);

        return audioManager;
    }

    private void addSong(SlashCommandInteractionEvent event, String songIdentifier, AudioPlayerManager playerManager) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());

        playerManager.loadItemOrdered(musicManager, songIdentifier, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {

                event.reply("Adding to queue " + track.getInfo().title).queue();

                play(event.getMember(), musicManager, track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {

                AudioTrack firstTrack = playlist.getSelectedTrack();

                if (firstTrack == null) {
                firstTrack = playlist.getTracks().get(0);
                }

                event.reply("Adding to queue " + firstTrack.getInfo().title + " (first track of playlist " + playlist.getName() + ")").queue();

                play(event.getMember(), musicManager, firstTrack);
            }

            @Override
            public void noMatches() {

                event.reply("Nothing found by " + songIdentifier).queue();
            }

            @Override
            public void loadFailed(FriendlyException exception) {

                event.reply("Could not play: " + exception.getMessage()).queue();
                exception.printStackTrace();
            }
        });
    }

    private void play(Member member, GuildMusicManager musicManager, AudioTrack track) {
        joinChannel(member);

        musicManager.scheduler.queue(track);
    }
    
    private void skipTrack(SlashCommandInteractionEvent event) {

        GuildMusicManager musicManager = getGuildAudioPlayer(event.getGuild());    
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
