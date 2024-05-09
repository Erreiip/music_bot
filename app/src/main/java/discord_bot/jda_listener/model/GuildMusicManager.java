package discord_bot.jda_listener.model;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

import discord_bot.commands.Commands;
import discord_bot.lava_player.AudioPlayerSendHandler;
import discord_bot.playlist_writer.Playlist;

public class GuildMusicManager {

    public final AudioPlayer player;
    public final AudioPlayer secondPlayer;
    public final TrackScheduler scheduler;

    public Playlist playlist ;
    public boolean record;

    public Commands[] commands;

    public GuildMusicManager(AudioPlayerManager manager) {

        player = manager.createPlayer();
        secondPlayer = manager.createPlayer();
        
        scheduler = new TrackScheduler(player, secondPlayer);
        this.commands = Commands.getCommands(scheduler);

        player.addListener(scheduler);
        secondPlayer.addListener(scheduler);
    }
  
    public AudioPlayerSendHandler getSendHandler() {
        
        return new AudioPlayerSendHandler(player);
    }

    public Commands getCommand(int command) {
        
        return commands[command];
    }
}
