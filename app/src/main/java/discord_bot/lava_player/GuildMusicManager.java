package discord_bot.lava_player;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.playlist_writer.Playlist;

public class GuildMusicManager {

    public final AudioPlayer player;
    public final AudioPlayer secondPlayer;
    public final TrackScheduler scheduler;

    public Playlist playlist ;
    public boolean record;

    public GuildMusicManager(AudioPlayerManager manager) {

        player = manager.createPlayer();
        secondPlayer = manager.createPlayer();
        scheduler = new TrackScheduler(player, secondPlayer);
        player.addListener(scheduler);
        secondPlayer.addListener(scheduler);
    }
  
    public AudioPlayerSendHandler getSendHandler() {
        
        return new AudioPlayerSendHandler(player);
    }
}
